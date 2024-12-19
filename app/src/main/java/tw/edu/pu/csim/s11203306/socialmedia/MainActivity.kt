package tw.edu.pu.csim.s11203306.socialmedia

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tw.edu.pu.csim.s11203306.socialmedia.ui.theme.SocialmediaTheme
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialmediaTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {
    val gameStarted = remember { mutableStateOf(false) }
    val context = LocalContext.current // 获取上下文

    // 创建 MediaPlayer 并播放音频
    LaunchedEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.start) // 确保音频文件名为 start.mp3，放置在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center // 按钮会被放置在中央
        ) {
            // 只有当游戏尚未开始时，显示背景图片
            if (!gameStarted.value) {
                // 添加背景图片，确保图片填满整个画面
                Image(
                    painter = painterResource(id = R.drawable.i1), // 确保这里使用的是您的图片名称 i1
                    contentDescription = "Background Image",
                    modifier = Modifier.fillMaxSize() // 背景图片会满版显示
                )
            }

            // 根据游戏是否开始显示不同内容
            if (gameStarted.value) {
                // 当游戏已经开始时，显示情境选择页面
                SituationSelection()
            } else {
                // 否则显示开始游戏的按钮
                Button(onClick = { gameStarted.value = true }) {
                    Text(text = "開始遊戲")
                }
            }
        }
    }
}

@Composable
fun SituationSelection() {
    val context = LocalContext.current

    // 使用 remember 初始化 navigateToSupermarket 和 navigateToSpecialSituation
    val navigateToSupermarket = remember { mutableStateOf(false) }
    val navigateToSpecialSituation = remember { mutableStateOf(false) }

    // 播放 "choose" 音频（页面打开时）
    LaunchedEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.choose) // 确保 choose.mp3 文件在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }

    // 设置背景图片
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 背景图片，只在底层显示
        Image(
            painter = painterResource(id = R.drawable.i2), // 替换为您的 i2 图片资源
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize() // 背景图片会满版显示
        )

        // 显示页面内容
        if (navigateToSupermarket.value) {
            // 当选择「日常情境」后，显示 SupermarketPage 页面
            SupermarketPage()
        } else if (navigateToSpecialSituation.value) {
            // 当选择「特殊情境」后，显示 SpecialSituationPage 页面
            SpecialSituationPage(context)
        } else {
            // 显示选择情境的页面
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 显示选择情境的提示文字
                Text(text = "請選擇一個情境", modifier = Modifier.padding(bottom = 24.dp))

                Button(onClick = {
                    playAudio(context, R.raw.daily) // 播放 "daily" 音频
                    navigateToSupermarket.value = true // 转换到日常情境页面
                }) {
                    Text(text = "日常情境")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    playAudio(context, R.raw.sapecial) // 播放 "special" 音频
                    navigateToSpecialSituation.value = true // 转换到特殊情境页面
                }) {
                    Text(text = "特殊情境")
                }
            }
        }
    }
}

// 播放音频的通用函数
fun playAudio(context: Context, resId: Int) {
    val mediaPlayer = MediaPlayer.create(context, resId)
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
}




@Composable
fun SupermarketPage() {
    val context = LocalContext.current
    val navigateToCustomerService = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") } // 用于存储显示的消息

    // 播放 "convinient" 音频（页面打开时，延迟3秒）
    LaunchedEffect(Unit) {
        delay(1000) // 延迟3秒
        val mediaPlayer = MediaPlayer.create(context, R.raw.convinient) // 确保 convinient.mp3 在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }
    val handler = remember { Handler(Looper.getMainLooper()) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.i3), // 使用图片 i3
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize() // 背景图片会满版显示
        )

        if (navigateToCustomerService.value) {
            CustomerServicePage(context)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "在超商找不到商品時，應該怎麼辦?",
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(onClick = {
                    playAudio(context, R.raw.askclerk) // 播放 "askclerk" 音频
                    navigateToCustomerService.value = true // 转换到客服页面
                }) {
                    Text(text = "詢問店員")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    playAudio(context, R.raw.move) // 播放 "move" 音频
                    message.value = "沒事的，再試試看！"
                    handler.postDelayed({
                        playAudio(context, R.raw.see) // 播放 "see" 音频
                    }, 2000) // 延迟2000毫秒（2秒）
                }) {
                    Text(text = "直接走人")
                }

                // 在此处根据状态变量显示消息，并设置颜色
                if (message.value.isNotEmpty()) {
                    Text(
                        text = message.value,
                        modifier = Modifier.padding(top = 24.dp),
                        color = Color.Red // 设置文字为红色
                    )

                }
            }
        }
    }
}

// 播放音频的通用函数






