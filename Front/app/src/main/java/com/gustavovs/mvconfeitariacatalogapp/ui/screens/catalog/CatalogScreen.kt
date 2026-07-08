package com.gustavovs.mvconfeitariacatalogapp.ui.screens.catalog

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gustavovs.mvconfeitariacatalogapp.data.model.Cake
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onNavigateToAddCake: () -> Unit,
    onNavigateToEditCake: (Cake) -> Unit,
    viewModel: CatalogViewModel = viewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val dummyFocusRequester = remember { FocusRequester() }
    var cakeToDelete by remember { mutableStateOf<Cake?>(null) }

    // Load cakes on first entry
    LaunchedEffect(key1 = true) {
        viewModel.loadCakes()
        dummyFocusRequester.requestFocus()
    }

    // Clear focus and keyboard every time this screen resumes (e.g. navigating back)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                focusManager.clearFocus(force = true)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Color Palette
    val primaryGold = Color(0xFFD4AF37)
    val darkBrown = Color(0xFF3E2723)
    val backgroundCream = Color(0xFFFDF6F0)
    val primaryPink = Color(0xFFEC407A)
    val lightPink = Color(0xFFF8BBD0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "MV Confeitaria",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, viewModel.getFormattedCatalogText())
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Enviar Catálogo por:"
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Whatsapp,
                            contentDescription = "Compartilhar catálogo",
                            tint = Color.Green
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkBrown
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddCake,
                containerColor = primaryPink,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar Bolo"
                )
            }
        },
        containerColor = backgroundCream
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Invisible element to absorb automatic focus, preventing the search bar from stealing it
                Box(
                    modifier = Modifier
                        .size(1.dp)
                        .focusRequester(dummyFocusRequester)
                        .focusable()
                )
                // Elegant search bar and count
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(darkBrown)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) },
                        placeholder = {
                            Text(
                                "Pesquisar bolo pelo nome...",
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            if (viewModel.searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Limpar",
                                        tint = Color.White
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = primaryGold,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            cursorColor = primaryGold
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }

                // Error display
                AnimatedVisibility(visible = viewModel.errorMessage != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Erro",
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = viewModel.errorMessage ?: "",
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.weight(1f)
                            )
                            Button(
                                onClick = { viewModel.loadCakes() },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Tentar Novamente", color = Color.White)
                            }
                        }
                    }
                }

                // List of cakes
                if (viewModel.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = primaryPink)
                    }
                } else if (viewModel.cakes.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = darkBrown.copy(alpha = 0.5f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Nenhum bolo encontrado",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = darkBrown.copy(alpha = 0.7f)
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(viewModel.cakes, key = { it.id ?: 0L }) { cake ->
                            CakeCard(
                                cake = cake,
                                onEdit = { onNavigateToEditCake(cake) },
                                onDelete = { cakeToDelete = cake }
                            )
                        }
                    }
                }
            }

            // Deletion confirmation dialog
            cakeToDelete?.let { cake ->
                AlertDialog(
                    onDismissRequest = { cakeToDelete = null },
                    title = {
                        Text(
                            text = "Excluir Bolo",
                            fontWeight = FontWeight.Bold,
                            color = darkBrown
                        )
                    },
                    text = {
                        Text(
                            text = "Tem certeza que deseja excluir \"${cake.title}\"?",
                            color = Color.DarkGray
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                cake.id?.let { viewModel.deleteCake(it) }
                                cakeToDelete = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                        ) {
                            Text("Excluir", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { cakeToDelete = null },
                            colors = ButtonDefaults.textButtonColors(contentColor = darkBrown)
                        ) {
                            Text("Cancelar")
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    containerColor = Color.White
                )
            }
        }
    }
}

@Composable
fun CakeCard(
    cake: Cake,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    val formattedPrice = currencyFormatter.format(cake.price)

    val primaryGold = Color(0xFFD4AF37)
    val darkBrown = Color(0xFF3E2723)
    val primaryPink = Color(0xFFEC407A)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Elegant background top strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(darkBrown, Color(0xFF5D4037))
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cake.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$formattedPrice",
                        color = primaryGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Description if available
                if (!cake.description.isNullOrBlank()) {
                    Text(
                        text = cake.description,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Metadata (slices)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = primaryPink,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Rendimento: ${cake.slices} fatias",
                        color = darkBrown,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Card actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onEdit,
                        colors = ButtonDefaults.textButtonColors(contentColor = darkBrown)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Editar", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = onDelete,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Excluir", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
