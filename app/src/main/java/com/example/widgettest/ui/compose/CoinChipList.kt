package com.example.widgettest.ui.compose

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.widgettest.data.CoinNameAndSymbol

@Composable
fun CoinChipList(
    coins: List<CoinNameAndSymbol>,
    onRemoveCoin: (coin: CoinNameAndSymbol) -> Unit,
    onSwapCoin: (fromIndex: Int, toIndex: Int) -> Unit
) {
    var draggedItemTop by remember {
        mutableFloatStateOf(0f)
    }
    var draggedItemBottom by remember {
        mutableFloatStateOf(0f)
    }
    var draggedItemIndex by remember {
        mutableIntStateOf(-1)
    }
    val isDragging = draggedItemIndex != -1
    val lazyColumnState = rememberLazyListState()
    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        itemsIndexed(coins, key = { _, coin -> coin.symbol }) { index, coin ->
            var dragOffsetY by remember {
                mutableFloatStateOf(0f)
            }
            var thisItemTop by remember {
                mutableFloatStateOf(0f)
            }
            var thisItemHeight by remember {
                mutableIntStateOf(0)
            }
            val thisItemBottom = thisItemTop + thisItemHeight
            val isDraggingThisItem = index == draggedItemIndex
            val paddingInPixels = with(LocalDensity.current) {
                4.dp.roundToPx()
            }
            val shouldShiftDown =
                isDragging && index < draggedItemIndex && draggedItemTop < thisItemBottom
            val shouldShiftUp =
                isDragging && index > draggedItemIndex && draggedItemBottom > thisItemTop
            val targetOffset = when {
                shouldShiftDown -> IntOffset(0, thisItemHeight + paddingInPixels)
                shouldShiftUp -> IntOffset(0, -thisItemHeight - paddingInPixels)
                else -> IntOffset(0, 0)
            }
            val animatedOffset by animateIntOffsetAsState(
                targetValue = targetOffset,
                label = "drag-offset"
            )
            CoinChip(
                modifier = Modifier
                    .onPlaced {
                        thisItemTop = it.positionInParent().y
                        thisItemHeight = it.size.height
                    }
                    .offset {
                        if (isDraggingThisItem) {
                            IntOffset(0, dragOffsetY.toInt())
                        } else if (isDragging) {
                            animatedOffset
                        } else {
                            targetOffset
                        }
                    }
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState(onDelta = { delta ->
                            dragOffsetY += delta
                            draggedItemTop = thisItemTop + dragOffsetY
                            draggedItemBottom = thisItemBottom + dragOffsetY
                        }),
                        onDragStarted = {
                            draggedItemIndex = index
                            draggedItemTop = thisItemTop
                            draggedItemBottom = thisItemBottom
                        },
                        onDragStopped = {
                            val fromIndex = draggedItemIndex
                            var targetItemIndex = fromIndex
                            for (itemLayout in lazyColumnState.layoutInfo.visibleItemsInfo) {
                                val itemIndex = itemLayout.index
                                if (itemIndex < fromIndex && draggedItemTop < itemLayout.offset + itemLayout.size && itemIndex < targetItemIndex) {
                                    targetItemIndex = itemIndex
                                }
                                if (itemIndex > fromIndex && draggedItemBottom > itemLayout.offset && itemIndex > targetItemIndex) {
                                    targetItemIndex = itemIndex
                                }
                            }
                            draggedItemIndex = -1
                            dragOffsetY = 0f
                            if (targetItemIndex != fromIndex) {
                                onSwapCoin(fromIndex, targetItemIndex)
                            }
                        }
                    ),
                symbol = coin.display(),
                isDragging = isDraggingThisItem,
                onRemove = {
                    onRemoveCoin(coin)
                }
            )
        }
    }
}