@Composable
fun CustomerServicePage(context: Context) {
    val navigateToGuidePage = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        delay(1000) // 延迟3秒
        val mediaPlayer = MediaPlayer.create(context, R.raw.welcome) // 确保 convinient.mp3 在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }
    val handler = remember { Handler(Looper.getMainLooper()) }
    if (navigateToGuidePage.value) {
        // 當點選「不好意思，請問冰塊在哪？」後，跳轉到下一頁
        GuidePage(context)
    } else {
        // 顯示問題和選擇
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 顯示問題
            Text(
                text = "您好，歡迎光臨!",
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 顯示兩個按鈕
            Button(onClick = {
                playAudio(context, R.raw.sorryice)
                navigateToGuidePage.value = true // 點選「不好意思，請問冰塊在哪？」後轉換頁面
            }) {
                Text(text = "不好意思，請問冰塊在哪?")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                playAudio(context, R.raw.ice)
                message.value = "沒事的，再試試看！"
                handler.postDelayed({
                    playAudio(context, R.raw.see) // 播放 "see" 音频
                }, 2000) // 延迟2000毫秒（2秒）
            }) {
                Text(text = "冰塊呢?")
            }

            // 顯示訊息
            if (message.value.isNotEmpty()) {
                Text(
                    text = message.value,
                    modifier = Modifier.padding(top = 24.dp),
                    color = Color.Red // 设置文字为红色
                )
            }
        }
    }
}

@Composable
fun GuidePage(context: Context) {
    val navigateToEndPage = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        delay(4000) // 延迟3秒
        val mediaPlayer = MediaPlayer.create(context, R.raw.takeyou) // 确保 convinient.mp3 在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }
    val handler = remember { Handler(Looper.getMainLooper()) }
    // 使用 Box 來顯示背景圖片
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 背景圖片：這裡設置 i4 背景
        Image(
            painter = painterResource(id = R.drawable.i4),  // 替換為您的 i4 圖片資源
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize() // 背景圖片會滿版顯示
        )

        // 顯示問題和選擇
        if (navigateToEndPage.value) {
            // 當點選「謝謝！」後，跳轉到結束頁面
            EndPage(context)
        } else {
            // 顯示問題和選擇
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 顯示問題
                Text(
                    text = "您好，這邊帶您去拿!",
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // 顯示兩個按鈕
                Button(onClick = {
                    playAudio(context, R.raw.thanks)
                    navigateToEndPage.value = true // 點選「謝謝！」後轉換頁面
                }) {
                    Text(text = "謝謝!")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    playAudio(context, R.raw.nosound)
                    message.value = "沒事的，再試試看！"
                    handler.postDelayed({
                        playAudio(context, R.raw.see) // 播放 "see" 音频
                    }, 2000) // 延迟2000毫秒（2秒）點選「不說話...」顯示訊息
                }) {
                    Text(text = "不說話...")
                }

                // 顯示訊息
                if (message.value.isNotEmpty()) {
                    Text(
                        text = message.value,
                        modifier = Modifier.padding(top = 24.dp),
                        color = Color.Red // 设置文字为红色
                    )
                }
            }
        }
    }
}

@Composable
fun EndPage(context: Context) {
    val navigateToHome = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(4000) // 延迟3秒
        val mediaPlayer = MediaPlayer.create(context, R.raw.congratulations) // 确保 convinient.mp3 在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }
    // 使用 Box 來顯示背景圖片
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 背景圖片：這裡設置 i2 背景
        Image(
            painter = painterResource(id = R.drawable.i2),  // 替換為您的 i2 圖片資源
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize() // 背景圖片會滿版顯示
        )

        // 顯示結束訊息和按鈕
        if (navigateToHome.value) {
            // 當點選「回首頁」後，跳轉到情境選擇頁面
            SituationSelection()
        } else {
            // 顯示結束訊息和「回首頁」按鈕
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "恭喜你完成這次對話挑戰！", modifier = Modifier.padding(bottom = 24.dp))

                // 「回首頁」按鈕
                Button(onClick = {
                    navigateToHome.value = true // 點選「回首頁」後回到情境選擇頁面
                }) {
                    Text(text = "回首頁")
                }
            }
        }
    }
}

@Composable
fun SpecialSituationPage(context:Context) {
    val navigateToReportPage = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        delay(2000) // 延迟3秒
        val mediaPlayer = MediaPlayer.create(context, R.raw.accident) // 确保 convinient.mp3 在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }
    val handler = remember { Handler(Looper.getMainLooper()) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 背景圖片：只顯示 i5 背景
        Image(
            painter = painterResource(id = R.drawable.i5),  // 替換為您的 i5 圖片資源
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize() // 背景圖片會滿版顯示
        )

        // 顯示情境問題和選項
        if (navigateToReportPage.value) {
            ReportPage(context)  // 當點選報警後進入下一頁
        } else {
            // 顯示情境問題和選項，這裡會顯示在背景之上
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 顯示問題
                Text(
                    text = "在路上發生車禍，應該如何處理?",
                    modifier = Modifier.padding(bottom = 24.dp),
                    color = Color.Black // 設定文字顏色為白色，讓它在背景上清晰顯示
                )

                // 顯示兩個按鈕
                Button(onClick = {
                    playAudio(context, R.raw.callpolice)
                    navigateToReportPage.value = true // 點選報警後進入下一頁
                }) {
                    Text(text = "報警")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    playAudio(context, R.raw.movecar)
                    message.value = "沒事的，再試試看！" // 點選直接開走顯示訊息
                    handler.postDelayed({
                        playAudio(context, R.raw.see) // 播放 "see" 音频
                    }, 2000) // 延迟2000毫秒（2秒）
                }) {
                    Text(text = "直接開走")
                }

                // 顯示訊息
                if (message.value.isNotEmpty()) {
                    Text(
                        text = message.value,
                        modifier = Modifier.padding(top = 24.dp),
                        color = Color.Red // 设置文字为红色
                    )
                }
            }
        }
    }
}


