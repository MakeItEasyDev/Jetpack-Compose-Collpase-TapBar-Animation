package com.jetpack.collapsetapbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.collapsetapbar.ui.theme.CollapseTapBarTheme

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollapseTapBarTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Collapse TapBar",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            CollapseTapBar(
                                modifier = Modifier.padding(start = 30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun CollapseTapBar(
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    AnimatedContent(
        targetState = expanded,
        transitionSpec = {
            if (targetState) {
                fadeIn(animationSpec = tween(700)) with
                        fadeOut(animationSpec = tween(300))
            } else {
                fadeIn(animationSpec = tween(300)) with
                        fadeOut(animationSpec = tween(500))
            }.using(SizeTransform(sizeAnimationSpec = { _, _ ->
                tween(600)
            }))
        },
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = Color.Black.copy(alpha = 0.7f),
                shape = RoundedCornerShape(percent = 100)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    expanded = !expanded
                }
            )
    ) { targetExpanded ->
        if (!targetExpanded) {
            Box(contentAlignment = Alignment.Center) {
                val infiniteTransition = rememberInfiniteTransition()
                val size by infiniteTransition.animateFloat(
                    initialValue = 40f,
                    targetValue = 72f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                val animatedAlpha by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000),
                        repeatMode = RepeatMode.Restart
                    )
                )

                Box(
                    modifier = Modifier
                        .size(size.dp)
                        .graphicsLayer {
                            alpha = animatedAlpha
                        }
                        .background(
                            brush = Brush.radialGradient(
                                0.4f to Color.Red.copy(alpha = 0f),
                                0.6f to Color.Red.copy(alpha = 0.5f),
                                1.0f to Color.Red
                            ),
                            shape = CircleShape
                        )
                )
                Box(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.StopCircle,
                        contentDescription = "Stop Icon",
                        modifier = Modifier.size(48.dp),
                        tint = Color.Red
                    )
                }
            }
        } else {
            val icon = listOf(
                Icons.Outlined.Backspace,
                Icons.Outlined.Keyboard,
                Icons.Outlined.GraphicEq,
                Icons.Outlined.Camera,
                Icons.Outlined.Share
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 36.dp, vertical = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                icon.forEach { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = "List of image",
                        modifier = Modifier.size(36.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}














