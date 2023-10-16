package com.mertgolcu.mystore.tasker.common.loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

/**
 * @author mertgolcu
 * @since 16.10.2023
 */

@Composable
fun Modifier.shimmer(
    visible: Boolean = true,
    duration: Int = 800,
    initialValue: Float = 0f,
    targetValue: Float = 1000f,
    initialColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.6f),
    ),
    shape: Shape? = null
): Modifier {
    val transition = rememberInfiniteTransition(label = "ShimmerTransition")
    val animation = transition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(duration),
            repeatMode = RepeatMode.Reverse
        ), label = "ShimmerAnimation"
    )
    val brush = Brush.linearGradient(
        colors = initialColors,
        start = Offset.Zero,
        end = Offset(x = animation.value, y = animation.value)
    )
    if (visible) {
        if (shape != null) return background(brush = brush, shape = shape)
        return background(brush = brush)
    }
    return this
}

@Composable
fun TextSkeleton(modifier: Modifier = Modifier) {
    Text(text = "", modifier = modifier.shimmer(shape = RoundedCornerShape(8.dp)))
}

@Composable
fun GenericLoading(
    visible: Boolean = true,
    density: Density = LocalDensity.current
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shimmer(), text = ""
        )
        Box(
            modifier = Modifier
                .shimmer()
                .width(150.dp)
                .height(200.dp)
        )
    }

    /*Box {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
           Box(
               modifier= Modifier
                   .width(100.dp)
                   .width(200.dp)
                   .background(shimmerBrush())
           ) {

           }
        }

    }*/
}

@Composable
@Preview(showBackground = true)
fun GenericLoadingPreview() {
    TextSkeleton(modifier = Modifier.fillMaxWidth().padding(16.dp))
}