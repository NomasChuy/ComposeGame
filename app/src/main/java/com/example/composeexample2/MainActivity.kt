package com.example.composeexample2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeexample2.ui.theme.ComposeExample2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExample2Theme {

                val viewModel : MainViewModel = viewModel()

                Column{
                    ToolbarTitle("Aptivist Compose Challenge")
                    Spacer(modifier = Modifier.height(12.dp))
                    SwitchBoxButton(viewModel)
                    Body(modifier = Modifier.fillMaxSize(),viewModel)
                }
            }
        }
    }

    @Composable
    private fun Body(modifier: Modifier = Modifier, viewModel: MainViewModel) {
        var result by remember{
            mutableStateOf(1)
        }
        val diceImage = when(result){
            1 -> R.drawable.one
            2 -> R.drawable.two
            3 -> R.drawable.three
            4 -> R.drawable.four
            5 -> R.drawable.five
            else ->  R.drawable.six
        }

        val coinImage = when(result){
            1 -> R.drawable.heads
            else -> R.drawable.tails
        }

        val viewState = remember {
            viewModel.containerState
        }
        Column(modifier = modifier, verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .width(340.dp)
                    .height(350.dp)
            ) {
                when (viewState.value) {
                    MainViewContainerState.ViewDice -> {
                        Image(
                            painter = painterResource(id = diceImage),
                            contentDescription = null,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                    MainViewContainerState.ViewCoin -> {
                        Column {
                            Image(
                                painter = painterResource(id = coinImage),
                                contentDescription = null,
                                modifier = Modifier.size(400.dp)
                            )
                        }
                    }
                }
            }
            Button(onClick = {
                result = if (viewModel.containerState.value == MainViewContainerState.ViewDice) {
                    (1..6).random()
                } else {
                    (1..2).random()
                }
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)) {
                Text(text = if (viewModel.containerState.value == MainViewContainerState.ViewDice) "Lanzar Dado" else "Lanzar Moneda", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = if (viewModel.containerState.value == MainViewContainerState.ViewDice) result.toString() else {
                    when (result) {
                        1 -> "Cara"
                        else -> "Cruz"
                    }
                }
            )
        }
    }

    @Composable
    private fun SwitchBoxButton(viewModel: MainViewModel) {

        val viewState = remember{viewModel.containerState}

        Row(modifier = Modifier.fillMaxWidth())
        {
            Spacer(modifier = Modifier.width(6.dp))
            Tab(
                "Dado",
                Modifier.weight(1f),
                isSelected = viewState.value == MainViewContainerState.ViewDice
            ) {
                viewModel.switchViews(MainViewContainerState.ViewDice)
            }
            Spacer(modifier = Modifier.width(6.dp))
            Tab(
                "Moneda",
                Modifier.weight(1f),
                isSelected = viewState.value == MainViewContainerState.ViewCoin
            )
            {
                viewModel.switchViews(MainViewContainerState.ViewCoin)
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}

@Composable
fun ToolbarTitle(name: String) {
    TopAppBar(title = { Text(text = name, color = Color.White) }, backgroundColor = Color.DarkGray)
}

@Composable
fun Tab(name: String, modifier: Modifier = Modifier,isSelected : Boolean, onClick: () -> Unit) {
    Box(modifier = modifier
        .clickable { onClick.invoke() }
        .background(if (isSelected) Color.DarkGray else Color.LightGray)
        .height(60.dp)) {
        Text(
            text = name,
            modifier = Modifier.align(Center),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExample2Theme {
        //ToolbarTitle("Android")
    }
}