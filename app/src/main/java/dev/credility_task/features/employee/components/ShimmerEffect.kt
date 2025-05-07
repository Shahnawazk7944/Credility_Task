package dev.credility_task.features.employee.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import dev.designsystem.theme.CredilityTaskTheme
import dev.designsystem.theme.spacing

fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = "shimmerAlpha"
    ).value
    background(color = MaterialTheme.colorScheme.primary.copy(alpha = alpha))
}

@Composable
fun EmployeesScreenShimmerEffect(innerPadding: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top = 20.dp, start = 12.dp, end = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = MaterialTheme.spacing.extraLarge)
                .clip(RoundedCornerShape(60.dp))
                .background(MaterialTheme.colorScheme.surface)
                .shimmerEffect()
        )
        Spacer(Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            items(10) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .shimmerEffect(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(all = 20.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .size(285.dp, 243.dp)
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .height(25.dp)
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.extraLarge)
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    )
                    Spacer(Modifier.height(15.dp))
                    Box(
                        modifier = Modifier
                            .height(25.dp)
                            .width(100.dp)
                            .padding(start = MaterialTheme.spacing.extraLarge)
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    )
                    Spacer(Modifier.height(15.dp))
                    Box(
                        modifier = Modifier
                            .height(25.dp)
                            .width(100.dp)
                            .padding(start = MaterialTheme.spacing.extraLarge)
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    )
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}


@Composable
fun EmployeeListItemShimmer() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .weight(0.8f)
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.7f)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.9f)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))

                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.9f)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))

                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.5f)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
fun EmployeesScreenListShimmerEffect(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = MaterialTheme.spacing.medium)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(6) {
                EmployeeListItemShimmer()
            }
        }
    }
}


@PreviewLightDark
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShimmerEffectPreview() {
    CredilityTaskTheme {
        EmployeesScreenListShimmerEffect(PaddingValues())
    }
}