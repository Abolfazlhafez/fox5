package com.vauth.foxyvpn.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** An ambient, slowly layered background: color is in the content, glass is reserved for controls. */
@Composable
fun LiquidGlassBackdrop(content: @Composable BoxScope.() -> Unit) {
    val dark = isSystemInDarkTheme()
    val base = if (dark) Color(0xFF080E1B) else Color(0xFFF3F7FD)
    val blue = if (dark) Color(0xFF244FA3) else Color(0xFF9BBEFF)
    val violet = if (dark) Color(0xFF563D98) else Color(0xFFC7C5FF)
    val aqua = if (dark) Color(0xFF126E7A) else Color(0xFFB7EFEB)
    Box(Modifier.fillMaxSize().background(base)) {
        Canvas(Modifier.fillMaxSize()) {
            drawRect(
                Brush.linearGradient(
                    colors = if (dark) listOf(Color(0xFF0A1222), Color(0xFF10172A), Color(0xFF08101D))
                    else listOf(Color(0xFFF8FAFF), Color(0xFFEEF4FC), Color(0xFFF7F8FE)),
                    start = Offset.Zero,
                    end = Offset(size.width, size.height),
                ),
            )
            val radius = size.width * 0.74f
            drawCircle(
                brush = Brush.radialGradient(listOf(blue.copy(alpha = if (dark) .35f else .38f), blue.copy(alpha = .12f), Color.Transparent), center = Offset(size.width * .08f, size.height * .17f), radius = radius),
                radius = radius,
                center = Offset(size.width * .08f, size.height * .17f),
            )
            drawCircle(
                brush = Brush.radialGradient(listOf(violet.copy(alpha = if (dark) .26f else .34f), violet.copy(alpha = .10f), Color.Transparent), center = Offset(size.width * .94f, size.height * .43f), radius = radius * .82f),
                radius = radius * .82f,
                center = Offset(size.width * .94f, size.height * .43f),
            )
            drawCircle(
                brush = Brush.radialGradient(listOf(aqua.copy(alpha = if (dark) .19f else .42f), Color.Transparent), center = Offset(size.width * .55f, size.height * .98f), radius = radius * .9f),
                radius = radius * .9f,
                center = Offset(size.width * .55f, size.height * .98f),
            )
        }
        content()
    }
}

/** Translucent material with a hairline highlight and soft shadow, used selectively for controls. */
@Composable
fun LiquidGlassPanel(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(28.dp),
    elevation: Dp = 14.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    val dark = isSystemInDarkTheme()
    val top = if (dark) Color(0xB92B3D59) else Color(0xC8FFFFFF)
    val bottom = if (dark) Color(0x96303C56) else Color(0x9EDCE8FA)
    val edgeTop = if (dark) Color(0x77E0EBFF) else Color(0xDDFFFFFF)
    val edgeBottom = if (dark) Color(0x334F7CC2) else Color(0x5590A9D0)
    Box(
        modifier = modifier
            .shadow(elevation, shape, clip = false)
            .clip(shape)
            .background(Brush.verticalGradient(listOf(top, bottom)))
            .border(1.dp, Brush.linearGradient(listOf(edgeTop, edgeBottom, edgeTop.copy(alpha = .30f))), shape),
        content = content,
    )
}
