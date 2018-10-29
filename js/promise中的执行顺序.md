1）
```
new Promise(function(resolve,reject){
	console.log("one")
	resolve()
	console.log('four')
}).then(function(){
    console.log("two")
}).catch(function(){
    console.log("err")
})
console.log("three")

// one four three two
```
2)
```
new Promise(function(resolve,reject){
	console.log("one")
	reject()
	console.log('four')
}).then(function(){
    console.log("two")
}).catch(function(){
    console.log("err")
})
console.log("three")

// one four three err
```
3)
```
new Promise(function(resolve,reject){
	console.log("one")
	throw new Error();
	
	console.log('four')
}).then(function(){
    console.log("two")
}).catch(function(){
    console.log("err")
})
console.log("three")

// one three err
```
