package dev.credility_task.features.addEmployee.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun FormPagerProgressIndicator(
    currentPage: Int,
    totalPages: Int = 3,
    stepLabels: List<String> = listOf("Personal", "Employee", "Bank")
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items(totalPages) { index ->

            val isCurrent = index == currentPage
            val isCompleted = index < currentPage

            val backgroundColor = when {
                isCurrent -> colorScheme.primary
                isCompleted -> colorScheme.tertiary
                else -> Color.Gray.copy(alpha = 0.3f)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isCompleted) colorScheme.onTertiary else colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stepLabels.getOrNull(index) ?: "Step ${index + 1}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            if (index < totalPages - 1) {
                Spacer(modifier = Modifier.width(8.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .width(40.dp),
                    thickness = 2.dp,
                    color = if (index < currentPage) colorScheme.tertiary else Color.LightGray
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}