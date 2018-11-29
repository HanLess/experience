在这里我们把history简化成一个链表来讨论 ， 以下红色数字为 url 当前位置

#### 引起history变化的动作有三类：

<ul>
  <li>页面点击链接，js控制location.href跳转等，我们给这类起名为 <strong>硬跳转</strong> </li>
  <li>pushState</li>
  <li>replaceState</li>
</ul>

#### 引起当前位置在表中的变化有两种：前进、后退

#### 硬跳转：

[ 1 ,2 ,3 , 4, ... , n , n+1 , n+2 ]

1）history 表如上，通过回退的方式使当前 url 在 2 处

2）触发硬跳转，history变为如下：

[ 1 , 2 , 3 ]
当前位置为 3 ，url为硬跳转的链接

页面：

1）立即变化

2）接下来的前进后退，页面按 history 中的url顺序变化

#### pushState：

[ 1 , 2 , 3, ... , n , n+1 ]
1）history 表如上，当前位置在 2 处，触发pushState方法

2）history 表如下：

[ 1 , 2 , 3 ]
当前位置为 3 ，url为push进来的链接

页面：

1）不会立即变化，还是 2

2）接下来的前进后退，1与2都会正常加载页面，但当url到3时，页面仍是 2，这里没有找到原因，存疑！！！！！

#### replaceState：

[ 1 , 2 , 3 , ... , n , n+1 ]
1）history 表如上，当前位置在 2 处，触发 replaceState 方法

2）history 表如下：

[ 1 , 2 , 3 , ... , n , n+1 ]
当前位置在 2 处，且 url 为 replace 进来的 新url

页面：

1）页面不会立即变化

2）接下来的前进后退，页面按 history 中的url顺序变化

#### popstate：

需要注意的是，仅仅调用popstate方法或replaceState方法 ，并不会触发该事件，只有用户点击浏览器倒退按钮和前进按钮，或者使用JavaScript调用back、forward、go方法时才会触发。另外，该事件只针对同一个文档，如果浏览历史的切换，导致加载不同的文档，该事件也不会触发。
