vue项目基于vuex来管理数据已经很成熟了，基于 vue - router - vuex 的技术栈也有很多应用。
vue与router都属于展现层，vuex才是真正的数据管理层，但如果项目复杂了，或者同一个产品多端进行开发
（如pc端使用 iview-admin，移动端使用vue，小程序使用mpvue），有大量的内容可以重复共用，把所有数据处理的逻辑都放在vuex显然不合适
