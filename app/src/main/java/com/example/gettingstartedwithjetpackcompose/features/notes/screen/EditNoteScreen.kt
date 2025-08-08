package com.example.gettingstartedwithjetpackcompose.features.notes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gettingstartedwithjetpackcompose.features.notes.viewModel.NotesHomeViewModel
import com.example.gettingstartedwithjetpackcompose.ui.theme.Roboto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@Composable
fun EditNoteScreen(
    noteId: Long,
    viewModel: NotesHomeViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val title by viewModel.noteTitle.collectAsState()
    val content by viewModel.noteContent.collectAsState()
    //val note by viewModel.note.collectAsState()

    val contentFocusRequester = remember { FocusRequester() }
    //val focusManager = LocalFocusManager.current


    // Load note when screen opens or noteId changes
    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    // Save when leaving screen
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearCurrentNote()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 16.dp)
    ) {
        IconButton(
            onClick = {
                // Save before navigating back
                viewModel.triggerAutoSave()
                onNavigateBack()
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier
            .padding(horizontal = 16.dp) .fillMaxSize() .verticalScroll(rememberScrollState())) {

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = title,
                onValueChange = viewModel::updateTitle,
                textStyle = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold,
                    color = Color.Black, fontFamily = Roboto),
                modifier = Modifier.fillMaxWidth() .padding(vertical = 8.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (title.isEmpty()) {
                        Text("Enter a title", style = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.Bold,
                            color = Color.DarkGray.copy(alpha = 0.7f), fontFamily = Roboto) )
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = { contentFocusRequester.requestFocus() }),
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = content,
                onValueChange = viewModel::updateContent,
                textStyle = TextStyle(fontSize = 18.sp, lineHeight = 24.sp,
                    color = Color.Black, fontFamily = Roboto),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .focusRequester(contentFocusRequester)
                    .defaultMinSize(minHeight = 300.dp),
                //.weight(1f),
                decorationBox = { innerTextField ->
                    if (content.isEmpty()) {
                        Text(
                            text = "Enter a note",
                            style = TextStyle( fontSize = 28.sp, fontWeight = FontWeight.Bold,
                            color = Color.DarkGray.copy(alpha = 0.7f), fontFamily = Roboto)
                        )
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    imeAction = ImeAction.Default,
                ),
            )
        }
    }
}
