# Roguelike Snake Game


## 第一部分：游戏简介

基于曹老师的模型完成的贪吃蛇小游戏，游戏中的蛇可以通过键盘上的上下左右键来控制移动方向，吃到食物后蛇的长度会增加，当蛇达到胜利所需的长度时胜利。
超时或者被敌人、陷阱单位抓到会瞬间输掉比赛。

与原先相比：
1. 增加了敌人单位，敌人单位会随机移动，由单独线程控制，当蛇与敌人单位相遇时，蛇会瞬间死亡。
2. 增加了陷阱单位，陷阱单位会随机移动，当蛇与陷阱单位相遇时，蛇会瞬间死亡。
3. 增加了胜利条件，当蛇的长度达到胜利所需的长度时，蛇会胜利。
4. 增加了胜利后的动画，当蛇胜利时，会有胜利的动画。
5. 增加了失败后的动画，当蛇失败时，会有失败的动画。
6. 增加了特殊果实。当蛇吃到特殊果实时，蛇的长度会增加，且获得额外buff(增加视野等)。
7. 允许自定义胜利所需的长度。
8. 修复了打印bug和视野穿墙的bug。
9. GUI界面的美化。

## 第二部分：游戏操作

视频链接：[https://www.bilibili.com/video/BV1gc411C7FH/](https://www.bilibili.com/video/BV1gc411C7FH/)

