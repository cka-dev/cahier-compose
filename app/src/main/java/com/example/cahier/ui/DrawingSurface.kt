/*
 *
 *  * Copyright 2025 Google LLC. All rights reserved.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.example.cahier.ui

import android.annotation.SuppressLint
import android.graphics.Matrix
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.graphics.withSave
import androidx.ink.authoring.compose.InProgressStrokes
import androidx.ink.brush.Brush
import androidx.ink.rendering.android.canvas.CanvasStrokeRenderer
import androidx.ink.strokes.Stroke
import com.example.cahier.data.CahierUiState

@SuppressLint("ClickableViewAccessibility")
@Composable
fun DrawingSurface(
    strokes: List<Stroke>,
    canvasStrokeRenderer: CanvasStrokeRenderer,
    selectedBrush: Brush?,
    onStrokesFinished: (List<Stroke>) -> Unit,
    uiState: CahierUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        InProgressStrokes(
            defaultBrush = selectedBrush,
            onStrokesFinished = onStrokesFinished
        )
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize()

            ) {
                Canvas(
                    modifier = Modifier
                ) {
                    val canvas = drawContext.canvas.nativeCanvas
                    strokes.forEach { stroke ->
                        canvas.withSave {
                            canvasStrokeRenderer.draw(
                                stroke = stroke,
                                canvas = this,
                                strokeToScreenTransform = Matrix()
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                if (uiState.note.imageUriList?.isNotEmpty() == true) {
                    NoteImagesView(
                        images = uiState.note.imageUriList,
                        onClearImages = { /*TODO*/ },
                    )
                }
            }

        }
    }
}