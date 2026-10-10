package com.vauth.foxyvpn.vpn

import android.app.PendingIntent
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.vauth.foxyvpn.FoxyVpnApp
import com.vauth.foxyvpn.MainActivity
import com.vauth.foxyvpn.data.model.ConnectionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** Quick Settings tile for toggling FoxyVPN without opening the app when permission is already granted. */
class FoxyVpnTileService : TileService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var stateJob: Job? = null

    override fun onStartListening() {
        super.onStartListening()
        stateJob?.cancel()
        stateJob = scope.launch {
            FoxyVpnService.state.collect { state -> renderState(state) }
        }
        renderState(FoxyVpnService.state.value)
    }

    override fun onStopListening() {
        stateJob?.cancel()
        stateJob = null
        super.onStopListening()
    }

    override fun onClick() {
        super.onClick()
        val state = FoxyVpnService.state.value
        if (state != ConnectionState.DISCONNECTED) {
            FoxyVpnService.stop(this)
            renderState(ConnectionState.DISCONNECTED)
            return
        }

        val app = application as FoxyVpnApp
        val vpnConsentIntent = if (app.settingsStore.proxyOnlyMode) null else VpnService.prepare(this)
        if (vpnConsentIntent == null) {
            // No UI is required once Android's VPN permission has been granted.
            FoxyVpnService.start(this)
            renderState(ConnectionState.CONNECTING)
        } else {
            // Android requires an Activity to display its first-time VPN consent screen.
            val launchIntent = Intent(this, MainActivity::class.java).apply {
                action = MainActivity.ACTION_CONNECT_FROM_TILE
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            if (Build.VERSION.SDK_INT >= 34) {
                val pendingIntent = PendingIntent.getActivity(
                    this,
                    REQUEST_CODE_CONNECT_FROM_TILE,
                    launchIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
                startActivityAndCollapse(pendingIntent)
            } else {
                @Suppress("DEPRECATION")
                startActivityAndCollapse(launchIntent)
            }
        }
    }

    private fun renderState(state: ConnectionState) {
        val tile = qsTile ?: return
        tile.label = getString(com.vauth.foxyvpn.R.string.qs_tile_label)
        tile.state = if (state == ConnectionState.DISCONNECTED) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
        val status = when (state) {
            ConnectionState.DISCONNECTED -> "Disconnected"
            ConnectionState.CONNECTING -> "Connecting…"
            ConnectionState.CONNECTED -> "Connected"
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.subtitle = status
            tile.contentDescription = "FoxyVPN: $status"
        }
        tile.updateTile()
    }

    override fun onDestroy() {
        stateJob?.cancel()
        scope.cancel()
        super.onDestroy()
    }

    private companion object {
        const val REQUEST_CODE_CONNECT_FROM_TILE = 42
    }
}
