history.pushstate 和 history.replacestate 是无法被监听的，所以需要重写


配合 Event，dispatchEvent 

```
var _wr = function(type) {
   var orig = history[type];
   return function() {
       var rv = orig.apply(this, arguments);
      var e = new Event(type);
       e.arguments = arguments;
       window.dispatchEvent(e);
       return rv;
   };
};
 history.pushState = _wr('pushState');
 history.replaceState = _wr('replaceState');

```
