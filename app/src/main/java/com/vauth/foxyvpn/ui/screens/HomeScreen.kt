package com.vauth.foxyvpn.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vauth.foxyvpn.FoxyVpnApp
import com.vauth.foxyvpn.data.formatBytes
import com.vauth.foxyvpn.data.formatBytesPerSecond
import com.vauth.foxyvpn.data.model.ConnectionState
import com.vauth.foxyvpn.ui.theme.LiquidGlassPanel
import com.vauth.foxyvpn.ui.theme.LocalFoxyStatusColors
import com.vauth.foxyvpn.vpn.FoxyVpnService

@Composable
fun HomeScreen(
    app: FoxyVpnApp,
    onRequestConnect: () -> Unit,
    onDisconnect: () -> Unit,
    onOpenServers: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val state by FoxyVpnService.state.collectAsState()
    val lastError by FoxyVpnService.lastError.collectAsState()
    val trafficUsage by FoxyVpnService.trafficUsage.collectAsState()
    val selectedProxy by app.proxyStateStore.selectedProxyFlow.collectAsState()
    val statusColors = LocalFoxyStatusColors.current
    val statusColor = when (state) {
        ConnectionState.CONNECTED -> statusColors.connected
        ConnectionState.CONNECTING -> statusColors.connecting
        ConnectionState.DISCONNECTED -> MaterialTheme.colorScheme.primary
    }
    val ringColor by animateColorAsState(statusColor, label = "glass-status-color")
    val scale by animateFloatAsState(
        targetValue = if (state == ConnectionState.CONNECTED) 1f else .96f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "glass-power-scale",
    )
    val haptics = LocalHapticFeedback.current
    val connected = state == ConnectionState.CONNECTED
    val statusTitle = when (state) {
        ConnectionState.CONNECTED -> "You’re protected"
        ConnectionState.CONNECTING -> "Securing connection…"
        ConnectionState.DISCONNECTED -> "Your privacy, in focus"
    }
    val statusSubtitle = when (state) {
        ConnectionState.CONNECTED -> "Your traffic is routed through FoxyVPN"
        ConnectionState.CONNECTING -> "Establishing an encrypted tunnel"
        ConnectionState.DISCONNECTED -> "Connect to protect your traffic"
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            modifier = Modifier.size(38.dp).clip(RoundedCornerShape(13.dp)).background(
                                Brush.linearGradient(listOf(Color(0xFF6BA8FF), Color(0xFF5869E8), Color(0xFF46CFC5))),
                            ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                            Text("FoxyVPN", fontWeight = FontWeight.Bold, fontSize = 19.sp, color = MaterialTheme.colorScheme.onBackground)
                            Text("PRIVATE CONNECTION", fontSize = 9.sp, letterSpacing = 1.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier.padding(end = 12.dp).size(42.dp).clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .clickable { onOpenSettings() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp).padding(bottom = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    Text(if (connected) "CONNECTION ACTIVE" else "YOUR DIGITAL SPACE", fontSize = 10.sp, letterSpacing = 1.8.sp, fontWeight = FontWeight.SemiBold, color = statusColor)
                    Spacer(Modifier.height(7.dp))
                    Text(statusTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(5.dp))
                    Text(statusSubtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(Modifier.height(26.dp))
            // Central connection control: the primary Liquid Glass interaction on this screen.
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(252.dp)) {
                Box(
                    modifier = Modifier.size(246.dp).clip(CircleShape)
                        .background(Brush.radialGradient(listOf(ringColor.copy(alpha = .22f), ringColor.copy(alpha = .08f), Color.Transparent))),
                )
                Box(
                    modifier = Modifier.size(214.dp).clip(CircleShape)
                        .background(Brush.radialGradient(listOf(ringColor.copy(alpha = .17f), ringColor.copy(alpha = .04f), Color.Transparent)))
                        .background(Color.Transparent)
                        .clip(CircleShape),
                )
                Box(
                    modifier = Modifier.size((170 * scale).dp).clip(CircleShape)
                        .background(Brush.linearGradient(listOf(
                            if (connected) Color(0xFF66E7B7).copy(alpha = .30f) else Color(0xFF78ABFF).copy(alpha = .36f),
                            if (connected) Color(0xFF16865A).copy(alpha = .11f) else Color(0xFF726CF2).copy(alpha = .14f),
                        )))
                        .clickable {
                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            if (state == ConnectionState.DISCONNECTED) onRequestConnect() else onDisconnect()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier.size((138 * scale).dp).clip(CircleShape)
                            .background(Brush.linearGradient(listOf(
                                if (connected) Color(0xFF69E2B3).copy(alpha = .94f) else Color(0xFF80B9FF).copy(alpha = .96f),
                                if (connected) Color(0xFF1BAF88).copy(alpha = .95f) else Color(0xFF6675EF).copy(alpha = .96f),
                                if (connected) Color(0xFF15987A).copy(alpha = .94f) else Color(0xFF416BE4).copy(alpha = .96f),
                            )))
                            .clickable {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                if (state == ConnectionState.DISCONNECTED) onRequestConnect() else onDisconnect()
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = if (connected) Icons.Filled.CheckCircle else Icons.Filled.PowerSettingsNew,
                            contentDescription = if (state == ConnectionState.DISCONNECTED) "Connect VPN" else "Disconnect VPN",
                            tint = Color.White,
                            modifier = Modifier.size(49.dp),
                        )
                    }
                }
            }
            Spacer(Modifier.height(7.dp))
            Text(
                when (state) {
                    ConnectionState.CONNECTED -> "CONNECTED"
                    ConnectionState.CONNECTING -> "PLEASE WAIT"
                    ConnectionState.DISCONNECTED -> "TAP TO CONNECT"
                },
                fontSize = 11.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
            )
            if (state == ConnectionState.CONNECTING) {
                Spacer(Modifier.height(5.dp))
                Text("Tap the control again to stop", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            val error = lastError
            if (error != null && state != ConnectionState.CONNECTING) {
                Spacer(Modifier.height(8.dp))
                LiquidGlassPanel(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), elevation = 3.dp) {
                    Text(error, modifier = Modifier.padding(14.dp), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(Modifier.height(27.dp))
            LiquidGlassPanel(modifier = Modifier.fillMaxWidth().clickable { onOpenServers() }, shape = RoundedCornerShape(25.dp), elevation = 8.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp, vertical = 17.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.size(47.dp).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = .13f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.Public, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    }
                    Spacer(Modifier.size(13.dp))
                    Column(Modifier.weight(1f)) {
                        Text("VPN LOCATION", fontSize = 9.sp, letterSpacing = 1.4.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(4.dp))
                        Text(selectedProxy?.let { it.countryName.ifBlank { it.countryCode } } ?: "Recommended location", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Choose your virtual location", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(Icons.Filled.ChevronRight, contentDescription = "Change location", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(23.dp))
                }
            }
            Spacer(Modifier.height(13.dp))
            LiquidGlassPanel(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = 6.dp,
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "DATA TRANSFER",
                            fontSize = 10.sp,
                            letterSpacing = 1.6.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            when (state) {
                                ConnectionState.CONNECTED -> "THIS SESSION"
                                ConnectionState.CONNECTING -> "STARTING…"
                                ConnectionState.DISCONNECTED -> "LAST SESSION"
                            },
                            fontSize = 9.sp,
                            letterSpacing = 1.1.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = statusColor,
                        )
                    }
                    Spacer(Modifier.height(13.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(11.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.weight(1f).clip(RoundedCornerShape(19.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = .68f))
                                .padding(horizontal = 13.dp, vertical = 14.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Filled.ArrowDownward, contentDescription = null, tint = Color(0xFF38A7FF), modifier = Modifier.size(18.dp))
                                Text("Downloaded", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Spacer(Modifier.height(9.dp))
                            Text(
                                formatBytes(trafficUsage.downloadedBytes),
                                fontSize = 23.sp,
                                lineHeight = 27.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "${formatBytesPerSecond(if (connected) trafficUsage.downloadBytesPerSecond else 0L)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF38A7FF),
                                maxLines = 1,
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f).clip(RoundedCornerShape(19.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = .68f))
                                .padding(horizontal = 13.dp, vertical = 14.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Filled.ArrowUpward, contentDescription = null, tint = Color(0xFF9A80FF), modifier = Modifier.size(18.dp))
                                Text("Uploaded", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Spacer(Modifier.height(9.dp))
                            Text(
                                formatBytes(trafficUsage.uploadedBytes),
                                fontSize = 23.sp,
                                lineHeight = 27.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "${formatBytesPerSecond(if (connected) trafficUsage.uploadBytesPerSecond else 0L)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF9A80FF),
                                maxLines = 1,
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Traffic counted by the VPN tunnel · updates every 2 seconds",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(Modifier.height(13.dp))
            LiquidGlassPanel(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(22.dp), elevation = 4.dp) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(38.dp).clip(CircleShape).background(statusColor.copy(alpha = .13f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.Lock, contentDescription = null, tint = statusColor, modifier = Modifier.size(19.dp))
                    }
                    Spacer(Modifier.size(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(if (connected) "Encrypted tunnel" else "Protection status", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(3.dp))
                        Text(if (connected) "Your VPN tunnel is running" else "Connect to start protecting traffic", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Box(Modifier.size(8.dp).clip(CircleShape).background(statusColor))
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Designed for a calmer, safer connection.", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
