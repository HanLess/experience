在dom中，node对象有12种type类型，element是其中之一

element继承node对象，type值为1

someEle.childNodes 返回node对象，someEle.children返回element对象

someEle.childNodes 列表中，会有大量 text 类型的节点，这是因为在写html结构时，输入了大量的空格、换行，这些空格，换行符被当做 text 类型的节点

someEle.children 返回的列表则全部返回 element 类型的对象
