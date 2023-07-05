# DatabaseProjectConstruction.github.io
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>测试页面</title>
	</head>
	<body bgcolor="pink" align="center" text="white">
		<a href="https://www.yanfeisuoyi.top/">切换到主页面!</a>
		<h1>欢迎来到我的网站!</h1>
		<h2 id="nr0">欢迎来到我的主页!</h2>
		<h3 id="nr1">欢迎来到yanfeisuoyi的小屋!</h3>
		<hr width="100%" align="center" size="10"/>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<h4 id="nr2">欢迎来到我的 小屋!</h4>
		<h5>欢迎来到这里!</h5>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<p align="justify">在p标签中使用了无效的id属性，应该移除p标签中的id属性。
		在br>标签之后连续使用了多个br标签，没有必要重复使用这么多换行符。
		在footer标签中使用了无效的text属性，应该移除footer标签中的text属性。
		在title标签中应该填写具体的页面标题，而不是简单地写成"测试页面"。
		在body标签中使用了bgcolor属性来设置背景颜色，这种做法已经过时，更推荐使用CSS样式来设置元素的外观。
		在body标签中使用了align属性来设置内容居中，同样地，更好的做法是使用CSS样式来控制元素的对齐方式。
		在a>标签中使用了绝对路径作为链接地址，建议使用相对路径，以避免在将网站迁移到其他服务器时出现问题。
		在h1、h2、h3等标题标签内部包裹了p标签，这是不符合HTML规范的。应该直接使用标题标签，不需要额外的p标签进行包裹。
		在text标签中写入了文本内容，而text标签是无效的HTML标签，应该使用合适的标签（如p、span等）来包裹文本内容。
		以上是我对代码的分析和存在问题的总结，希望能对你有所帮助。如果还有其他问题，请随时提问。</p>
		<h2 align="center">无序列表</h2>
		<ul>
		<li align="left">张三</li>	
		<li align="left">李四</li>
		<li align="left">王五</li>
		</ul>
		<ul>
		<li align="center">张三</li>	
		<li align="center">李四</li>
		<li align="center">王五</li>
		</ul>
		<ul>
		<li align="right">张三</li>	
		<li align="right">李四</li>
		<li align="right">王五</li>
		</ul>
		<h2 align="center">有序列表</h2>
		<ol type="1">
		<li align="left">张三</li>	
		<li align="left">李四</li>
		<li align="left">王五</li>
		</ol>
		<ol type="A">
		<li align="center">张三</li>	
		<li align="center">李四</li>
		<li align="center">王五</li>
		</ol>
		<ol type="I">
		<li align="right">张三</li>	
		<li align="right">李四</li>
		<li align="right">王五</li>
		</ol>
		<div align="center">div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试div测试</div>
		<span>span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试span测试</span>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<a href="#nr1">跳转到yanfeisuoyi</a>
		<br>
		<pre>Hello                      world！</pre>
		<br>
		<strong>Hello                      world！</strong>
		<br>
		<p>Hello                        world!</p>
		<br>
		<b>Hello                      world！</b>
		<br>
		<font color="blue" size="5" face="宋体">你好!</font>
		<br>
		<i size="5">你好</i>
		<br>
		<u size="5">你好</u>
		<br>
		<del size="5"> 你好</del>
		<br>
		H<sub>2</sub>O
		<br>
		<p>y=x<sup>2</sup></p>
		<br>
		<img src="壁纸.jpg" title="背景" alt="图片加载错误!" width="400px" border="10"/>
		<br>
		<img src="https://pss.bdstatic.com/static/superman/img/logo/bd_logo1-66368c33f8.png" title="百度一下,你就知道!" alt="图片加载错误!">
		<br>
		<table width="50%" align="center" border="10" style="border-collapse: collapse;">
		<tr>
			<th>班级</th>
			<th>姓名</th>
			<th>性别</th>
			<th>年龄</th>
		</tr>
		<tr>
			<td>101</td>
			<td>张三</td>
			<td>男</td>
			<td>18</td>
		</tr>
		<tr>
			<td>101</td>
			<td>李四</td>
			<td>男</td>
			<td>19</td>
		</tr>
		<tr>
			<td>102</td>
			<td>王五</td>
			<td>女</td>
			<td>20</td>
		</tr>
		</table>
		<h3 align="center">横向合并colspan</h3>
		<table width="50%" align="center" border="10" style="border-collapse: collapse;">
		<tr>
			<th colspan="2">班级+姓名</th>
			<th>性别</th>
			<th>年龄</th>
		</tr>
		<tr>
			<td>101</td>
			<td>张三</td>
			<td>男</td>
			<td>18</td>
		</tr>
		<tr>
			<td>101</td>
			<td>李四</td>
			<td>男</td>
			<td>19</td>
		</tr>
		<tr>
			<td>102</td>
			<td>王五</td>
			<td>女</td>
			<td>20</td>
		</tr>
		</table>
		<h3 align="center">纵向合并rowspan</h3>
		<table width="50%" align="center" border="10" style="border-collapse: collapse;">
			<tr>
				<th>班级</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
			</tr>
			<tr>
				<td rowspan="2">101</td>
				<td>张三</td>
				<td>男</td>
				<td>18</td>
			</tr>
			<tr>
				<td>李四</td>
				<td>男</td>
				<td>19</td>
			</tr>
			<tr>
				<td>102</td>
				<td>王五</td>
				<td>女</td>
				<td>20</td>
			</tr>
		</table>
		<br>
		<form action="https://yanfeisuoyi.top" method="get"> target="_blank" 姓名<input type="text" name="test1"/><button>提交数据1</button>
		<form action="https://yanfeisuoyi.top" method="post"> target="_blank" 姓名<input type="text" name="test2"/><button>提交数据2</button>
		<br>
		<a href="#nr2">跳转到我的小屋</a>
		<br>
		<a href="#nr0">跳转到我的网页</a>
		<br>
		<p>Hello visitors!</p>
	</body>
	<footer>ICP主体备案号：鄂ICP备2023010483号</footer>
</html>