@Composable
fun ReportPage(context:Context) {
    val navigateToAccidentPage = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        delay(2000) // 延迟3秒
        val mediaPlayer = MediaPlayer.create(context, R.raw.calling) // 确保 convinient.mp3 在 res/raw 文件夹中
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }
    val handler = remember { Handler(Looper.getMainLooper()) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 背景圖片：只顯示 police 背景
        Image(
            painter = painterResource(id = R.drawable.police),  // 替換為您的 police 圖片資源
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize() // 背景圖片會滿版顯示
        )

        // 顯示問題和選項
        if (navigateToAccidentPage.value) {
            AccidentPage(context)  // 當點選進入車禍頁面後
        } else {
            // 顯示情境問題和選項，這裡會顯示在背景之上
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 顯示問題
                Text(
                    text = "報警時該說什麼?",
                    modifier = Modifier.padding(bottom = 24.dp),
                    color = Color.Black // 設定文字顏色為白色，讓它在背景上清晰顯示
                )

                // 顯示兩個按鈕
                Button(onClick = {
                    playAudio(context, R.raw.place)
                    navigateToAccidentPage.value = true // 點選「我在發生車禍的地方發生車禍了」後進入下一頁
                }) {
                    Text(text = "我在(發生車禍的地方)發生車禍了")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    playAudio(context, R.raw.here)
                    message.value = "沒事的，再試試看！"
                    handler.postDelayed({
                        playAudio(context, R.raw.see) // 播放 "see" 音频
                    }, 3000) // 延迟2000毫秒（2秒）
                }) {
                    Text(text = "我在這裡出車禍了")
                }

                // 顯示訊息
                if (message.value.isNotEmpty()) {
                    Text(
                        text = message.value,
                        modifier = Modifier.padding(top = 24.dp),
                        color = Color.Red // 设置文字为红色
                    )
                }
            }
        }
    }
}


@Composable
fun AccidentPage(context: Context) {
    val navigateToEndPage = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") }

    // 播放警察到达的音频（延迟1500毫秒）
    LaunchedEffect(Unit) {
        delay(3000) // 延迟1.5秒
        val mediaPlayer = MediaPlayer.create(context, R.raw.policearrive) // 播放警察到达音频
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() } // 释放资源
    }
    val handler = remember { Handler(Looper.getMainLooper()) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 背景图片：显示 car 背景
        Image(
            painter = painterResource(id = R.drawable.car),  // 替换为您的 car 图片资源
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize() // 背景图片会满版显示
        )

        // 显示事故问题和选项
        if (navigateToEndPage.value) {
            EndPage(context)  // 当点击进入结束页面后
        } else {
            // 显示情境问题和选项，这里会显示在背景之上
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 显示问题
                Text(
                    text = "警察到現場要說什麼?",
                    modifier = Modifier.padding(bottom = 24.dp),
                    color = Color.Black // 设置文字颜色为黑色，保证在背景上清晰显示
                )

                // 显示两个按钮
                Button(onClick = {
                    playAudio(context, R.raw.right) // 播放右转音频
                    navigateToEndPage.value = true // 点击「我在右转时跟直行车擦撞了」后转换页面
                }) {
                    Text(text = "我在右轉時跟直行車擦撞了。")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    playAudio(context, R.raw.crushfalse)
                    // 播放错误音频
                    // 在 SupermarketPage 和 CustomerServicePage 等地方找到相应的 Text 组件
                    message.value = "沒事的，再試試看！" // 更新消息为 "没事的，再试试看！"
                    // 播放 "see" 音频
                    handler.postDelayed({
                        playAudio(context, R.raw.see) // 播放 "see" 音频
                    }, 3000) // 延迟2000毫秒（2秒）
                }) {
                    Text(text = "我剛剛撞到他了!")
                }

                // 显示消息
                if (message.value.isNotEmpty()) {
                    Text(
                        text = message.value,
                        modifier = Modifier.padding(top = 24.dp),
                        color = Color.Red // 设置文字为红色
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SocialmediaTheme {
        GameScreen()
    }
}

