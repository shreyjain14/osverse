package me.shreyjain.osverse.ui.components

import android.content.Context
import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import java.io.File
import java.io.IOException

@Composable
fun ArSceneView(
    modelPath: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sceneView = remember { SceneView(context) }
    
    DisposableEffect(Unit) {
        onDispose {
            sceneView.destroy()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            sceneView.apply {
                // Set up the scene
                scene = Scene(this)
                
                // Load and display the 3D model
                try {
                    ModelRenderable.builder()
                        .setSource(context, context.assets.open(modelPath).readBytes())
                        .setIsFilamentGltf(true)
                        .build()
                        .thenAccept { renderable ->
                            val anchorNode = AnchorNode().apply {
                                setParent(scene)
                            }
                            
                            TransformableNode(sceneView.transformationSystem).apply {
                                setParent(anchorNode)
                                renderable = renderable
                                select()
                            }
                        }
                        .exceptionally { throwable ->
                            throw IOException("Error loading model: $modelPath", throwable)
                        }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    )
} 