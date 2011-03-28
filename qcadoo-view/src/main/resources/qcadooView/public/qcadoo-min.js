/*
 * jQuery JavaScript Library v1.4.2
 * http://jquery.com/
 *
 * Copyright 2010, John Resig
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * Includes Sizzle.js
 * http://sizzlejs.com/
 * Copyright 2010, The Dojo Foundation
 * Released under the MIT, BSD, and GPL Licenses.
 *
 * Date: Sat Feb 13 22:33:48 2010 -0500
 */
(function(aO,I){function a0(){if(!ah.isReady){try{M.documentElement.doScroll("left")
}catch(c){setTimeout(a0,1);
return
}ah.ready()
}}function E(s,c){c.src?ah.ajax({url:c.src,async:false,dataType:"script"}):ah.globalEval(c.text||c.textContent||c.innerHTML||"");
c.parentNode&&c.parentNode.removeChild(c)
}function ap(s,c,K,F,G,w){var A=s.length;
if(typeof c==="object"){for(var J in c){ap(s,J,c[J],F,G,K)
}return s
}if(K!==I){F=!w&&F&&ah.isFunction(K);
for(J=0;
J<A;
J++){G(s[J],c,F?K.call(s[J],J,G(s[J],c)):K,w)
}return s
}return A?G(s[0],c):I
}function aF(){return(new Date).getTime()
}function ao(){return false
}function am(){return true
}function aK(s,c,w){w[0].type=s;
return ah.event.handle.apply(c,w)
}function ag(O){var N,L=[],J=[],K=arguments,F,G,s,A,w,c;
G=ah.data(this,"events");
if(!(O.liveFired===this||!G||!G.live||O.button&&O.type==="click")){O.liveFired=this;
var P=G.live.slice(0);
for(A=0;
A<P.length;
A++){G=P[A];
G.origType.replace(az,"")===O.type?J.push(G.selector):P.splice(A--,1)
}F=ah(O.target).closest(J,O.currentTarget);
w=0;
for(c=F.length;
w<c;
w++){for(A=0;
A<P.length;
A++){G=P[A];
if(F[w].selector===G.selector){s=F[w].elem;
J=null;
if(G.preType==="mouseenter"||G.preType==="mouseleave"){J=ah(O.relatedTarget).closest(G.selector)[0]
}if(!J||J!==s){L.push({elem:s,handleObj:G})
}}}}w=0;
for(c=L.length;
w<c;
w++){F=L[w];
O.currentTarget=F.elem;
O.data=F.handleObj.data;
O.handleObj=F.handleObj;
if(F.handleObj.origHandler.apply(F.elem,K)===false){N=false;
break
}}return N
}}function z(s,c){return"live."+(s&&s!=="*"?s+".":"")+c.replace(/\./g,"`").replace(/ /g,"&")
}function l(c){return !c||!c.parentNode||c.parentNode.nodeType===11
}function bj(s,c){var w=0;
c.each(function(){if(this.nodeName===(s[w]&&s[w].nodeName)){var G=ah.data(s[w++]),J=ah.data(this,G);
if(G=G&&G.events){delete J.handle;
J.events={};
for(var A in G){for(var F in G[A]){ah.event.add(this,A,G[A][F],G[A][F].data)
}}}}})
}function a3(s,c,G){var A,F,w;
c=c&&c[0]?c[0].ownerDocument||c[0]:M;
if(s.length===1&&typeof s[0]==="string"&&s[0].length<512&&c===M&&!aP.test(s[0])&&(ah.support.checkClone||!ak.test(s[0]))){F=true;
if(w=ah.fragments[s[0]]){if(w!==1){A=w
}}}if(!A){A=c.createDocumentFragment();
ah.clean(s,c,A,G)
}if(F){ah.fragments[s[0]]=w?A:1
}return{fragment:A,cacheable:F}
}function aC(s,c){var w={};
ah.each(D.concat.apply([],D.slice(0,c)),function(){w[this]=s
});
return w
}function o(c){return"scrollTo" in c&&c.document?c:c.nodeType===9?c.defaultView||c.parentWindow:false
}var ah=function(s,c){return new ah.fn.init(s,c)
},p=aO.jQuery,d=aO.$,M=aO.document,at,a7=/^[^<]*(<[\w\W]+>)[^>]*$|^#([\w-]+)$/,aT=/^.[^:#\[\.,]*$/,an=/\S/,H=/^(\s|\u00A0)+|(\s|\u00A0)+$/g,q=/^<(\w+)\s*\/?>(?:<\/\1>)?$/,ax=navigator.userAgent,b=false,av=[],aB,a1=Object.prototype.toString,aV=Object.prototype.hasOwnProperty,ay=Array.prototype.push,au=Array.prototype.slice,a6=Array.prototype.indexOf;
ah.fn=ah.prototype={init:function(s,c){var A,w;
if(!s){return this
}if(s.nodeType){this.context=this[0]=s;
this.length=1;
return this
}if(s==="body"&&!c){this.context=M;
this[0]=M.body;
this.selector="body";
this.length=1;
return this
}if(typeof s==="string"){if((A=a7.exec(s))&&(A[1]||!c)){if(A[1]){w=c?c.ownerDocument||c:M;
if(s=q.exec(s)){if(ah.isPlainObject(c)){s=[M.createElement(s[1])];
ah.fn.attr.call(s,c,true)
}else{s=[w.createElement(s[1])]
}}else{s=a3([A[1]],[w]);
s=(s.cacheable?s.fragment.cloneNode(true):s.fragment).childNodes
}return ah.merge(this,s)
}else{if(c=M.getElementById(A[2])){if(c.id!==A[2]){return at.find(s)
}this.length=1;
this[0]=c
}this.context=M;
this.selector=s;
return this
}}else{if(!c&&/^\w+$/.test(s)){this.selector=s;
this.context=M;
s=M.getElementsByTagName(s);
return ah.merge(this,s)
}else{return !c||c.jquery?(c||at).find(s):ah(c).find(s)
}}}else{if(ah.isFunction(s)){return at.ready(s)
}}if(s.selector!==I){this.selector=s.selector;
this.context=s.context
}return ah.makeArray(s,this)
},selector:"",jquery:"1.4.2",length:0,size:function(){return this.length
},toArray:function(){return au.call(this,0)
},get:function(c){return c==null?this.toArray():c<0?this.slice(c)[0]:this[c]
},pushStack:function(s,c,A){var w=ah();
ah.isArray(s)?ay.apply(w,s):ah.merge(w,s);
w.prevObject=this;
w.context=this.context;
if(c==="find"){w.selector=this.selector+(this.selector?" ":"")+A
}else{if(c){w.selector=this.selector+"."+c+"("+A+")"
}}return w
},each:function(s,c){return ah.each(this,s,c)
},ready:function(c){ah.bindReady();
if(ah.isReady){c.call(M,ah)
}else{av&&av.push(c)
}return this
},eq:function(c){return c===-1?this.slice(c):this.slice(c,+c+1)
},first:function(){return this.eq(0)
},last:function(){return this.eq(-1)
},slice:function(){return this.pushStack(au.apply(this,arguments),"slice",au.call(arguments).join(","))
},map:function(c){return this.pushStack(ah.map(this,function(s,w){return c.call(s,w,s)
}))
},end:function(){return this.prevObject||ah(null)
},push:ay,sort:[].sort,splice:[].splice};
ah.fn.init.prototype=ah.fn;
ah.extend=ah.fn.extend=function(){var s=arguments[0]||{},c=1,K=arguments.length,F=false,G,w,A,J;
if(typeof s==="boolean"){F=s;
s=arguments[1]||{};
c=2
}if(typeof s!=="object"&&!ah.isFunction(s)){s={}
}if(K===c){s=this;
--c
}for(;
c<K;
c++){if((G=arguments[c])!=null){for(w in G){A=s[w];
J=G[w];
if(s!==J){if(F&&J&&(ah.isPlainObject(J)||ah.isArray(J))){A=A&&(ah.isPlainObject(A)||ah.isArray(A))?A:ah.isArray(J)?[]:{};
s[w]=ah.extend(F,A,J)
}else{if(J!==I){s[w]=J
}}}}}}return s
};
ah.extend({noConflict:function(c){aO.$=d;
if(c){aO.jQuery=p
}return ah
},isReady:false,ready:function(){if(!ah.isReady){if(!M.body){return setTimeout(ah.ready,13)
}ah.isReady=true;
if(av){for(var s,c=0;
s=av[c++];
){s.call(M,ah)
}av=null
}ah.fn.triggerHandler&&ah(M).triggerHandler("ready")
}},bindReady:function(){if(!b){b=true;
if(M.readyState==="complete"){return ah.ready()
}if(M.addEventListener){M.addEventListener("DOMContentLoaded",aB,false);
aO.addEventListener("load",ah.ready,false)
}else{if(M.attachEvent){M.attachEvent("onreadystatechange",aB);
aO.attachEvent("onload",ah.ready);
var s=false;
try{s=aO.frameElement==null
}catch(c){}M.documentElement.doScroll&&s&&a0()
}}}},isFunction:function(c){return a1.call(c)==="[object Function]"
},isArray:function(c){return a1.call(c)==="[object Array]"
},isPlainObject:function(s){if(!s||a1.call(s)!=="[object Object]"||s.nodeType||s.setInterval){return false
}if(s.constructor&&!aV.call(s,"constructor")&&!aV.call(s.constructor.prototype,"isPrototypeOf")){return false
}var c;
for(c in s){}return c===I||aV.call(s,c)
},isEmptyObject:function(s){for(var c in s){return false
}return true
},error:function(c){throw c
},parseJSON:function(c){if(typeof c!=="string"||!c){return null
}c=ah.trim(c);
if(/^[\],:{}\s]*$/.test(c.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){return aO.JSON&&aO.JSON.parse?aO.JSON.parse(c):(new Function("return "+c))()
}else{ah.error("Invalid JSON: "+c)
}},noop:function(){},globalEval:function(s){if(s&&an.test(s)){var c=M.getElementsByTagName("head")[0]||M.documentElement,w=M.createElement("script");
w.type="text/javascript";
if(ah.support.scriptEval){w.appendChild(M.createTextNode(s))
}else{w.text=s
}c.insertBefore(w,c.firstChild);
c.removeChild(w)
}},nodeName:function(s,c){return s.nodeName&&s.nodeName.toUpperCase()===c.toUpperCase()
},each:function(s,c,J){var F,G=0,w=s.length,A=w===I||ah.isFunction(s);
if(J){if(A){for(F in s){if(c.apply(s[F],J)===false){break
}}}else{for(;
G<w;
){if(c.apply(s[G++],J)===false){break
}}}}else{if(A){for(F in s){if(c.call(s[F],F,s[F])===false){break
}}}else{for(J=s[0];
G<w&&c.call(J,G,J)!==false;
J=s[++G]){}}}return s
},trim:function(c){return(c||"").replace(H,"")
},makeArray:function(s,c){c=c||[];
if(s!=null){s.length==null||typeof s==="string"||ah.isFunction(s)||typeof s!=="function"&&s.setInterval?ay.call(c,s):ah.merge(c,s)
}return c
},inArray:function(s,c){if(c.indexOf){return c.indexOf(s)
}for(var A=0,w=c.length;
A<w;
A++){if(c[A]===s){return A
}}return -1
},merge:function(s,c){var F=s.length,w=0;
if(typeof c.length==="number"){for(var A=c.length;
w<A;
w++){s[F++]=c[w]
}}else{for(;
c[w]!==I;
){s[F++]=c[w++]
}}s.length=F;
return s
},grep:function(s,c,G){for(var A=[],F=0,w=s.length;
F<w;
F++){!G!==!c(s[F],F)&&A.push(s[F])
}return A
},map:function(s,c,J){for(var F=[],G,w=0,A=s.length;
w<A;
w++){G=c(s[w],w,J);
if(G!=null){F[F.length]=G
}}return F.concat.apply([],F)
},guid:1,proxy:function(s,c,w){if(arguments.length===2){if(typeof c==="string"){w=s;
s=w[c];
c=I
}else{if(c&&!ah.isFunction(c)){w=c;
c=I
}}}if(!c&&s){c=function(){return s.apply(w||this,arguments)
}
}if(s){c.guid=s.guid=s.guid||c.guid||ah.guid++
}return c
},uaMatch:function(c){c=c.toLowerCase();
c=/(webkit)[ \/]([\w.]+)/.exec(c)||/(opera)(?:.*version)?[ \/]([\w.]+)/.exec(c)||/(msie) ([\w.]+)/.exec(c)||!/compatible/.test(c)&&/(mozilla)(?:.*? rv:([\w.]+))?/.exec(c)||[];
return{browser:c[1]||"",version:c[2]||"0"}
},browser:{}});
ax=ah.uaMatch(ax);
if(ax.browser){ah.browser[ax.browser]=true;
ah.browser.version=ax.version
}if(ah.browser.webkit){ah.browser.safari=true
}if(a6){ah.inArray=function(s,c){return a6.call(c,s)
}
}at=ah(M);
if(M.addEventListener){aB=function(){M.removeEventListener("DOMContentLoaded",aB,false);
ah.ready()
}
}else{if(M.attachEvent){aB=function(){if(M.readyState==="complete"){M.detachEvent("onreadystatechange",aB);
ah.ready()
}}
}}(function(){ah.support={};
var L=M.documentElement,K=M.createElement("script"),J=M.createElement("div"),F="script"+aF();
J.style.display="none";
J.innerHTML="   <link/><table></table><a href='/a' style='color:red;float:left;opacity:.55;'>a</a><input type='checkbox'/>";
var G=J.getElementsByTagName("*"),w=J.getElementsByTagName("a")[0];
if(!(!G||!G.length||!w)){ah.support={leadingWhitespace:J.firstChild.nodeType===3,tbody:!J.getElementsByTagName("tbody").length,htmlSerialize:!!J.getElementsByTagName("link").length,style:/red/.test(w.getAttribute("style")),hrefNormalized:w.getAttribute("href")==="/a",opacity:/^0.55$/.test(w.style.opacity),cssFloat:!!w.style.cssFloat,checkOn:J.getElementsByTagName("input")[0].value==="on",optSelected:M.createElement("select").appendChild(M.createElement("option")).selected,parentNode:J.removeChild(J.appendChild(M.createElement("div"))).parentNode===null,deleteExpando:true,checkClone:false,scriptEval:false,noCloneEvent:true,boxModel:null};
K.type="text/javascript";
try{K.appendChild(M.createTextNode("window."+F+"=1;"))
}catch(A){}L.insertBefore(K,L.firstChild);
if(aO[F]){ah.support.scriptEval=true;
delete aO[F]
}try{delete K.test
}catch(c){ah.support.deleteExpando=false
}L.removeChild(K);
if(J.attachEvent&&J.fireEvent){J.attachEvent("onclick",function s(){ah.support.noCloneEvent=false;
J.detachEvent("onclick",s)
});
J.cloneNode(true).fireEvent("onclick")
}J=M.createElement("div");
J.innerHTML="<input type='radio' name='radiotest' checked='checked'/>";
L=M.createDocumentFragment();
L.appendChild(J.firstChild);
ah.support.checkClone=L.cloneNode(true).cloneNode(true).lastChild.checked;
ah(function(){var N=M.createElement("div");
N.style.width=N.style.paddingLeft="1px";
M.body.appendChild(N);
ah.boxModel=ah.support.boxModel=N.offsetWidth===2;
M.body.removeChild(N).style.display="none"
});
L=function(N){var P=M.createElement("div");
N="on"+N;
var O=N in P;
if(!O){P.setAttribute(N,"return;");
O=typeof P[N]==="function"
}return O
};
ah.support.submitBubbles=L("submit");
ah.support.changeBubbles=L("change");
L=K=J=G=w=null
}})();
ah.props={"for":"htmlFor","class":"className",readonly:"readOnly",maxlength:"maxLength",cellspacing:"cellSpacing",rowspan:"rowSpan",colspan:"colSpan",tabindex:"tabIndex",usemap:"useMap",frameborder:"frameBorder"};
var aH="jQuery"+aF(),e=0,aS={};
ah.extend({cache:{},expando:aH,noData:{embed:true,object:true,applet:true},data:function(s,c,F){if(!(s.nodeName&&ah.noData[s.nodeName.toLowerCase()])){s=s==aO?aS:s;
var w=s[aH],A=ah.cache;
if(!w&&typeof c==="string"&&F===I){return null
}w||(w=++e);
if(typeof c==="object"){s[aH]=w;
A[w]=ah.extend(true,{},c)
}else{if(!A[w]){s[aH]=w;
A[w]={}
}}s=A[w];
if(F!==I){s[c]=F
}return typeof c==="string"?s[c]:s
}},removeData:function(s,c){if(!(s.nodeName&&ah.noData[s.nodeName.toLowerCase()])){s=s==aO?aS:s;
var F=s[aH],w=ah.cache,A=w[F];
if(c){if(A){delete A[c];
ah.isEmptyObject(A)&&ah.removeData(s)
}}else{if(ah.support.deleteExpando){delete s[ah.expando]
}else{s.removeAttribute&&s.removeAttribute(ah.expando)
}delete w[F]
}}}});
ah.fn.extend({data:function(s,c){if(typeof s==="undefined"&&this.length){return ah.data(this[0])
}else{if(typeof s==="object"){return this.each(function(){ah.data(this,s)
})
}}var A=s.split(".");
A[1]=A[1]?"."+A[1]:"";
if(c===I){var w=this.triggerHandler("getData"+A[1]+"!",[A[0]]);
if(w===I&&this.length){w=ah.data(this[0],s)
}return w===I&&A[1]?this.data(A[0]):w
}else{return this.trigger("setData"+A[1]+"!",[A[0],c]).each(function(){ah.data(this,s,c)
})
}},removeData:function(c){return this.each(function(){ah.removeData(this,c)
})
}});
ah.extend({queue:function(s,c,A){if(s){c=(c||"fx")+"queue";
var w=ah.data(s,c);
if(!A){return w||[]
}if(!w||ah.isArray(A)){w=ah.data(s,c,ah.makeArray(A))
}else{w.push(A)
}return w
}},dequeue:function(s,c){c=c||"fx";
var A=ah.queue(s,c),w=A.shift();
if(w==="inprogress"){w=A.shift()
}if(w){c==="fx"&&A.unshift("inprogress");
w.call(s,function(){ah.dequeue(s,c)
})
}}});
ah.fn.extend({queue:function(s,c){if(typeof s!=="string"){c=s;
s="fx"
}if(c===I){return ah.queue(this[0],s)
}return this.each(function(){var w=ah.queue(this,s,c);
s==="fx"&&w[0]!=="inprogress"&&ah.dequeue(this,s)
})
},dequeue:function(c){return this.each(function(){ah.dequeue(this,c)
})
},delay:function(s,c){s=ah.fx?ah.fx.speeds[s]||s:s;
c=c||"fx";
return this.queue(c,function(){var w=this;
setTimeout(function(){ah.dequeue(w,c)
},s)
})
},clearQueue:function(c){return this.queue(c||"fx",[])
}});
var be=/[\n\t]/g,U=/\s+/,a8=/\r/g,aM=/href|src|style/,aU=/(button|input)/i,aw=/(button|input|object|select|textarea)/i,S=/^(a|area)$/i,aY=/radio|checkbox/;
ah.fn.extend({attr:function(s,c){return ap(this,s,c,true,ah.attr)
},removeAttr:function(c){return this.each(function(){ah.attr(this,c,"");
this.nodeType===1&&this.removeAttribute(c)
})
},addClass:function(L){if(ah.isFunction(L)){return this.each(function(O){var N=ah(this);
N.addClass(L.call(this,O,N.attr("class")))
})
}if(L&&typeof L==="string"){for(var K=(L||"").split(U),J=0,F=this.length;
J<F;
J++){var G=this[J];
if(G.nodeType===1){if(G.className){for(var w=" "+G.className+" ",A=G.className,c=0,s=K.length;
c<s;
c++){if(w.indexOf(" "+K[c]+" ")<0){A+=" "+K[c]
}}G.className=ah.trim(A)
}else{G.className=L
}}}}return this
},removeClass:function(s){if(ah.isFunction(s)){return this.each(function(L){var N=ah(this);
N.removeClass(s.call(this,L,N.attr("class")))
})
}if(s&&typeof s==="string"||s===I){for(var c=(s||"").split(U),K=0,F=this.length;
K<F;
K++){var G=this[K];
if(G.nodeType===1&&G.className){if(s){for(var w=(" "+G.className+" ").replace(be," "),A=0,J=c.length;
A<J;
A++){w=w.replace(" "+c[A]+" "," ")
}G.className=ah.trim(w)
}else{G.className=""
}}}}return this
},toggleClass:function(s,c){var A=typeof s,w=typeof c==="boolean";
if(ah.isFunction(s)){return this.each(function(G){var F=ah(this);
F.toggleClass(s.call(this,G,F.attr("class"),c),c)
})
}return this.each(function(){if(A==="string"){for(var K,G=0,J=ah(this),L=c,F=s.split(U);
K=F[G++];
){L=w?L:!J.hasClass(K);
J[L?"addClass":"removeClass"](K)
}}else{if(A==="undefined"||A==="boolean"){this.className&&ah.data(this,"__className__",this.className);
this.className=this.className||s===false?"":ah.data(this,"__className__")||""
}}})
},hasClass:function(s){s=" "+s+" ";
for(var c=0,w=this.length;
c<w;
c++){if((" "+this[c].className+" ").replace(be," ").indexOf(s)>-1){return true
}}return false
},val:function(s){if(s===I){var c=this[0];
if(c){if(ah.nodeName(c,"option")){return(c.attributes.value||{}).specified?c.value:c.text
}if(ah.nodeName(c,"select")){var K=c.selectedIndex,F=[],G=c.options;
c=c.type==="select-one";
if(K<0){return null
}var w=c?K:0;
for(K=c?K+1:G.length;
w<K;
w++){var A=G[w];
if(A.selected){s=ah(A).val();
if(c){return s
}F.push(s)
}}return F
}if(aY.test(c.type)&&!ah.support.checkOn){return c.getAttribute("value")===null?"on":c.value
}return(c.value||"").replace(a8,"")
}return I
}var J=ah.isFunction(s);
return this.each(function(L){var P=ah(this),O=s;
if(this.nodeType===1){if(J){O=s.call(this,L,P.val())
}if(typeof O==="number"){O+=""
}if(ah.isArray(O)&&aY.test(this.type)){this.checked=ah.inArray(P.val(),O)>=0
}else{if(ah.nodeName(this,"select")){var N=ah.makeArray(O);
ah("option",this).each(function(){this.selected=ah.inArray(ah(this).val(),N)>=0
});
if(!N.length){this.selectedIndex=-1
}}else{this.value=O
}}}})
}});
ah.extend({attrFn:{val:true,css:true,html:true,text:true,data:true,width:true,height:true,offset:true},attr:function(s,c,G,A){if(!s||s.nodeType===3||s.nodeType===8){return I
}if(A&&c in ah.attrFn){return ah(s)[c](G)
}A=s.nodeType!==1||!ah.isXMLDoc(s);
var F=G!==I;
c=A&&ah.props[c]||c;
if(s.nodeType===1){var w=aM.test(c);
if(c in s&&A&&!w){if(F){c==="type"&&aU.test(s.nodeName)&&s.parentNode&&ah.error("type property can't be changed");
s[c]=G
}if(ah.nodeName(s,"form")&&s.getAttributeNode(c)){return s.getAttributeNode(c).nodeValue
}if(c==="tabIndex"){return(c=s.getAttributeNode("tabIndex"))&&c.specified?c.value:aw.test(s.nodeName)||S.test(s.nodeName)&&s.href?0:I
}return s[c]
}if(!ah.support.style&&A&&c==="style"){if(F){s.style.cssText=""+G
}return s.style.cssText
}F&&s.setAttribute(c,""+G);
s=!ah.support.hrefNormalized&&A&&w?s.getAttribute(c,2):s.getAttribute(c);
return s===null?I:s
}return ah.style(s,c,G)
}});
var az=/\.(.*)$/,r=function(c){return c.replace(/[^\w\s\.\|`]/g,function(s){return"\\"+s
})
};
ah.event={add:function(P,O,L,J){if(!(P.nodeType===3||P.nodeType===8)){if(P.setInterval&&P!==aO&&!P.frameElement){P=aO
}var K,F;
if(L.handler){K=L;
L=K.handler
}if(!L.guid){L.guid=ah.guid++
}if(F=ah.data(P)){var G=F.events=F.events||{},s=F.handle;
if(!s){F.handle=s=function(){return typeof ah!=="undefined"&&!ah.event.triggered?ah.event.handle.apply(s.elem,arguments):I
}
}s.elem=P;
O=O.split(" ");
for(var A,w=0,c;
A=O[w++];
){F=K?ah.extend({},K):{handler:L,data:J};
if(A.indexOf(".")>-1){c=A.split(".");
A=c.shift();
F.namespace=c.slice(0).sort().join(".")
}else{c=[];
F.namespace=""
}F.type=A;
F.guid=L.guid;
var Q=G[A],N=ah.event.special[A]||{};
if(!Q){Q=G[A]=[];
if(!N.setup||N.setup.call(P,J,c,s)===false){if(P.addEventListener){P.addEventListener(A,s,false)
}else{P.attachEvent&&P.attachEvent("on"+A,s)
}}}if(N.add){N.add.call(P,F);
if(!F.handler.guid){F.handler.guid=L.guid
}}Q.push(F);
ah.event.global[A]=true
}P=null
}}},global:{},remove:function(R,Q,O,L){if(!(R.nodeType===3||R.nodeType===8)){var N,J=0,K,A,G,F,c,T,P=ah.data(R),s=P&&P.events;
if(P&&s){if(Q&&Q.type){O=Q.handler;
Q=Q.type
}if(!Q||typeof Q==="string"&&Q.charAt(0)==="."){Q=Q||"";
for(N in s){ah.event.remove(R,N+Q)
}}else{for(Q=Q.split(" ");
N=Q[J++];
){F=N;
K=N.indexOf(".")<0;
A=[];
if(!K){A=N.split(".");
N=A.shift();
G=new RegExp("(^|\\.)"+ah.map(A.slice(0).sort(),r).join("\\.(?:.*\\.)?")+"(\\.|$)")
}if(c=s[N]){if(O){F=ah.event.special[N]||{};
for(w=L||0;
w<c.length;
w++){T=c[w];
if(O.guid===T.guid){if(K||G.test(T.namespace)){L==null&&c.splice(w--,1);
F.remove&&F.remove.call(R,T)
}if(L!=null){break
}}}if(c.length===0||L!=null&&c.length===1){if(!F.teardown||F.teardown.call(R,A)===false){aG(R,N,P.handle)
}delete s[N]
}}else{for(var w=0;
w<c.length;
w++){T=c[w];
if(K||G.test(T.namespace)){ah.event.remove(R,F,T.handler,w);
c.splice(w--,1)
}}}}}if(ah.isEmptyObject(s)){if(Q=P.handle){Q.elem=null
}delete P.events;
delete P.handle;
ah.isEmptyObject(P)&&ah.removeData(R)
}}}}},trigger:function(N,L,K,G){var J=N.type||N;
if(!G){N=typeof N==="object"?N[aH]?N:ah.extend(ah.Event(J),N):ah.Event(J);
if(J.indexOf("!")>=0){N.type=J=J.slice(0,-1);
N.exclusive=true
}if(!K){N.stopPropagation();
ah.event.global[J]&&ah.each(ah.cache,function(){this.events&&this.events[J]&&ah.event.trigger(N,L,this.handle.elem)
})
}if(!K||K.nodeType===3||K.nodeType===8){return I
}N.result=I;
N.target=K;
L=ah.makeArray(L);
L.unshift(N)
}N.currentTarget=K;
(G=ah.data(K,"handle"))&&G.apply(K,L);
G=K.parentNode||K.ownerDocument;
try{if(!(K&&K.nodeName&&ah.noData[K.nodeName.toLowerCase()])){if(K["on"+J]&&K["on"+J].apply(K,L)===false){N.result=false
}}}catch(A){}if(!N.isPropagationStopped()&&G){ah.event.trigger(N,L,G,true)
}else{if(!N.isDefaultPrevented()){G=N.target;
var F,c=ah.nodeName(G,"a")&&J==="click",w=ah.event.special[J]||{};
if((!w._default||w._default.call(K,N)===false)&&!c&&!(G&&G.nodeName&&ah.noData[G.nodeName.toLowerCase()])){try{if(G[J]){if(F=G["on"+J]){G["on"+J]=null
}ah.event.triggered=true;
G[J]()
}}catch(s){}if(F){G["on"+J]=F
}ah.event.triggered=false
}}}},handle:function(s){var c,J,F,G;
s=arguments[0]=ah.event.fix(s||aO.event);
s.currentTarget=this;
c=s.type.indexOf(".")<0&&!s.exclusive;
if(!c){J=s.type.split(".");
s.type=J.shift();
F=new RegExp("(^|\\.)"+J.slice(0).sort().join("\\.(?:.*\\.)?")+"(\\.|$)")
}G=ah.data(this,"events");
J=G[s.type];
if(G&&J){J=J.slice(0);
G=0;
for(var w=J.length;
G<w;
G++){var A=J[G];
if(c||F.test(A.namespace)){s.handler=A.handler;
s.data=A.data;
s.handleObj=A;
A=A.handler.apply(this,arguments);
if(A!==I){s.result=A;
if(A===false){s.preventDefault();
s.stopPropagation()
}}if(s.isImmediatePropagationStopped()){break
}}}}return s.result
},props:"altKey attrChange attrName bubbles button cancelable XcharCode clientX clientY ctrlKey currentTarget data detail eventPhase fromElement handler keyCode layerX layerY metaKey newValue offsetX offsetY originalTarget pageX pageY prevValue relatedNode relatedTarget screenX screenY shiftKey srcElement target toElement view wheelDelta which".split(" "),fix:function(s){if(s[aH]){return s
}var c=s;
s=ah.Event(c);
for(var A=this.props.length,w;
A;
){w=this.props[--A];
s[w]=c[w]
}if(!s.target){s.target=s.srcElement||M
}if(s.target.nodeType===3){s.target=s.target.parentNode
}if(!s.relatedTarget&&s.fromElement){s.relatedTarget=s.fromElement===s.target?s.toElement:s.fromElement
}if(s.pageX==null&&s.clientX!=null){c=M.documentElement;
A=M.body;
s.pageX=s.clientX+(c&&c.scrollLeft||A&&A.scrollLeft||0)-(c&&c.clientLeft||A&&A.clientLeft||0);
s.pageY=s.clientY+(c&&c.scrollTop||A&&A.scrollTop||0)-(c&&c.clientTop||A&&A.clientTop||0)
}if(!s.which&&(s.XcharCode||s.XcharCode===0?s.XcharCode:s.keyCode)){s.which=s.XcharCode||s.keyCode
}if(!s.metaKey&&s.ctrlKey){s.metaKey=s.ctrlKey
}if(!s.which&&s.button!==I){s.which=s.button&1?1:s.button&2?3:s.button&4?2:0
}return s
},guid:100000000,proxy:ah.proxy,special:{ready:{setup:ah.bindReady,teardown:ah.noop},live:{add:function(c){ah.event.add(this,c.origType,ah.extend({},c,{handler:ag}))
},remove:function(s){var c=true,w=s.origType.replace(az,"");
ah.each(ah.data(this,"events").live||[],function(){if(w===this.origType.replace(az,"")){return c=false
}});
c&&ah.event.remove(this,s.origType,ag)
}},beforeunload:{setup:function(s,c,w){if(this.setInterval){this.onbeforeunload=w
}return false
},teardown:function(s,c){if(this.onbeforeunload===c){this.onbeforeunload=null
}}}}};
var aG=M.removeEventListener?function(s,c,w){s.removeEventListener(c,w,false)
}:function(s,c,w){s.detachEvent("on"+c,w)
};
ah.Event=function(c){if(!this.preventDefault){return new ah.Event(c)
}if(c&&c.type){this.originalEvent=c;
this.type=c.type
}else{this.type=c
}this.timeStamp=aF();
this[aH]=true
};
ah.Event.prototype={preventDefault:function(){this.isDefaultPrevented=am;
var c=this.originalEvent;
if(c){c.preventDefault&&c.preventDefault();
c.returnValue=false
}},stopPropagation:function(){this.isPropagationStopped=am;
var c=this.originalEvent;
if(c){c.stopPropagation&&c.stopPropagation();
c.cancelBubble=true
}},stopImmediatePropagation:function(){this.isImmediatePropagationStopped=am;
this.stopPropagation()
},isDefaultPrevented:ao,isPropagationStopped:ao,isImmediatePropagationStopped:ao};
var ae=function(s){var c=s.relatedTarget;
try{for(;
c&&c!==this;
){c=c.parentNode
}if(c!==this){s.type=s.data;
ah.event.handle.apply(this,arguments)
}}catch(w){}},x=function(c){c.type=c.data;
ah.event.handle.apply(this,arguments)
};
ah.each({mouseenter:"mouseover",mouseleave:"mouseout"},function(s,c){ah.event.special[s]={setup:function(w){ah.event.add(this,c,w&&w.selector?x:ae,s)
},teardown:function(w){ah.event.remove(this,c,w&&w.selector?x:ae)
}}
});
if(!ah.support.submitBubbles){ah.event.special.submit={setup:function(){if(this.nodeName.toLowerCase()!=="form"){ah.event.add(this,"click.specialSubmit",function(s){var c=s.target,w=c.type;
if((w==="submit"||w==="image")&&ah(c).closest("form").length){return aK("submit",this,arguments)
}});
ah.event.add(this,"keypress.specialSubmit",function(s){var c=s.target,w=c.type;
if((w==="text"||w==="password")&&ah(c).closest("form").length&&s.keyCode===13){return aK("submit",this,arguments)
}})
}else{return false
}},teardown:function(){ah.event.remove(this,".specialSubmit")
}}
}if(!ah.support.changeBubbles){var t=/textarea|input|select/i,g,j=function(s){var c=s.type,w=s.value;
if(c==="radio"||c==="checkbox"){w=s.checked
}else{if(c==="select-multiple"){w=s.selectedIndex>-1?ah.map(s.options,function(A){return A.selected
}).join("-"):""
}else{if(s.nodeName.toLowerCase()==="select"){w=s.selectedIndex
}}}return w
},bd=function(s,c){var F=s.target,w,A;
if(!(!t.test(F.nodeName)||F.readOnly)){w=ah.data(F,"_change_data");
A=j(F);
if(s.type!=="focusout"||F.type!=="radio"){ah.data(F,"_change_data",A)
}if(!(w===I||A===w)){if(w!=null||A){s.type="change";
return ah.event.trigger(s,c,F)
}}}};
ah.event.special.change={filters:{focusout:bd,click:function(s){var c=s.target,w=c.type;
if(w==="radio"||w==="checkbox"||c.nodeName.toLowerCase()==="select"){return bd.call(this,s)
}},keydown:function(s){var c=s.target,w=c.type;
if(s.keyCode===13&&c.nodeName.toLowerCase()!=="textarea"||s.keyCode===32&&(w==="checkbox"||w==="radio")||w==="select-multiple"){return bd.call(this,s)
}},beforeactivate:function(c){c=c.target;
ah.data(c,"_change_data",j(c))
}},setup:function(){if(this.type==="file"){return false
}for(var c in g){ah.event.add(this,c+".specialChange",g[c])
}return t.test(this.nodeName)
},teardown:function(){ah.event.remove(this,".specialChange");
return t.test(this.nodeName)
}};
g=ah.event.special.change.filters
}M.addEventListener&&ah.each({focus:"focusin",blur:"focusout"},function(s,c){function w(A){A=ah.event.fix(A);
A.type=c;
return ah.event.handle.call(this,A)
}ah.event.special[c]={setup:function(){this.addEventListener(s,w,true)
},teardown:function(){this.removeEventListener(s,w,true)
}}
});
ah.each(["bind","one"],function(s,c){ah.fn[c]=function(K,F,G){if(typeof K==="object"){for(var w in K){this[c](w,F,K[w],G)
}return this
}if(ah.isFunction(F)){G=F;
F=I
}var A=c==="one"?ah.proxy(G,function(L){ah(this).unbind(L,A);
return G.apply(this,arguments)
}):G;
if(K==="unload"&&c!=="one"){this.one(K,F,G)
}else{w=0;
for(var J=this.length;
w<J;
w++){ah.event.add(this[w],K,A,F)
}}return this
}
});
ah.fn.extend({unbind:function(s,c){if(typeof s==="object"&&!s.preventDefault){for(var A in s){this.unbind(A,s[A])
}}else{A=0;
for(var w=this.length;
A<w;
A++){ah.event.remove(this[A],s,c)
}}return this
},delegate:function(s,c,A,w){return this.live(c,A,w,s)
},undelegate:function(s,c,w){return arguments.length===0?this.unbind("live"):this.die(c,null,w,s)
},trigger:function(s,c){return this.each(function(){ah.event.trigger(s,c,this)
})
},triggerHandler:function(s,c){if(this[0]){s=ah.Event(s);
s.preventDefault();
s.stopPropagation();
ah.event.trigger(s,c,this[0]);
return s.result
}},toggle:function(s){for(var c=arguments,w=1;
w<c.length;
){ah.proxy(s,c[w++])
}return this.click(ah.proxy(s,function(A){var F=(ah.data(this,"lastToggle"+s.guid)||0)%w;
ah.data(this,"lastToggle"+s.guid,F+1);
A.preventDefault();
return c[F].apply(this,arguments)||false
}))
},hover:function(s,c){return this.mouseenter(s).mouseleave(c||s)
}});
var bh={focus:"focusin",blur:"focusout",mouseenter:"mouseover",mouseleave:"mouseout"};
ah.each(["live","die"],function(s,c){ah.fn[c]=function(O,L,N,J){var K,A=0,G,F,w=J||this.selector,P=J?this:ah(this.context);
if(ah.isFunction(L)){N=L;
L=I
}for(O=(O||"").split(" ");
(K=O[A++])!=null;
){J=az.exec(K);
G="";
if(J){G=J[0];
K=K.replace(az,"")
}if(K==="hover"){O.push("mouseenter"+G,"mouseleave"+G)
}else{F=K;
if(K==="focus"||K==="blur"){O.push(bh[K]+G);
K+=G
}else{K=(bh[K]||K)+G
}c==="live"?P.each(function(){ah.event.add(this,z(K,w),{data:L,selector:w,handler:N,origType:K,origHandler:N,preType:F})
}):P.unbind(z(K,w),N)
}}return this
}
});
ah.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error".split(" "),function(s,c){ah.fn[c]=function(w){return w?this.bind(c,w):this.trigger(c)
};
if(ah.attrFn){ah.attrFn[c]=true
}});
aO.attachEvent&&!aO.addEventListener&&aO.attachEvent("onunload",function(){for(var s in ah.cache){if(ah.cache[s].handle){try{ah.event.remove(ah.cache[s].handle.elem)
}catch(c){}}}});
(function(){function W(ab){for(var aa="",Z,Y=0;
ab[Y];
Y++){Z=ab[Y];
if(Z.nodeType===3||Z.nodeType===4){aa+=Z.nodeValue
}else{if(Z.nodeType!==8){aa+=W(Z.childNodes)
}}}return aa
}function V(bb,ba,ab,aa,Y,Z){Y=0;
for(var bm=aa.length;
Y<bm;
Y++){var bn=aa[Y];
if(bn){bn=bn[bb];
for(var bl=false;
bn;
){if(bn.sizcache===ab){bl=aa[bn.sizset];
break
}if(bn.nodeType===1&&!Z){bn.sizcache=ab;
bn.sizset=Y
}if(bn.nodeName.toLowerCase()===ba){bl=bn;
break
}bn=bn[bb]
}aa[Y]=bl
}}}function T(bb,ba,ab,aa,Y,Z){Y=0;
for(var bm=aa.length;
Y<bm;
Y++){var bn=aa[Y];
if(bn){bn=bn[bb];
for(var bl=false;
bn;
){if(bn.sizcache===ab){bl=aa[bn.sizset];
break
}if(bn.nodeType===1){if(!Z){bn.sizcache=ab;
bn.sizset=Y
}if(typeof ba!=="string"){if(bn===ba){bl=true;
break
}}else{if(N.filter(ba,[bn]).length>0){bl=bn;
break
}}}bn=bn[bb]
}aa[Y]=bl
}}}var Q=/((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^[\]]*\]|['"][^'"]*['"]|[^[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?((?:.|\r|\n)*)/g,R=0,O=Object.prototype.toString,P=false,K=true;
[0,0].sort(function(){K=false;
return 0
});
var N=function(bm,bl,ba,ab){ba=ba||[];
var Z=bl=bl||M;
if(bl.nodeType!==1&&bl.nodeType!==9){return[]
}if(!bm||typeof bm!=="string"){return ba
}for(var aa=[],br,bs,bo,bb,bq=true,bn=s(bl),bp=bm;
(Q.exec(""),br=Q.exec(bp))!==null;
){bp=br[3];
aa.push(br[1]);
if(br[2]){bb=br[3];
break
}}if(aa.length>1&&G.exec(bm)){if(aa.length===2&&L.relative[aa[0]]){bs=X(aa[0]+aa[1],bl)
}else{for(bs=L.relative[aa[0]]?[bl]:N(aa.shift(),bl);
aa.length;
){bm=aa.shift();
if(L.relative[bm]){bm+=aa.shift()
}bs=X(bm,bs)
}}}else{if(!ab&&aa.length>1&&bl.nodeType===9&&!bn&&L.match.ID.test(aa[0])&&!L.match.ID.test(aa[aa.length-1])){br=N.find(aa.shift(),bl,bn);
bl=br.expr?N.filter(br.expr,br.set)[0]:br.set[0]
}if(bl){br=ab?{expr:aa.pop(),set:c(ab)}:N.find(aa.pop(),aa.length===1&&(aa[0]==="~"||aa[0]==="+")&&bl.parentNode?bl.parentNode:bl,bn);
bs=br.expr?N.filter(br.expr,br.set):br.set;
if(aa.length>0){bo=c(bs)
}else{bq=false
}for(;
aa.length;
){var Y=aa.pop();
br=Y;
if(L.relative[Y]){br=aa.pop()
}else{Y=""
}if(br==null){br=bl
}L.relative[Y](bo,br,bn)
}}else{bo=[]
}}bo||(bo=bs);
bo||N.error(Y||bm);
if(O.call(bo)==="[object Array]"){if(bq){if(bl&&bl.nodeType===1){for(bm=0;
bo[bm]!=null;
bm++){if(bo[bm]&&(bo[bm]===true||bo[bm].nodeType===1&&A(bl,bo[bm]))){ba.push(bs[bm])
}}}else{for(bm=0;
bo[bm]!=null;
bm++){bo[bm]&&bo[bm].nodeType===1&&ba.push(bs[bm])
}}}else{ba.push.apply(ba,bo)
}}else{c(bo,ba)
}if(bb){N(bb,Z,ba,ab);
N.uniqueSort(ba)
}return ba
};
N.uniqueSort=function(Z){if(J){P=K;
Z.sort(J);
if(P){for(var Y=1;
Y<Z.length;
Y++){Z[Y]===Z[Y-1]&&Z.splice(Y--,1)
}}}return Z
};
N.matches=function(Z,Y){return N(Z,null,null,Y)
};
N.find=function(bb,ba,ab){var aa,Y;
if(!bb){return[]
}for(var Z=0,bm=L.order.length;
Z<bm;
Z++){var bn=L.order[Z];
if(Y=L.leftMatch[bn].exec(bb)){var bl=Y[1];
Y.splice(1,1);
if(bl.substr(bl.length-1)!=="\\"){Y[1]=(Y[1]||"").replace(/\\/g,"");
aa=L.find[bn](Y,ba,ab);
if(aa!=null){bb=bb.replace(L.match[bn],"");
break
}}}}aa||(aa=ba.getElementsByTagName("*"));
return{set:aa,expr:bb}
};
N.filter=function(bn,bm,bb,ab){for(var Z=bn,aa=[],bt=bm,bu,bq,bl=bm&&bm[0]&&s(bm[0]);
bn&&bm.length;
){for(var bs in L.filter){if((bu=L.leftMatch[bs].exec(bn))!=null&&bu[2]){var bo=L.filter[bs],br,Y;
Y=bu[1];
bq=false;
bu.splice(1,1);
if(Y.substr(Y.length-1)!=="\\"){if(bt===aa){aa=[]
}if(L.preFilter[bs]){if(bu=L.preFilter[bs](bu,bt,bb,aa,ab,bl)){if(bu===true){continue
}}else{bq=br=true
}}if(bu){for(var ba=0;
(Y=bt[ba])!=null;
ba++){if(Y){br=bo(Y,bu,ba,bt);
var bp=ab^!!br;
if(bb&&br!=null){if(bp){bq=true
}else{bt[ba]=false
}}else{if(bp){aa.push(Y);
bq=true
}}}}}if(br!==I){bb||(bt=aa);
bn=bn.replace(L.match[bs],"");
if(!bq){return[]
}break
}}}}if(bn===Z){if(bq==null){N.error(bn)
}else{break
}}Z=bn
}return bt
};
N.error=function(Y){throw"Syntax error, unrecognized expression: "+Y
};
var L=N.selectors={order:["ID","NAME","TAG"],match:{ID:/#((?:[\w\u00c0-\uFFFF-]|\\.)+)/,CLASS:/\.((?:[\w\u00c0-\uFFFF-]|\\.)+)/,NAME:/\[name=['"]*((?:[\w\u00c0-\uFFFF-]|\\.)+)['"]*\]/,ATTR:/\[\s*((?:[\w\u00c0-\uFFFF-]|\\.)+)\s*(?:(\S?=)\s*(['"]*)(.*?)\3|)\s*\]/,TAG:/^((?:[\w\u00c0-\uFFFF\*-]|\\.)+)/,CHILD:/:(only|nth|last|first)-child(?:\((even|odd|[\dn+-]*)\))?/,POS:/:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^-]|$)/,PSEUDO:/:((?:[\w\u00c0-\uFFFF-]|\\.)+)(?:\((['"]?)((?:\([^\)]+\)|[^\(\)]*)+)\2\))?/},leftMatch:{},attrMap:{"class":"className","for":"htmlFor"},attrHandle:{href:function(Y){return Y.getAttribute("href")
}},relative:{"+":function(ab,aa){var Z=typeof aa==="string",Y=Z&&!/\W/.test(aa);
Z=Z&&!Y;
if(Y){aa=aa.toLowerCase()
}Y=0;
for(var ba=ab.length,bb;
Y<ba;
Y++){if(bb=ab[Y]){for(;
(bb=bb.previousSibling)&&bb.nodeType!==1;
){}ab[Y]=Z||bb&&bb.nodeName.toLowerCase()===aa?bb||false:bb===aa
}}Z&&N.filter(aa,ab,true)
},">":function(ab,aa){var Z=typeof aa==="string";
if(Z&&!/\W/.test(aa)){aa=aa.toLowerCase();
for(var Y=0,ba=ab.length;
Y<ba;
Y++){var bb=ab[Y];
if(bb){Z=bb.parentNode;
ab[Y]=Z.nodeName.toLowerCase()===aa?Z:false
}}}else{Y=0;
for(ba=ab.length;
Y<ba;
Y++){if(bb=ab[Y]){ab[Y]=Z?bb.parentNode:bb.parentNode===aa
}}Z&&N.filter(aa,ab,true)
}},"":function(ab,aa,Z){var Y=R++,ba=T;
if(typeof aa==="string"&&!/\W/.test(aa)){var bb=aa=aa.toLowerCase();
ba=V
}ba("parentNode",aa,Y,ab,bb,Z)
},"~":function(ab,aa,Z){var Y=R++,ba=T;
if(typeof aa==="string"&&!/\W/.test(aa)){var bb=aa=aa.toLowerCase();
ba=V
}ba("previousSibling",aa,Y,ab,bb,Z)
}},find:{ID:function(aa,Z,Y){if(typeof Z.getElementById!=="undefined"&&!Y){return(aa=Z.getElementById(aa[1]))?[aa]:[]
}},NAME:function(ab,aa){if(typeof aa.getElementsByName!=="undefined"){var Z=[];
aa=aa.getElementsByName(ab[1]);
for(var Y=0,ba=aa.length;
Y<ba;
Y++){aa[Y].getAttribute("name")===ab[1]&&Z.push(aa[Y])
}return Z.length===0?null:Z
}},TAG:function(Z,Y){return Y.getElementsByTagName(Z[1])
}},preFilter:{CLASS:function(ba,ab,Z,Y,bb,bl){ba=" "+ba[1].replace(/\\/g,"")+" ";
if(bl){return ba
}bl=0;
for(var aa;
(aa=ab[bl])!=null;
bl++){if(aa){if(bb^(aa.className&&(" "+aa.className+" ").replace(/[\t\n]/g," ").indexOf(ba)>=0)){Z||Y.push(aa)
}else{if(Z){ab[bl]=false
}}}}return false
},ID:function(Y){return Y[1].replace(/\\/g,"")
},TAG:function(Y){return Y[1].toLowerCase()
},CHILD:function(Z){if(Z[1]==="nth"){var Y=/(-?)(\d*)n((?:\+|-)?\d*)/.exec(Z[2]==="even"&&"2n"||Z[2]==="odd"&&"2n+1"||!/\D/.test(Z[2])&&"0n+"+Z[2]||Z[2]);
Z[2]=Y[1]+(Y[2]||1)-0;
Z[3]=Y[3]-0
}Z[0]=R++;
return Z
},ATTR:function(ab,aa,Z,Y,ba,bb){aa=ab[1].replace(/\\/g,"");
if(!bb&&L.attrMap[aa]){ab[1]=L.attrMap[aa]
}if(ab[2]==="~="){ab[4]=" "+ab[4]+" "
}return ab
},PSEUDO:function(ab,aa,Z,Y,ba){if(ab[1]==="not"){if((Q.exec(ab[3])||"").length>1||/^\w/.test(ab[3])){ab[3]=N(ab[3],null,null,aa)
}else{ab=N.filter(ab[3],aa,Z,true^ba);
Z||Y.push.apply(Y,ab);
return false
}}else{if(L.match.POS.test(ab[0])||L.match.CHILD.test(ab[0])){return true
}}return ab
},POS:function(Y){Y.unshift(true);
return Y
}},filters:{enabled:function(Y){return Y.disabled===false&&Y.type!=="hidden"
},disabled:function(Y){return Y.disabled===true
},checked:function(Y){return Y.checked===true
},selected:function(Y){return Y.selected===true
},parent:function(Y){return !!Y.firstChild
},empty:function(Y){return !Y.firstChild
},has:function(aa,Z,Y){return !!N(Y[3],aa).length
},header:function(Y){return/h\d/i.test(Y.nodeName)
},text:function(Y){return"text"===Y.type
},radio:function(Y){return"radio"===Y.type
},checkbox:function(Y){return"checkbox"===Y.type
},file:function(Y){return"file"===Y.type
},password:function(Y){return"password"===Y.type
},submit:function(Y){return"submit"===Y.type
},image:function(Y){return"image"===Y.type
},reset:function(Y){return"reset"===Y.type
},button:function(Y){return"button"===Y.type||Y.nodeName.toLowerCase()==="button"
},input:function(Y){return/input|select|textarea|button/i.test(Y.nodeName)
}},setFilters:{first:function(Z,Y){return Y===0
},last:function(ab,aa,Z,Y){return aa===Y.length-1
},even:function(Z,Y){return Y%2===0
},odd:function(Z,Y){return Y%2===1
},lt:function(aa,Z,Y){return Z<Y[3]-0
},gt:function(aa,Z,Y){return Z>Y[3]-0
},nth:function(aa,Z,Y){return Y[3]-0===Z
},eq:function(aa,Z,Y){return Y[3]-0===Z
}},filter:{PSEUDO:function(ab,aa,Z,Y){var ba=aa[1],bb=L.filters[ba];
if(bb){return bb(ab,Z,aa,Y)
}else{if(ba==="contains"){return(ab.textContent||ab.innerText||W([ab])||"").indexOf(aa[3])>=0
}else{if(ba==="not"){aa=aa[3];
Z=0;
for(Y=aa.length;
Z<Y;
Z++){if(aa[Z]===ab){return false
}}return true
}else{N.error("Syntax error, unrecognized expression: "+ba)
}}}},CHILD:function(ba,ab){var Z=ab[1],Y=ba;
switch(Z){case"only":case"first":for(;
Y=Y.previousSibling;
){if(Y.nodeType===1){return false
}}if(Z==="first"){return true
}Y=ba;
case"last":for(;
Y=Y.nextSibling;
){if(Y.nodeType===1){return false
}}return true;
case"nth":Z=ab[2];
var bb=ab[3];
if(Z===1&&bb===0){return true
}ab=ab[0];
var bl=ba.parentNode;
if(bl&&(bl.sizcache!==ab||!ba.nodeIndex)){var aa=0;
for(Y=bl.firstChild;
Y;
Y=Y.nextSibling){if(Y.nodeType===1){Y.nodeIndex=++aa
}}bl.sizcache=ab
}ba=ba.nodeIndex-bb;
return Z===0?ba===0:ba%Z===0&&ba/Z>=0
}},ID:function(Z,Y){return Z.nodeType===1&&Z.getAttribute("id")===Y
},TAG:function(Z,Y){return Y==="*"&&Z.nodeType===1||Z.nodeName.toLowerCase()===Y
},CLASS:function(Z,Y){return(" "+(Z.className||Z.getAttribute("class"))+" ").indexOf(Y)>-1
},ATTR:function(ab,aa){var Z=aa[1];
ab=L.attrHandle[Z]?L.attrHandle[Z](ab):ab[Z]!=null?ab[Z]:ab.getAttribute(Z);
Z=ab+"";
var Y=aa[2];
aa=aa[4];
return ab==null?Y==="!=":Y==="="?Z===aa:Y==="*="?Z.indexOf(aa)>=0:Y==="~="?(" "+Z+" ").indexOf(aa)>=0:!aa?Z&&ab!==false:Y==="!="?Z!==aa:Y==="^="?Z.indexOf(aa)===0:Y==="$="?Z.substr(Z.length-aa.length)===aa:Y==="|="?Z===aa||Z.substr(0,aa.length+1)===aa+"-":false
},POS:function(ab,aa,Z,Y){var ba=L.setFilters[aa[2]];
if(ba){return ba(ab,Z,aa,Y)
}}}},G=L.match.POS;
for(var w in L.match){L.match[w]=new RegExp(L.match[w].source+/(?![^\[]*\])(?![^\(]*\))/.source);
L.leftMatch[w]=new RegExp(/(^(?:.|\r|\n)*?)/.source+L.match[w].source.replace(/\\(\d+)/g,function(Z,Y){return"\\"+(Y-0+1)
}))
}var c=function(Z,Y){Z=Array.prototype.slice.call(Z,0);
if(Y){Y.push.apply(Y,Z);
return Y
}return Z
};
try{Array.prototype.slice.call(M.documentElement.childNodes,0)
}catch(F){c=function(ab,aa){aa=aa||[];
if(O.call(ab)==="[object Array]"){Array.prototype.push.apply(aa,ab)
}else{if(typeof ab.length==="number"){for(var Z=0,Y=ab.length;
Z<Y;
Z++){aa.push(ab[Z])
}}else{for(Z=0;
ab[Z];
Z++){aa.push(ab[Z])
}}}return aa
}
}var J;
if(M.documentElement.compareDocumentPosition){J=function(Z,Y){if(!Z.compareDocumentPosition||!Y.compareDocumentPosition){if(Z==Y){P=true
}return Z.compareDocumentPosition?-1:1
}Z=Z.compareDocumentPosition(Y)&4?-1:Z===Y?0:1;
if(Z===0){P=true
}return Z
}
}else{if("sourceIndex" in M.documentElement){J=function(Z,Y){if(!Z.sourceIndex||!Y.sourceIndex){if(Z==Y){P=true
}return Z.sourceIndex?-1:1
}Z=Z.sourceIndex-Y.sourceIndex;
if(Z===0){P=true
}return Z
}
}else{if(M.createRange){J=function(ab,aa){if(!ab.ownerDocument||!aa.ownerDocument){if(ab==aa){P=true
}return ab.ownerDocument?-1:1
}var Z=ab.ownerDocument.createRange(),Y=aa.ownerDocument.createRange();
Z.setStart(ab,0);
Z.setEnd(ab,0);
Y.setStart(aa,0);
Y.setEnd(aa,0);
ab=Z.compareBoundaryPoints(Range.START_TO_END,Y);
if(ab===0){P=true
}return ab
}
}}}(function(){var aa=M.createElement("div"),Z="script"+(new Date).getTime();
aa.innerHTML="<a name='"+Z+"'/>";
var Y=M.documentElement;
Y.insertBefore(aa,Y.firstChild);
if(M.getElementById(Z)){L.find.ID=function(ab,ba,bb){if(typeof ba.getElementById!=="undefined"&&!bb){return(ba=ba.getElementById(ab[1]))?ba.id===ab[1]||typeof ba.getAttributeNode!=="undefined"&&ba.getAttributeNode("id").nodeValue===ab[1]?[ba]:I:[]
}};
L.filter.ID=function(ab,ba){var bb=typeof ab.getAttributeNode!=="undefined"&&ab.getAttributeNode("id");
return ab.nodeType===1&&bb&&bb.nodeValue===ba
}
}Y.removeChild(aa);
Y=aa=null
})();
(function(){var Y=M.createElement("div");
Y.appendChild(M.createComment(""));
if(Y.getElementsByTagName("*").length>0){L.find.TAG=function(ab,aa){aa=aa.getElementsByTagName(ab[1]);
if(ab[1]==="*"){ab=[];
for(var Z=0;
aa[Z];
Z++){aa[Z].nodeType===1&&ab.push(aa[Z])
}aa=ab
}return aa
}
}Y.innerHTML="<a href='#'></a>";
if(Y.firstChild&&typeof Y.firstChild.getAttribute!=="undefined"&&Y.firstChild.getAttribute("href")!=="#"){L.attrHandle.href=function(Z){return Z.getAttribute("href",2)
}
}Y=null
})();
M.querySelectorAll&&function(){var aa=N,Z=M.createElement("div");
Z.innerHTML="<p class='TEST'></p>";
if(!(Z.querySelectorAll&&Z.querySelectorAll(".TEST").length===0)){N=function(ab,bl,bm,ba){bl=bl||M;
if(!ba&&bl.nodeType===9&&!s(bl)){try{return c(bl.querySelectorAll(ab),bm)
}catch(bb){}}return aa(ab,bl,bm,ba)
};
for(var Y in aa){N[Y]=aa[Y]
}Z=null
}}();
(function(){var Y=M.createElement("div");
Y.innerHTML="<div class='test e'></div><div class='test'></div>";
if(!(!Y.getElementsByClassName||Y.getElementsByClassName("e").length===0)){Y.lastChild.className="e";
if(Y.getElementsByClassName("e").length!==1){L.order.splice(1,0,"CLASS");
L.find.CLASS=function(ab,aa,Z){if(typeof aa.getElementsByClassName!=="undefined"&&!Z){return aa.getElementsByClassName(ab[1])
}};
Y=null
}}})();
var A=M.compareDocumentPosition?function(Z,Y){return !!(Z.compareDocumentPosition(Y)&16)
}:function(Z,Y){return Z!==Y&&(Z.contains?Z.contains(Y):true)
},s=function(Y){return(Y=(Y?Y.ownerDocument||Y:0).documentElement)?Y.nodeName!=="HTML":false
},X=function(ab,aa){var Z=[],Y="",ba;
for(aa=aa.nodeType?[aa]:aa;
ba=L.match.PSEUDO.exec(ab);
){Y+=ba[0];
ab=ab.replace(L.match.PSEUDO,"")
}ab=L.relative[ab]?ab+"*":ab;
ba=0;
for(var bb=aa.length;
ba<bb;
ba++){N(ab,aa[ba],Z)
}return N.filter(Y,Z)
};
ah.find=N;
ah.expr=N.selectors;
ah.expr[":"]=ah.expr.filters;
ah.unique=N.uniqueSort;
ah.text=W;
ah.isXMLDoc=s;
ah.contains=A
})();
var f=/Until$/,a9=/^(?:parents|prevUntil|prevAll)/,aW=/,/;
au=Array.prototype.slice;
var aL=function(s,c,A){if(ah.isFunction(c)){return ah.grep(s,function(G,F){return !!c.call(G,F,G)===A
})
}else{if(c.nodeType){return ah.grep(s,function(F){return F===c===A
})
}else{if(typeof c==="string"){var w=ah.grep(s,function(F){return F.nodeType===1
});
if(aT.test(c)){return ah.filter(c,w,!A)
}else{c=ah.filter(c,w)
}}}}return ah.grep(s,function(F){return ah.inArray(F,c)>=0===A
})
};
ah.fn.extend({find:function(s){for(var c=this.pushStack("","find",s),J=0,F=0,G=this.length;
F<G;
F++){J=c.length;
ah.find(s,this[F],c);
if(F>0){for(var w=J;
w<c.length;
w++){for(var A=0;
A<J;
A++){if(c[A]===c[w]){c.splice(w--,1);
break
}}}}}return c
},has:function(s){var c=ah(s);
return this.filter(function(){for(var A=0,w=c.length;
A<w;
A++){if(ah.contains(this,c[A])){return true
}}})
},not:function(c){return this.pushStack(aL(this,c,false),"not",c)
},filter:function(c){return this.pushStack(aL(this,c,true),"filter",c)
},is:function(c){return !!c&&ah.filter(c,this).length>0
},closest:function(L,K){if(ah.isArray(L)){var J=[],F=this[0],G,w={},A;
if(F&&L.length){G=0;
for(var c=L.length;
G<c;
G++){A=L[G];
w[A]||(w[A]=ah.expr.match.POS.test(A)?ah(A,K||this.context):A)
}for(;
F&&F.ownerDocument&&F!==K;
){for(A in w){G=w[A];
if(G.jquery?G.index(F)>-1:ah(F).is(G)){J.push({selector:A,elem:F});
delete w[A]
}}F=F.parentNode
}}return J
}var s=ah.expr.match.POS.test(L)?ah(L,K||this.context):null;
return this.map(function(O,N){for(;
N&&N.ownerDocument&&N!==K;
){if(s?s.index(N)>-1:ah(N).is(L)){return N
}N=N.parentNode
}return null
})
},index:function(c){if(!c||typeof c==="string"){return ah.inArray(this[0],c?ah(c):this.parent().children())
}return ah.inArray(c.jquery?c[0]:c,this)
},add:function(s,c){s=typeof s==="string"?ah(s,c||this.context):ah.makeArray(s);
c=ah.merge(this.get(),s);
return this.pushStack(l(s[0])||l(c[0])?c:ah.unique(c))
},andSelf:function(){return this.add(this.prevObject)
}});
ah.each({parent:function(c){return(c=c.parentNode)&&c.nodeType!==11?c:null
},parents:function(c){return ah.dir(c,"parentNode")
},parentsUntil:function(s,c,w){return ah.dir(s,"parentNode",w)
},next:function(c){return ah.nth(c,2,"nextSibling")
},prev:function(c){return ah.nth(c,2,"previousSibling")
},nextAll:function(c){return ah.dir(c,"nextSibling")
},prevAll:function(c){return ah.dir(c,"previousSibling")
},nextUntil:function(s,c,w){return ah.dir(s,"nextSibling",w)
},prevUntil:function(s,c,w){return ah.dir(s,"previousSibling",w)
},siblings:function(c){return ah.sibling(c.parentNode.firstChild,c)
},children:function(c){return ah.sibling(c.firstChild)
},contents:function(c){return ah.nodeName(c,"iframe")?c.contentDocument||c.contentWindow.document:ah.makeArray(c.childNodes)
}},function(s,c){ah.fn[s]=function(F,w){var A=ah.map(this,c,F);
f.test(s)||(w=F);
if(w&&typeof w==="string"){A=ah.filter(w,A)
}A=this.length>1?ah.unique(A):A;
if((this.length>1||aW.test(w))&&a9.test(s)){A=A.reverse()
}return this.pushStack(A,s,au.call(arguments).join(","))
}
});
ah.extend({filter:function(s,c,w){if(w){s=":not("+s+")"
}return ah.find.matches(s,c)
},dir:function(s,c,A){var w=[];
for(s=s[c];
s&&s.nodeType!==9&&(A===I||s.nodeType!==1||!ah(s).is(A));
){s.nodeType===1&&w.push(s);
s=s[c]
}return w
},nth:function(s,c,A){c=c||1;
for(var w=0;
s;
s=s[A]){if(s.nodeType===1&&++w===c){break
}}return s
},sibling:function(s,c){for(var w=[];
s;
s=s.nextSibling){s.nodeType===1&&s!==c&&w.push(s)
}return w
}});
var ai=/ jQuery\d+="(?:\d+|null)"/g,ar=/^\s+/,B=/(<([\w:]+)[^>]*?)\/>/g,aD=/^(?:area|br|col|embed|hr|img|input|link|meta|param)$/i,m=/<([\w:]+)/,ac=/<tbody/i,u=/<|&#?\w+;/,aP=/<script|<object|<embed|<option|<style/i,ak=/checked\s*(?:[^=]|=\s*.checked.)/i,bk=function(s,c,w){return aD.test(w)?s:c+"></"+w+">"
},aJ={option:[1,"<select multiple='multiple'>","</select>"],legend:[1,"<fieldset>","</fieldset>"],thead:[1,"<table>","</table>"],tr:[2,"<table><tbody>","</tbody></table>"],td:[3,"<table><tbody><tr>","</tr></tbody></table>"],col:[2,"<table><tbody></tbody><colgroup>","</colgroup></table>"],area:[1,"<map>","</map>"],_default:[0,"",""]};
aJ.optgroup=aJ.option;
aJ.tbody=aJ.tfoot=aJ.colgroup=aJ.caption=aJ.thead;
aJ.th=aJ.td;
if(!ah.support.htmlSerialize){aJ._default=[1,"div<div>","</div>"]
}ah.fn.extend({text:function(c){if(ah.isFunction(c)){return this.each(function(s){var w=ah(this);
w.text(c.call(this,s,w.text()))
})
}if(typeof c!=="object"&&c!==I){return this.empty().append((this[0]&&this[0].ownerDocument||M).createTextNode(c))
}return ah.text(this)
},wrapAll:function(s){if(ah.isFunction(s)){return this.each(function(w){ah(this).wrapAll(s.call(this,w))
})
}if(this[0]){var c=ah(s,this[0].ownerDocument).eq(0).clone(true);
this[0].parentNode&&c.insertBefore(this[0]);
c.map(function(){for(var w=this;
w.firstChild&&w.firstChild.nodeType===1;
){w=w.firstChild
}return w
}).append(this)
}return this
},wrapInner:function(c){if(ah.isFunction(c)){return this.each(function(s){ah(this).wrapInner(c.call(this,s))
})
}return this.each(function(){var s=ah(this),w=s.contents();
w.length?w.wrapAll(c):s.append(c)
})
},wrap:function(c){return this.each(function(){ah(this).wrapAll(c)
})
},unwrap:function(){return this.parent().each(function(){ah.nodeName(this,"body")||ah(this).replaceWith(this.childNodes)
}).end()
},append:function(){return this.domManip(arguments,true,function(c){this.nodeType===1&&this.appendChild(c)
})
},prepend:function(){return this.domManip(arguments,true,function(c){this.nodeType===1&&this.insertBefore(c,this.firstChild)
})
},before:function(){if(this[0]&&this[0].parentNode){return this.domManip(arguments,false,function(s){this.parentNode.insertBefore(s,this)
})
}else{if(arguments.length){var c=ah(arguments[0]);
c.push.apply(c,this.toArray());
return this.pushStack(c,"before",arguments)
}}},after:function(){if(this[0]&&this[0].parentNode){return this.domManip(arguments,false,function(s){this.parentNode.insertBefore(s,this.nextSibling)
})
}else{if(arguments.length){var c=this.pushStack(this,"after",arguments);
c.push.apply(c,ah(arguments[0]).toArray());
return c
}}},remove:function(s,c){for(var A=0,w;
(w=this[A])!=null;
A++){if(!s||ah.filter(s,[w]).length){if(!c&&w.nodeType===1){ah.cleanData(w.getElementsByTagName("*"));
ah.cleanData([w])
}w.parentNode&&w.parentNode.removeChild(w)
}}return this
},empty:function(){for(var s=0,c;
(c=this[s])!=null;
s++){for(c.nodeType===1&&ah.cleanData(c.getElementsByTagName("*"));
c.firstChild;
){c.removeChild(c.firstChild)
}}return this
},clone:function(s){var c=this.map(function(){if(!ah.support.noCloneEvent&&!ah.isXMLDoc(this)){var A=this.outerHTML,w=this.ownerDocument;
if(!A){A=w.createElement("div");
A.appendChild(this.cloneNode(true));
A=A.innerHTML
}return ah.clean([A.replace(ai,"").replace(/=([^="'>\s]+\/)>/g,'="$1">').replace(ar,"")],w)[0]
}else{return this.cloneNode(true)
}});
if(s===true){bj(this,c);
bj(this.find("*"),c.find("*"))
}return c
},html:function(s){if(s===I){return this[0]&&this[0].nodeType===1?this[0].innerHTML.replace(ai,""):null
}else{if(typeof s==="string"&&!aP.test(s)&&(ah.support.leadingWhitespace||!ar.test(s))&&!aJ[(m.exec(s)||["",""])[1].toLowerCase()]){s=s.replace(B,bk);
try{for(var c=0,A=this.length;
c<A;
c++){if(this[c].nodeType===1){ah.cleanData(this[c].getElementsByTagName("*"));
this[c].innerHTML=s
}}}catch(w){this.empty().append(s)
}}else{ah.isFunction(s)?this.each(function(J){var F=ah(this),G=F.html();
F.empty().append(function(){return s.call(this,J,G)
})
}):this.empty().append(s)
}}return this
},replaceWith:function(c){if(this[0]&&this[0].parentNode){if(ah.isFunction(c)){return this.each(function(s){var A=ah(this),w=A.html();
A.replaceWith(c.call(this,s,w))
})
}if(typeof c!=="string"){c=ah(c).detach()
}return this.each(function(){var s=this.nextSibling,w=this.parentNode;
ah(this).remove();
s?ah(s).before(c):ah(w).append(c)
})
}else{return this.pushStack(ah(ah.isFunction(c)?c():c),"replaceWith",c)
}},detach:function(c){return this.remove(c,true)
},domManip:function(O,N,L){function J(P){return ah.nodeName(P,"table")?P.getElementsByTagName("tbody")[0]||P.appendChild(P.ownerDocument.createElement("tbody")):P
}var K,F,G=O[0],s=[],A;
if(!ah.support.checkClone&&arguments.length===3&&typeof G==="string"&&ak.test(G)){return this.each(function(){ah(this).domManip(O,N,L,true)
})
}if(ah.isFunction(G)){return this.each(function(P){var Q=ah(this);
O[0]=G.call(this,P,N?Q.html():I);
Q.domManip(O,N,L)
})
}if(this[0]){K=G&&G.parentNode;
K=ah.support.parentNode&&K&&K.nodeType===11&&K.childNodes.length===this.length?{fragment:K}:a3(O,this,s);
A=K.fragment;
if(F=A.childNodes.length===1?(A=A.firstChild):A.firstChild){N=N&&ah.nodeName(F,"tr");
for(var w=0,c=this.length;
w<c;
w++){L.call(N?J(this[w],F):this[w],w>0||K.cacheable||this.length>1?A.cloneNode(true):A)
}}s.length&&ah.each(s,E)
}return this
}});
ah.fragments={};
ah.each({appendTo:"append",prependTo:"prepend",insertBefore:"before",insertAfter:"after",replaceAll:"replaceWith"},function(s,c){ah.fn[s]=function(J){var F=[];
J=ah(J);
var G=this.length===1&&this[0].parentNode;
if(G&&G.nodeType===11&&G.childNodes.length===1&&J.length===1){J[c](this[0]);
return this
}else{G=0;
for(var w=J.length;
G<w;
G++){var A=(G>0?this.clone(true):this).get();
ah.fn[c].apply(ah(J[G]),A);
F=F.concat(A)
}return this.pushStack(F,s,J.selector)
}}
});
ah.extend({clean:function(O,N,L,J){N=N||M;
if(typeof N.createElement==="undefined"){N=N.ownerDocument||N[0]&&N[0].ownerDocument||M
}for(var K=[],F=0,G;
(G=O[F])!=null;
F++){if(typeof G==="number"){G+=""
}if(G){if(typeof G==="string"&&!u.test(G)){G=N.createTextNode(G)
}else{if(typeof G==="string"){G=G.replace(B,bk);
var s=(m.exec(G)||["",""])[1].toLowerCase(),A=aJ[s]||aJ._default,w=A[0],c=N.createElement("div");
for(c.innerHTML=A[1]+G+A[2];
w--;
){c=c.lastChild
}if(!ah.support.tbody){w=ac.test(G);
s=s==="table"&&!w?c.firstChild&&c.firstChild.childNodes:A[1]==="<table>"&&!w?c.childNodes:[];
for(A=s.length-1;
A>=0;
--A){ah.nodeName(s[A],"tbody")&&!s[A].childNodes.length&&s[A].parentNode.removeChild(s[A])
}}!ah.support.leadingWhitespace&&ar.test(G)&&c.insertBefore(N.createTextNode(ar.exec(G)[0]),c.firstChild);
G=c.childNodes
}}if(G.nodeType){K.push(G)
}else{K=ah.merge(K,G)
}}}if(L){for(F=0;
K[F];
F++){if(J&&ah.nodeName(K[F],"script")&&(!K[F].type||K[F].type.toLowerCase()==="text/javascript")){J.push(K[F].parentNode?K[F].parentNode.removeChild(K[F]):K[F])
}else{K[F].nodeType===1&&K.splice.apply(K,[F+1,0].concat(ah.makeArray(K[F].getElementsByTagName("script"))));
L.appendChild(K[F])
}}}return K
},cleanData:function(L){for(var K,J,F=ah.cache,G=ah.event.special,w=ah.support.deleteExpando,A=0,c;
(c=L[A])!=null;
A++){if(J=c[ah.expando]){K=F[J];
if(K.events){for(var s in K.events){G[s]?ah.event.remove(c,s):aG(c,s,K.handle)
}}if(w){delete c[ah.expando]
}else{c.removeAttribute&&c.removeAttribute(ah.expando)
}delete F[J]
}}}});
var h=/z-?index|font-?weight|opacity|zoom|line-?height/i,a4=/alpha\([^)]*\)/,aQ=/opacity=([^)]*)/,aE=/float/i,ad=/-([a-z])/ig,bf=/([A-Z])/g,aZ=/^-?\d+(?:px)?$/i,aI=/^-?\d/,af={position:"absolute",visibility:"hidden",display:"block"},y=["Left","Right"],k=["Top","Bottom"],bi=M.defaultView&&M.defaultView.getComputedStyle,al=ah.support.cssFloat?"cssFloat":"styleFloat",v=function(s,c){return c.toUpperCase()
};
ah.fn.css=function(s,c){return ap(this,s,c,true,function(F,w,A){if(A===I){return ah.curCSS(F,w)
}if(typeof A==="number"&&!h.test(w)){A+="px"
}ah.style(F,w,A)
})
};
ah.extend({style:function(s,c,F){if(!s||s.nodeType===3||s.nodeType===8){return I
}if((c==="width"||c==="height")&&parseFloat(F)<0){F=I
}var w=s.style||s,A=F!==I;
if(!ah.support.opacity&&c==="opacity"){if(A){w.zoom=1;
c=parseInt(F,10)+""==="NaN"?"":"alpha(opacity="+F*100+")";
s=w.filter||ah.curCSS(s,"filter")||"";
w.filter=a4.test(s)?s.replace(a4,c):c
}return w.filter&&w.filter.indexOf("opacity=")>=0?parseFloat(aQ.exec(w.filter)[1])/100+"":""
}if(aE.test(c)){c=al
}c=c.replace(ad,v);
if(A){w[c]=F
}return w[c]
},css:function(s,c,J,F){if(c==="width"||c==="height"){var G,w=c==="width"?y:k;
function A(){G=c==="width"?s.offsetWidth:s.offsetHeight;
F!=="border"&&ah.each(w,function(){F||(G-=parseFloat(ah.curCSS(s,"padding"+this,true))||0);
if(F==="margin"){G+=parseFloat(ah.curCSS(s,"margin"+this,true))||0
}else{G-=parseFloat(ah.curCSS(s,"border"+this+"Width",true))||0
}})
}s.offsetWidth!==0?A():ah.swap(s,af,A);
return Math.max(0,Math.round(G))
}return ah.curCSS(s,c,J)
},curCSS:function(s,c,G){var A,F=s.style;
if(!ah.support.opacity&&c==="opacity"&&s.currentStyle){A=aQ.test(s.currentStyle.filter||"")?parseFloat(RegExp.$1)/100+"":"";
return A===""?"1":A
}if(aE.test(c)){c=al
}if(!G&&F&&F[c]){A=F[c]
}else{if(bi){if(aE.test(c)){c="float"
}c=c.replace(bf,"-$1").toLowerCase();
F=s.ownerDocument.defaultView;
if(!F){return null
}if(s=F.getComputedStyle(s,null)){A=s.getPropertyValue(c)
}if(c==="opacity"&&A===""){A="1"
}}else{if(s.currentStyle){G=c.replace(ad,v);
A=s.currentStyle[c]||s.currentStyle[G];
if(!aZ.test(A)&&aI.test(A)){c=F.left;
var w=s.runtimeStyle.left;
s.runtimeStyle.left=s.currentStyle.left;
F.left=G==="fontSize"?"1em":A||0;
A=F.pixelLeft+"px";
F.left=c;
s.runtimeStyle.left=w
}}}}return A
},swap:function(s,c,F){var w={};
for(var A in c){w[A]=s.style[A];
s.style[A]=c[A]
}F.call(s);
for(A in c){s.style[A]=w[A]
}}});
if(ah.expr&&ah.expr.filters){ah.expr.filters.hidden=function(s){var c=s.offsetWidth,A=s.offsetHeight,w=s.nodeName.toLowerCase()==="tr";
return c===0&&A===0&&!w?true:c>0&&A>0&&!w?false:ah.curCSS(s,"display")==="none"
};
ah.expr.filters.visible=function(c){return !ah.expr.filters.hidden(c)
}
}var a2=aF(),aN=/<script(.|\s)*?\/script>/gi,aj=/select|textarea/i,C=/color|date|datetime|email|hidden|month|number|password|range|search|tel|text|time|url|week/i,aA=/=\?(&|$)/,i=/\?/,n=/(\?|&)_=.*?(&|$)/,a=/^(\w+:)?\/\/([^\/?#]+)/,a5=/%20/g,aR=ah.fn.load;
ah.fn.extend({load:function(s,c,G){if(typeof s!=="string"){return aR.call(this,s)
}else{if(!this.length){return this
}}var A=s.indexOf(" ");
if(A>=0){var F=s.slice(A,s.length);
s=s.slice(0,A)
}A="GET";
if(c){if(ah.isFunction(c)){G=c;
c=null
}else{if(typeof c==="object"){c=ah.param(c,ah.ajaxSettings.traditional);
A="POST"
}}}var w=this;
ah.ajax({url:s,type:A,dataType:"html",data:c,complete:function(J,K){if(K==="success"||K==="notmodified"){w.html(F?ah("<div />").append(J.responseText.replace(aN,"")).find(F):J.responseText)
}G&&w.each(G,[J.responseText,K,J])
}});
return this
},serialize:function(){return ah.param(this.serializeArray())
},serializeArray:function(){return this.map(function(){return this.elements?ah.makeArray(this.elements):this
}).filter(function(){return this.name&&!this.disabled&&(this.checked||aj.test(this.nodeName)||C.test(this.type))
}).map(function(s,c){s=ah(this).val();
return s==null?null:ah.isArray(s)?ah.map(s,function(w){return{name:c.name,value:w}
}):{name:c.name,value:s}
}).get()
}});
ah.each("ajaxStart ajaxStop ajaxComplete ajaxError ajaxSuccess ajaxSend".split(" "),function(s,c){ah.fn[c]=function(w){return this.bind(c,w)
}
});
ah.extend({get:function(s,c,A,w){if(ah.isFunction(c)){w=w||A;
A=c;
c=null
}return ah.ajax({type:"GET",url:s,data:c,success:A,dataType:w})
},getScript:function(s,c){return ah.get(s,null,c,"script")
},getJSON:function(s,c,w){return ah.get(s,c,w,"json")
},post:function(s,c,A,w){if(ah.isFunction(c)){w=w||A;
A=c;
c={}
}return ah.ajax({type:"POST",url:s,data:c,success:A,dataType:w})
},ajaxSetup:function(c){ah.extend(ah.ajaxSettings,c)
},ajaxSettings:{url:location.href,global:true,type:"GET",contentType:"application/x-www-form-urlencoded",processData:true,async:true,xhr:aO.XMLHttpRequest&&(aO.location.protocol!=="file:"||!aO.ActiveXObject)?function(){return new aO.XMLHttpRequest
}:function(){try{return new aO.ActiveXObject("Microsoft.XMLHTTP")
}catch(c){}},accepts:{xml:"application/xml, text/xml",html:"text/html",script:"text/javascript, application/javascript",json:"application/json, text/javascript",text:"text/plain",_default:"*/*"}},lastModified:{},etag:{},ajax:function(aa){function Z(){X.success&&X.success.call(P,K,R,s);
X.global&&W("ajaxSuccess",[s,X])
}function Y(){X.complete&&X.complete.call(P,s,R);
X.global&&W("ajaxComplete",[s,X]);
X.global&&!--ah.active&&ah.event.trigger("ajaxStop")
}function W(ba,bb){(X.context?ah(X.context):ah.event).trigger(ba,bb)
}var X=ah.extend(true,{},ah.ajaxSettings,aa),Q,R,K,P=aa&&aa.context||X,L=X.type.toUpperCase();
if(X.data&&X.processData&&typeof X.data!=="string"){X.data=ah.param(X.data,X.traditional)
}if(X.dataType==="jsonp"){if(L==="GET"){aA.test(X.url)||(X.url+=(i.test(X.url)?"&":"?")+(X.jsonp||"callback")+"=?")
}else{if(!X.data||!aA.test(X.data)){X.data=(X.data?X.data+"&":"")+(X.jsonp||"callback")+"=?"
}}X.dataType="json"
}if(X.dataType==="json"&&(X.data&&aA.test(X.data)||aA.test(X.url))){Q=X.jsonpCallback||"jsonp"+a2++;
if(X.data){X.data=(X.data+"").replace(aA,"="+Q+"$1")
}X.url=X.url.replace(aA,"="+Q+"$1");
X.dataType="script";
aO[Q]=aO[Q]||function(ba){K=ba;
Z();
Y();
aO[Q]=I;
try{delete aO[Q]
}catch(bb){}c&&c.removeChild(F)
}
}if(X.dataType==="script"&&X.cache===null){X.cache=false
}if(X.cache===false&&L==="GET"){var G=aF(),w=X.url.replace(n,"$1_="+G+"$2");
X.url=w+(w===X.url?(i.test(X.url)?"&":"?")+"_="+G:"")
}if(X.data&&L==="GET"){X.url+=(i.test(X.url)?"&":"?")+X.data
}X.global&&!ah.active++&&ah.event.trigger("ajaxStart");
G=(G=a.exec(X.url))&&(G[1]&&G[1]!==location.protocol||G[2]!==location.host);
if(X.dataType==="script"&&L==="GET"&&G){var c=M.getElementsByTagName("head")[0]||M.documentElement,F=M.createElement("script");
F.src=X.url;
if(X.scriptCharset){F.charset=X.scriptCharset
}if(!Q){var J=false;
F.onload=F.onreadystatechange=function(){if(!J&&(!this.readyState||this.readyState==="loaded"||this.readyState==="complete")){J=true;
Z();
Y();
F.onload=F.onreadystatechange=null;
c&&F.parentNode&&c.removeChild(F)
}}
}c.insertBefore(F,c.firstChild);
return I
}var A=false,s=X.xhr();
if(s){X.username?s.open(L,X.url,X.async,X.username,X.password):s.open(L,X.url,X.async);
try{if(X.data||aa&&aa.contentType){s.setRequestHeader("Content-Type",X.contentType)
}if(X.ifModified){ah.lastModified[X.url]&&s.setRequestHeader("If-Modified-Since",ah.lastModified[X.url]);
ah.etag[X.url]&&s.setRequestHeader("If-None-Match",ah.etag[X.url])
}G||s.setRequestHeader("X-Requested-With","XMLHttpRequest");
s.setRequestHeader("Accept",X.dataType&&X.accepts[X.dataType]?X.accepts[X.dataType]+", */*":X.accepts._default)
}catch(ab){}if(X.beforeSend&&X.beforeSend.call(P,s,X)===false){X.global&&!--ah.active&&ah.event.trigger("ajaxStop");
s.abort();
return false
}X.global&&W("ajaxSend",[s,X]);
var V=s.onreadystatechange=function(bb){if(!s||s.readyState===0||bb==="abort"){A||Y();
A=true;
if(s){s.onreadystatechange=ah.noop
}}else{if(!A&&s&&(s.readyState===4||bb==="timeout")){A=true;
s.onreadystatechange=ah.noop;
R=bb==="timeout"?"timeout":!ah.httpSuccess(s)?"error":X.ifModified&&ah.httpNotModified(s,X.url)?"notmodified":"success";
var bl;
if(R==="success"){try{K=ah.httpData(s,X.dataType,X)
}catch(ba){R="parsererror";
bl=ba
}}if(R==="success"||R==="notmodified"){Q||Z()
}else{ah.handleError(X,s,R,bl)
}Y();
bb==="timeout"&&s.abort();
if(X.async){s=null
}}}};
try{var T=s.abort;
s.abort=function(){s&&T.call(s);
V("abort")
}
}catch(O){}X.async&&X.timeout>0&&setTimeout(function(){s&&!A&&V("timeout")
},X.timeout);
try{s.send(L==="POST"||L==="PUT"||L==="DELETE"?X.data:null)
}catch(N){ah.handleError(X,s,null,N);
Y()
}X.async||V();
return s
}},handleError:function(s,c,A,w){if(s.error){s.error.call(s.context||s,c,A,w)
}if(s.global){(s.context?ah(s.context):ah.event).trigger("ajaxError",[c,s,w])
}},active:0,httpSuccess:function(s){try{return !s.status&&location.protocol==="file:"||s.status>=200&&s.status<300||s.status===304||s.status===1223||s.status===0
}catch(c){}return false
},httpNotModified:function(s,c){var A=s.getResponseHeader("Last-Modified"),w=s.getResponseHeader("Etag");
if(A){ah.lastModified[c]=A
}if(w){ah.etag[c]=w
}return s.status===304||s.status===0
},httpData:function(s,c,F){var w=s.getResponseHeader("content-type")||"",A=c==="xml"||!c&&w.indexOf("xml")>=0;
s=A?s.responseXML:s.responseText;
A&&s.documentElement.nodeName==="parsererror"&&ah.error("parsererror");
if(F&&F.dataFilter){s=F.dataFilter(s,c)
}if(typeof s==="string"){if(c==="json"||!c&&w.indexOf("json")>=0){s=ah.parseJSON(s)
}else{if(c==="script"||!c&&w.indexOf("javascript")>=0){ah.globalEval(s)
}}}return s
},param:function(s,c){function G(J,K){if(ah.isArray(K)){ah.each(K,function(L,N){c||/\[\]$/.test(J)?A(J,N):G(J+"["+(typeof N==="object"||ah.isArray(N)?L:"")+"]",N)
})
}else{!c&&K!=null&&typeof K==="object"?ah.each(K,function(L,N){G(J+"["+L+"]",N)
}):A(J,K)
}}function A(J,K){K=ah.isFunction(K)?K():K;
F[F.length]=encodeURIComponent(J)+"="+encodeURIComponent(K)
}var F=[];
if(c===I){c=ah.ajaxSettings.traditional
}if(ah.isArray(s)||s.jquery){ah.each(s,function(){A(this.name,this.value)
})
}else{for(var w in s){G(w,s[w])
}}return F.join("&").replace(a5,"+")
}});
var bg={},bc=/toggle|show|hide/,aX=/^([+-]=)?([\d+-.]+)(.*)$/,aq,D=[["height","marginTop","marginBottom","paddingTop","paddingBottom"],["width","marginLeft","marginRight","paddingLeft","paddingRight"],["opacity"]];
ah.fn.extend({show:function(s,c){if(s||s===0){return this.animate(aC("show",3),s,c)
}else{s=0;
for(c=this.length;
s<c;
s++){var F=ah.data(this[s],"olddisplay");
this[s].style.display=F||"";
if(ah.css(this[s],"display")==="none"){F=this[s].nodeName;
var w;
if(bg[F]){w=bg[F]
}else{var A=ah("<"+F+" />").appendTo("body");
w=A.css("display");
if(w==="none"){w="block"
}A.remove();
bg[F]=w
}ah.data(this[s],"olddisplay",w)
}}s=0;
for(c=this.length;
s<c;
s++){this[s].style.display=ah.data(this[s],"olddisplay")||""
}return this
}},hide:function(s,c){if(s||s===0){return this.animate(aC("hide",3),s,c)
}else{s=0;
for(c=this.length;
s<c;
s++){var w=ah.data(this[s],"olddisplay");
!w&&w!=="none"&&ah.data(this[s],"olddisplay",ah.css(this[s],"display"))
}s=0;
for(c=this.length;
s<c;
s++){this[s].style.display="none"
}return this
}},_toggle:ah.fn.toggle,toggle:function(s,c){var w=typeof s==="boolean";
if(ah.isFunction(s)&&ah.isFunction(c)){this._toggle.apply(this,arguments)
}else{s==null||w?this.each(function(){var A=w?s:ah(this).is(":hidden");
ah(this)[A?"show":"hide"]()
}):this.animate(aC("toggle",3),s,c)
}return this
},fadeTo:function(s,c,w){return this.filter(":hidden").css("opacity",0).show().end().animate({opacity:c},s,w)
},animate:function(s,c,F,w){var A=ah.speed(c,F,w);
if(ah.isEmptyObject(s)){return this.each(A.complete)
}return this[A.queue===false?"each":"queue"](function(){var J=ah.extend({},A),K,L=this.nodeType===1&&ah(this).is(":hidden"),G=this;
for(K in s){var N=K.replace(ad,v);
if(K!==N){s[N]=s[K];
delete s[K];
K=N
}if(s[K]==="hide"&&L||s[K]==="show"&&!L){return J.complete.call(this)
}if((K==="height"||K==="width")&&this.style){J.display=ah.css(this,"display");
J.overflow=this.style.overflow
}if(ah.isArray(s[K])){(J.specialEasing=J.specialEasing||{})[K]=s[K][1];
s[K]=s[K][0]
}}if(J.overflow!=null){this.style.overflow="hidden"
}J.curAnim=ah.extend({},s);
ah.each(s,function(P,O){var T=new ah.fx(G,J,P);
if(bc.test(O)){T[O==="toggle"?L?"show":"hide":O](s)
}else{var R=aX.exec(O),V=T.cur(true)||0;
if(R){O=parseFloat(R[2]);
var Q=R[3]||"px";
if(Q!=="px"){G.style[P]=(O||1)+Q;
V=(O||1)/T.cur(true)*V;
G.style[P]=V+Q
}if(R[1]){O=(R[1]==="-="?-1:1)*O+V
}T.custom(V,O,Q)
}else{T.custom(V,O,"")
}}});
return true
})
},stop:function(s,c){var w=ah.timers;
s&&this.queue([]);
this.each(function(){for(var A=w.length-1;
A>=0;
A--){if(w[A].elem===this){c&&w[A](true);
w.splice(A,1)
}}});
c||this.dequeue();
return this
}});
ah.each({slideDown:aC("show",1),slideUp:aC("hide",1),slideToggle:aC("toggle",1),fadeIn:{opacity:"show"},fadeOut:{opacity:"hide"}},function(s,c){ah.fn[s]=function(A,w){return this.animate(c,A,w)
}
});
ah.extend({speed:function(s,c,A){var w=s&&typeof s==="object"?s:{complete:A||!A&&c||ah.isFunction(s)&&s,duration:s,easing:A&&c||c&&!ah.isFunction(c)&&c};
w.duration=ah.fx.off?0:typeof w.duration==="number"?w.duration:ah.fx.speeds[w.duration]||ah.fx.speeds._default;
w.old=w.complete;
w.complete=function(){w.queue!==false&&ah(this).dequeue();
ah.isFunction(w.old)&&w.old.call(this)
};
return w
},easing:{linear:function(s,c,A,w){return A+w*s
},swing:function(s,c,A,w){return(-Math.cos(s*Math.PI)/2+0.5)*w+A
}},timers:[],fx:function(s,c,w){this.options=c;
this.elem=s;
this.prop=w;
if(!c.orig){c.orig={}
}}});
ah.fx.prototype={update:function(){this.options.step&&this.options.step.call(this.elem,this.now,this);
(ah.fx.step[this.prop]||ah.fx.step._default)(this);
if((this.prop==="height"||this.prop==="width")&&this.elem.style){this.elem.style.display="block"
}},cur:function(c){if(this.elem[this.prop]!=null&&(!this.elem.style||this.elem.style[this.prop]==null)){return this.elem[this.prop]
}return(c=parseFloat(ah.css(this.elem,this.prop,c)))&&c>-10000?c:parseFloat(ah.curCSS(this.elem,this.prop))||0
},custom:function(s,c,F){function w(G){return A.step(G)
}this.startTime=aF();
this.start=s;
this.end=c;
this.unit=F||this.unit||"px";
this.now=this.start;
this.pos=this.state=0;
var A=this;
w.elem=this.elem;
if(w()&&ah.timers.push(w)&&!aq){aq=setInterval(ah.fx.tick,13)
}},show:function(){this.options.orig[this.prop]=ah.style(this.elem,this.prop);
this.options.show=true;
this.custom(this.prop==="width"||this.prop==="height"?1:0,this.cur());
ah(this.elem).show()
},hide:function(){this.options.orig[this.prop]=ah.style(this.elem,this.prop);
this.options.hide=true;
this.custom(this.cur(),0)
},step:function(s){var c=aF(),F=true;
if(s||c>=this.options.duration+this.startTime){this.now=this.end;
this.pos=this.state=1;
this.update();
this.options.curAnim[this.prop]=true;
for(var w in this.options.curAnim){if(this.options.curAnim[w]!==true){F=false
}}if(F){if(this.options.display!=null){this.elem.style.overflow=this.options.overflow;
s=ah.data(this.elem,"olddisplay");
this.elem.style.display=s?s:this.options.display;
if(ah.css(this.elem,"display")==="none"){this.elem.style.display="block"
}}this.options.hide&&ah(this.elem).hide();
if(this.options.hide||this.options.show){for(var A in this.options.curAnim){ah.style(this.elem,A,this.options.orig[A])
}}this.options.complete.call(this.elem)
}return false
}else{A=c-this.startTime;
this.state=A/this.options.duration;
s=this.options.easing||(ah.easing.swing?"swing":"linear");
this.pos=ah.easing[this.options.specialEasing&&this.options.specialEasing[this.prop]||s](this.state,A,0,1,this.options.duration);
this.now=this.start+(this.end-this.start)*this.pos;
this.update()
}return true
}};
ah.extend(ah.fx,{tick:function(){for(var s=ah.timers,c=0;
c<s.length;
c++){s[c]()||s.splice(c--,1)
}s.length||ah.fx.stop()
},stop:function(){clearInterval(aq);
aq=null
},speeds:{slow:600,fast:200,_default:400},step:{opacity:function(c){ah.style(c.elem,"opacity",c.now)
},_default:function(c){if(c.elem.style&&c.elem.style[c.prop]!=null){c.elem.style[c.prop]=(c.prop==="width"||c.prop==="height"?Math.max(0,c.now):c.now)+c.unit
}else{c.elem[c.prop]=c.now
}}}});
if(ah.expr&&ah.expr.filters){ah.expr.filters.animated=function(c){return ah.grep(ah.timers,function(s){return c===s.elem
}).length
}
}ah.fn.offset="getBoundingClientRect" in M.documentElement?function(s){var c=this[0];
if(s){return this.each(function(F){ah.offset.setOffset(this,s,F)
})
}if(!c||!c.ownerDocument){return null
}if(c===c.ownerDocument.body){return ah.offset.bodyOffset(c)
}var A=c.getBoundingClientRect(),w=c.ownerDocument;
c=w.body;
w=w.documentElement;
return{top:A.top+(self.pageYOffset||ah.support.boxModel&&w.scrollTop||c.scrollTop)-(w.clientTop||c.clientTop||0),left:A.left+(self.pageXOffset||ah.support.boxModel&&w.scrollLeft||c.scrollLeft)-(w.clientLeft||c.clientLeft||0)}
}:function(N){var L=this[0];
if(N){return this.each(function(O){ah.offset.setOffset(this,N,O)
})
}if(!L||!L.ownerDocument){return null
}if(L===L.ownerDocument.body){return ah.offset.bodyOffset(L)
}ah.offset.initialize();
var K=L.offsetParent,G=L,J=L.ownerDocument,A,F=J.documentElement,c=J.body;
G=(J=J.defaultView)?J.getComputedStyle(L,null):L.currentStyle;
for(var w=L.offsetTop,s=L.offsetLeft;
(L=L.parentNode)&&L!==c&&L!==F;
){if(ah.offset.supportsFixedPosition&&G.position==="fixed"){break
}A=J?J.getComputedStyle(L,null):L.currentStyle;
w-=L.scrollTop;
s-=L.scrollLeft;
if(L===K){w+=L.offsetTop;
s+=L.offsetLeft;
if(ah.offset.doesNotAddBorder&&!(ah.offset.doesAddBorderForTableAndCells&&/^t(able|d|h)$/i.test(L.nodeName))){w+=parseFloat(A.borderTopWidth)||0;
s+=parseFloat(A.borderLeftWidth)||0
}G=K;
K=L.offsetParent
}if(ah.offset.subtractsBorderForOverflowNotVisible&&A.overflow!=="visible"){w+=parseFloat(A.borderTopWidth)||0;
s+=parseFloat(A.borderLeftWidth)||0
}G=A
}if(G.position==="relative"||G.position==="static"){w+=c.offsetTop;
s+=c.offsetLeft
}if(ah.offset.supportsFixedPosition&&G.position==="fixed"){w+=Math.max(F.scrollTop,c.scrollTop);
s+=Math.max(F.scrollLeft,c.scrollLeft)
}return{top:w,left:s}
};
ah.offset={initialize:function(){var s=M.body,c=M.createElement("div"),G,A,F,w=parseFloat(ah.curCSS(s,"marginTop",true))||0;
ah.extend(c.style,{position:"absolute",top:0,left:0,margin:0,border:0,width:"1px",height:"1px",visibility:"hidden"});
c.innerHTML="<div style='position:absolute;top:0;left:0;margin:0;border:5px solid #000;padding:0;width:1px;height:1px;'><div></div></div><table style='position:absolute;top:0;left:0;margin:0;border:5px solid #000;padding:0;width:1px;height:1px;' cellpadding='0' cellspacing='0'><tr><td></td></tr></table>";
s.insertBefore(c,s.firstChild);
G=c.firstChild;
A=G.firstChild;
F=G.nextSibling.firstChild.firstChild;
this.doesNotAddBorder=A.offsetTop!==5;
this.doesAddBorderForTableAndCells=F.offsetTop===5;
A.style.position="fixed";
A.style.top="20px";
this.supportsFixedPosition=A.offsetTop===20||A.offsetTop===15;
A.style.position=A.style.top="";
G.style.overflow="hidden";
G.style.position="relative";
this.subtractsBorderForOverflowNotVisible=A.offsetTop===-5;
this.doesNotIncludeMarginInBodyOffset=s.offsetTop!==w;
s.removeChild(c);
ah.offset.initialize=ah.noop
},bodyOffset:function(s){var c=s.offsetTop,w=s.offsetLeft;
ah.offset.initialize();
if(ah.offset.doesNotIncludeMarginInBodyOffset){c+=parseFloat(ah.curCSS(s,"marginTop",true))||0;
w+=parseFloat(ah.curCSS(s,"marginLeft",true))||0
}return{top:c,left:w}
},setOffset:function(s,c,J){if(/static/.test(ah.curCSS(s,"position"))){s.style.position="relative"
}var F=ah(s),G=F.offset(),w=parseInt(ah.curCSS(s,"top",true),10)||0,A=parseInt(ah.curCSS(s,"left",true),10)||0;
if(ah.isFunction(c)){c=c.call(s,J,G)
}J={top:c.top-G.top+w,left:c.left-G.left+A};
"using" in c?c.using.call(s,J):F.css(J)
}};
ah.fn.extend({position:function(){if(!this[0]){return null
}var s=this[0],c=this.offsetParent(),A=this.offset(),w=/^body|html$/i.test(c[0].nodeName)?{top:0,left:0}:c.offset();
A.top-=parseFloat(ah.curCSS(s,"marginTop",true))||0;
A.left-=parseFloat(ah.curCSS(s,"marginLeft",true))||0;
w.top+=parseFloat(ah.curCSS(c[0],"borderTopWidth",true))||0;
w.left+=parseFloat(ah.curCSS(c[0],"borderLeftWidth",true))||0;
return{top:A.top-w.top,left:A.left-w.left}
},offsetParent:function(){return this.map(function(){for(var c=this.offsetParent||M.body;
c&&!/^body|html$/i.test(c.nodeName)&&ah.css(c,"position")==="static";
){c=c.offsetParent
}return c
})
}});
ah.each(["Left","Top"],function(s,c){var w="scroll"+c;
ah.fn[w]=function(F){var G=this[0],A;
if(!G){return null
}if(F!==I){return this.each(function(){if(A=o(this)){A.scrollTo(!s?F:ah(A).scrollLeft(),s?F:ah(A).scrollTop())
}else{this[w]=F
}})
}else{return(A=o(G))?"pageXOffset" in A?A[s?"pageYOffset":"pageXOffset"]:ah.support.boxModel&&A.document.documentElement[w]||A.document.body[w]:G[w]
}}
});
ah.each(["Height","Width"],function(s,c){var w=c.toLowerCase();
ah.fn["inner"+c]=function(){return this[0]?ah.css(this[0],w,false,"padding"):null
};
ah.fn["outer"+c]=function(A){return this[0]?ah.css(this[0],w,false,A?"margin":"border"):null
};
ah.fn[w]=function(A){var F=this[0];
if(!F){return A==null?null:this
}if(ah.isFunction(A)){return this.each(function(G){var J=ah(this);
J[w](A.call(this,G,J[w]()))
})
}return"scrollTo" in F&&F.document?F.document.compatMode==="CSS1Compat"&&F.document.documentElement["client"+c]||F.document.body["client"+c]:F.nodeType===9?Math.max(F.documentElement["client"+c],F.body["scroll"+c],F.documentElement["scroll"+c],F.body["offset"+c],F.documentElement["offset"+c]):A===I?ah.css(F,w):this.css(w,typeof A==="string"?A:A+"px")
}
});
aO.jQuery=aO.$=ah
})(window);Encoder={EncodeType:"entity",isEmpty:function(a){if(a){return((a===null)||a.length==0||/^\s+$/.test(a))
}else{return true
}},HTML2Numerical:function(c){var b=new Array("&nbsp;","&iexcl;","&cent;","&pound;","&curren;","&yen;","&brvbar;","&sect;","&uml;","&copy;","&ordf;","&laquo;","&not;","&shy;","&reg;","&macr;","&deg;","&plusmn;","&sup2;","&sup3;","&acute;","&micro;","&para;","&middot;","&cedil;","&sup1;","&ordm;","&raquo;","&frac14;","&frac12;","&frac34;","&iquest;","&agrave;","&aacute;","&acirc;","&atilde;","&Auml;","&aring;","&aelig;","&ccedil;","&egrave;","&eacute;","&ecirc;","&euml;","&igrave;","&iacute;","&icirc;","&iuml;","&eth;","&ntilde;","&ograve;","&oacute;","&ocirc;","&otilde;","&Ouml;","&times;","&oslash;","&ugrave;","&uacute;","&ucirc;","&Uuml;","&yacute;","&thorn;","&szlig;","&agrave;","&aacute;","&acirc;","&atilde;","&auml;","&aring;","&aelig;","&ccedil;","&egrave;","&eacute;","&ecirc;","&euml;","&igrave;","&iacute;","&icirc;","&iuml;","&eth;","&ntilde;","&ograve;","&oacute;","&ocirc;","&otilde;","&ouml;","&divide;","&Oslash;","&ugrave;","&uacute;","&ucirc;","&uuml;","&yacute;","&thorn;","&yuml;","&quot;","&amp;","&lt;","&gt;","&oelig;","&oelig;","&scaron;","&scaron;","&yuml;","&circ;","&tilde;","&ensp;","&emsp;","&thinsp;","&zwnj;","&zwj;","&lrm;","&rlm;","&ndash;","&mdash;","&lsquo;","&rsquo;","&sbquo;","&ldquo;","&rdquo;","&bdquo;","&dagger;","&dagger;","&permil;","&lsaquo;","&rsaquo;","&euro;","&fnof;","&alpha;","&beta;","&gamma;","&delta;","&epsilon;","&zeta;","&eta;","&theta;","&iota;","&kappa;","&lambda;","&mu;","&nu;","&xi;","&omicron;","&pi;","&rho;","&sigma;","&tau;","&upsilon;","&phi;","&chi;","&psi;","&omega;","&alpha;","&beta;","&gamma;","&delta;","&epsilon;","&zeta;","&eta;","&theta;","&iota;","&kappa;","&lambda;","&mu;","&nu;","&xi;","&omicron;","&pi;","&rho;","&sigmaf;","&sigma;","&tau;","&upsilon;","&phi;","&chi;","&psi;","&omega;","&thetasym;","&upsih;","&piv;","&bull;","&hellip;","&prime;","&prime;","&oline;","&frasl;","&weierp;","&image;","&real;","&trade;","&alefsym;","&larr;","&uarr;","&rarr;","&darr;","&harr;","&crarr;","&larr;","&uarr;","&rarr;","&darr;","&harr;","&forall;","&part;","&exist;","&empty;","&nabla;","&isin;","&notin;","&ni;","&prod;","&sum;","&minus;","&lowast;","&radic;","&prop;","&infin;","&ang;","&and;","&or;","&cap;","&cup;","&int;","&there4;","&sim;","&cong;","&asymp;","&ne;","&equiv;","&le;","&ge;","&sub;","&sup;","&nsub;","&sube;","&supe;","&oplus;","&otimes;","&perp;","&sdot;","&lceil;","&rceil;","&lfloor;","&rfloor;","&lang;","&rang;","&loz;","&spades;","&clubs;","&hearts;","&diams;");
var a=new Array("&#160;","&#161;","&#162;","&#163;","&#164;","&#165;","&#166;","&#167;","&#168;","&#169;","&#170;","&#171;","&#172;","&#173;","&#174;","&#175;","&#176;","&#177;","&#178;","&#179;","&#180;","&#181;","&#182;","&#183;","&#184;","&#185;","&#186;","&#187;","&#188;","&#189;","&#190;","&#191;","&#192;","&#193;","&#194;","&#195;","&#196;","&#197;","&#198;","&#199;","&#200;","&#201;","&#202;","&#203;","&#204;","&#205;","&#206;","&#207;","&#208;","&#209;","&#210;","&#211;","&#212;","&#213;","&#214;","&#215;","&#216;","&#217;","&#218;","&#219;","&#220;","&#221;","&#222;","&#223;","&#224;","&#225;","&#226;","&#227;","&#228;","&#229;","&#230;","&#231;","&#232;","&#233;","&#234;","&#235;","&#236;","&#237;","&#238;","&#239;","&#240;","&#241;","&#242;","&#243;","&#244;","&#245;","&#246;","&#247;","&#248;","&#249;","&#250;","&#251;","&#252;","&#253;","&#254;","&#255;","&#34;","&#38;","&#60;","&#62;","&#338;","&#339;","&#352;","&#353;","&#376;","&#710;","&#732;","&#8194;","&#8195;","&#8201;","&#8204;","&#8205;","&#8206;","&#8207;","&#8211;","&#8212;","&#8216;","&#8217;","&#8218;","&#8220;","&#8221;","&#8222;","&#8224;","&#8225;","&#8240;","&#8249;","&#8250;","&#8364;","&#402;","&#913;","&#914;","&#915;","&#916;","&#917;","&#918;","&#919;","&#920;","&#921;","&#922;","&#923;","&#924;","&#925;","&#926;","&#927;","&#928;","&#929;","&#931;","&#932;","&#933;","&#934;","&#935;","&#936;","&#937;","&#945;","&#946;","&#947;","&#948;","&#949;","&#950;","&#951;","&#952;","&#953;","&#954;","&#955;","&#956;","&#957;","&#958;","&#959;","&#960;","&#961;","&#962;","&#963;","&#964;","&#965;","&#966;","&#967;","&#968;","&#969;","&#977;","&#978;","&#982;","&#8226;","&#8230;","&#8242;","&#8243;","&#8254;","&#8260;","&#8472;","&#8465;","&#8476;","&#8482;","&#8501;","&#8592;","&#8593;","&#8594;","&#8595;","&#8596;","&#8629;","&#8656;","&#8657;","&#8658;","&#8659;","&#8660;","&#8704;","&#8706;","&#8707;","&#8709;","&#8711;","&#8712;","&#8713;","&#8715;","&#8719;","&#8721;","&#8722;","&#8727;","&#8730;","&#8733;","&#8734;","&#8736;","&#8743;","&#8744;","&#8745;","&#8746;","&#8747;","&#8756;","&#8764;","&#8773;","&#8776;","&#8800;","&#8801;","&#8804;","&#8805;","&#8834;","&#8835;","&#8836;","&#8838;","&#8839;","&#8853;","&#8855;","&#8869;","&#8901;","&#8968;","&#8969;","&#8970;","&#8971;","&#9001;","&#9002;","&#9674;","&#9824;","&#9827;","&#9829;","&#9830;");
return this.swapArrayVals(c,b,a)
},NumericalToHTML:function(c){var b=new Array("&#160;","&#161;","&#162;","&#163;","&#164;","&#165;","&#166;","&#167;","&#168;","&#169;","&#170;","&#171;","&#172;","&#173;","&#174;","&#175;","&#176;","&#177;","&#178;","&#179;","&#180;","&#181;","&#182;","&#183;","&#184;","&#185;","&#186;","&#187;","&#188;","&#189;","&#190;","&#191;","&#192;","&#193;","&#194;","&#195;","&#196;","&#197;","&#198;","&#199;","&#200;","&#201;","&#202;","&#203;","&#204;","&#205;","&#206;","&#207;","&#208;","&#209;","&#210;","&#211;","&#212;","&#213;","&#214;","&#215;","&#216;","&#217;","&#218;","&#219;","&#220;","&#221;","&#222;","&#223;","&#224;","&#225;","&#226;","&#227;","&#228;","&#229;","&#230;","&#231;","&#232;","&#233;","&#234;","&#235;","&#236;","&#237;","&#238;","&#239;","&#240;","&#241;","&#242;","&#243;","&#244;","&#245;","&#246;","&#247;","&#248;","&#249;","&#250;","&#251;","&#252;","&#253;","&#254;","&#255;","&#34;","&#38;","&#60;","&#62;","&#338;","&#339;","&#352;","&#353;","&#376;","&#710;","&#732;","&#8194;","&#8195;","&#8201;","&#8204;","&#8205;","&#8206;","&#8207;","&#8211;","&#8212;","&#8216;","&#8217;","&#8218;","&#8220;","&#8221;","&#8222;","&#8224;","&#8225;","&#8240;","&#8249;","&#8250;","&#8364;","&#402;","&#913;","&#914;","&#915;","&#916;","&#917;","&#918;","&#919;","&#920;","&#921;","&#922;","&#923;","&#924;","&#925;","&#926;","&#927;","&#928;","&#929;","&#931;","&#932;","&#933;","&#934;","&#935;","&#936;","&#937;","&#945;","&#946;","&#947;","&#948;","&#949;","&#950;","&#951;","&#952;","&#953;","&#954;","&#955;","&#956;","&#957;","&#958;","&#959;","&#960;","&#961;","&#962;","&#963;","&#964;","&#965;","&#966;","&#967;","&#968;","&#969;","&#977;","&#978;","&#982;","&#8226;","&#8230;","&#8242;","&#8243;","&#8254;","&#8260;","&#8472;","&#8465;","&#8476;","&#8482;","&#8501;","&#8592;","&#8593;","&#8594;","&#8595;","&#8596;","&#8629;","&#8656;","&#8657;","&#8658;","&#8659;","&#8660;","&#8704;","&#8706;","&#8707;","&#8709;","&#8711;","&#8712;","&#8713;","&#8715;","&#8719;","&#8721;","&#8722;","&#8727;","&#8730;","&#8733;","&#8734;","&#8736;","&#8743;","&#8744;","&#8745;","&#8746;","&#8747;","&#8756;","&#8764;","&#8773;","&#8776;","&#8800;","&#8801;","&#8804;","&#8805;","&#8834;","&#8835;","&#8836;","&#8838;","&#8839;","&#8853;","&#8855;","&#8869;","&#8901;","&#8968;","&#8969;","&#8970;","&#8971;","&#9001;","&#9002;","&#9674;","&#9824;","&#9827;","&#9829;","&#9830;");
var a=new Array("&nbsp;","&iexcl;","&cent;","&pound;","&curren;","&yen;","&brvbar;","&sect;","&uml;","&copy;","&ordf;","&laquo;","&not;","&shy;","&reg;","&macr;","&deg;","&plusmn;","&sup2;","&sup3;","&acute;","&micro;","&para;","&middot;","&cedil;","&sup1;","&ordm;","&raquo;","&frac14;","&frac12;","&frac34;","&iquest;","&agrave;","&aacute;","&acirc;","&atilde;","&Auml;","&aring;","&aelig;","&ccedil;","&egrave;","&eacute;","&ecirc;","&euml;","&igrave;","&iacute;","&icirc;","&iuml;","&eth;","&ntilde;","&ograve;","&oacute;","&ocirc;","&otilde;","&Ouml;","&times;","&oslash;","&ugrave;","&uacute;","&ucirc;","&Uuml;","&yacute;","&thorn;","&szlig;","&agrave;","&aacute;","&acirc;","&atilde;","&auml;","&aring;","&aelig;","&ccedil;","&egrave;","&eacute;","&ecirc;","&euml;","&igrave;","&iacute;","&icirc;","&iuml;","&eth;","&ntilde;","&ograve;","&oacute;","&ocirc;","&otilde;","&ouml;","&divide;","&Oslash;","&ugrave;","&uacute;","&ucirc;","&uuml;","&yacute;","&thorn;","&yuml;","&quot;","&amp;","&lt;","&gt;","&oelig;","&oelig;","&scaron;","&scaron;","&yuml;","&circ;","&tilde;","&ensp;","&emsp;","&thinsp;","&zwnj;","&zwj;","&lrm;","&rlm;","&ndash;","&mdash;","&lsquo;","&rsquo;","&sbquo;","&ldquo;","&rdquo;","&bdquo;","&dagger;","&dagger;","&permil;","&lsaquo;","&rsaquo;","&euro;","&fnof;","&alpha;","&beta;","&gamma;","&delta;","&epsilon;","&zeta;","&eta;","&theta;","&iota;","&kappa;","&lambda;","&mu;","&nu;","&xi;","&omicron;","&pi;","&rho;","&sigma;","&tau;","&upsilon;","&phi;","&chi;","&psi;","&omega;","&alpha;","&beta;","&gamma;","&delta;","&epsilon;","&zeta;","&eta;","&theta;","&iota;","&kappa;","&lambda;","&mu;","&nu;","&xi;","&omicron;","&pi;","&rho;","&sigmaf;","&sigma;","&tau;","&upsilon;","&phi;","&chi;","&psi;","&omega;","&thetasym;","&upsih;","&piv;","&bull;","&hellip;","&prime;","&prime;","&oline;","&frasl;","&weierp;","&image;","&real;","&trade;","&alefsym;","&larr;","&uarr;","&rarr;","&darr;","&harr;","&crarr;","&larr;","&uarr;","&rarr;","&darr;","&harr;","&forall;","&part;","&exist;","&empty;","&nabla;","&isin;","&notin;","&ni;","&prod;","&sum;","&minus;","&lowast;","&radic;","&prop;","&infin;","&ang;","&and;","&or;","&cap;","&cup;","&int;","&there4;","&sim;","&cong;","&asymp;","&ne;","&equiv;","&le;","&ge;","&sub;","&sup;","&nsub;","&sube;","&supe;","&oplus;","&otimes;","&perp;","&sdot;","&lceil;","&rceil;","&lfloor;","&rfloor;","&lang;","&rang;","&loz;","&spades;","&clubs;","&hearts;","&diams;");
return this.swapArrayVals(c,b,a)
},numEncode:function(b){if(this.isEmpty(b)){return""
}var d="";
for(var a=0;
a<b.length;
a++){var f=b.charAt(a);
if(f<" "||f>"~"){f="&#"+f.charCodeAt()+";"
}d+=f
}return d
},htmlDecode:function(e){var g,b,f=e;
if(this.isEmpty(f)){return""
}f=this.HTML2Numerical(f);
arr=f.match(/&#[0-9]{1,5};/g);
if(arr!=null){for(var a=0;
a<arr.length;
a++){b=arr[a];
g=b.substring(2,b.length-1);
if(g>=-32768&&g<=65535){f=f.replace(b,String.fromCharCode(g))
}else{f=f.replace(b,"")
}}}return f
},htmlEncode:function(a,b){if(this.isEmpty(a)){return""
}b=b|false;
if(b){if(this.EncodeType=="numerical"){a=a.replace(/&/g,"&#38;")
}else{a=a.replace(/&/g,"&amp;")
}}a=this.XSSEncode(a,false);
if(this.EncodeType=="numerical"||!b){a=this.HTML2Numerical(a)
}a=this.numEncode(a);
if(!b){a=a.replace(/&#/g,"##AMPHASH##");
if(this.EncodeType=="numerical"){a=a.replace(/&/g,"&#38;")
}else{a=a.replace(/&/g,"&amp;")
}a=a.replace(/##AMPHASH##/g,"&#")
}a=a.replace(/&#\d*([^\d;]|$)/g,"$1");
if(!b){a=this.correctEncoding(a)
}if(this.EncodeType=="entity"){a=this.NumericalToHTML(a)
}return a
},XSSEncode:function(b,a){if(!this.isEmpty(b)){a=a||true;
if(a){b=b.replace(/\'/g,"&#39;");
b=b.replace(/\"/g,"&quot;");
b=b.replace(/</g,"&lt;");
b=b.replace(/>/g,"&gt;")
}else{b=b.replace(/\'/g,"&#39;");
b=b.replace(/\"/g,"&#34;");
b=b.replace(/</g,"&#60;");
b=b.replace(/>/g,"&#62;")
}return b
}else{return""
}},hasEncoded:function(a){if(/&#[0-9]{1,5};/g.test(a)){return true
}else{if(/&[A-Z]{2,6};/gi.test(a)){return true
}else{return false
}}},stripUnicode:function(a){return a.replace(/[^\x20-\x7E]/g,"")
},correctEncoding:function(a){return a.replace(/(&amp;)(amp;)+/,"$1")
},swapArrayVals:function(f,c,b){if(this.isEmpty(f)){return""
}var e;
if(c&&b){if(c.length==b.length){for(var a=0,d=c.length;
a<d;
a++){e=new RegExp(c[a],"g");
f=f.replace(e,b[a])
}}}return f
},inArray:function(d,b){for(var c=0,a=b.length;
c<a;
c++){if(b[c]===d){return c
}}return -1
}};(function(d){d.fn.jqm=function(f){var e={overlay:50,overlayClass:"jqmOverlay",closeClass:"jqmClose",trigger:".jqModal",ajax:o,ajaxText:"",target:o,modal:o,toTop:o,onShow:o,onHide:o,onLoad:o};
return this.each(function(){if(this._jqm){return n[this._jqm].c=d.extend({},n[this._jqm].c,f)
}p++;
this._jqm=p;
n[p]={c:d.extend(e,d.jqm.params,f),a:o,w:d(this).addClass("jqmID"+p),s:p};
if(e.trigger){d(this).jqmAddTrigger(e.trigger)
}})
};
d.fn.jqmAddClose=function(f){return l(this,f,"jqmHide")
};
d.fn.jqmAddTrigger=function(f){return l(this,f,"jqmShow")
};
d.fn.jqmShow=function(e){return this.each(function(){e=e||window.event;
d.jqm.open(this._jqm,e)
})
};
d.fn.jqmHide=function(e){return this.each(function(){e=e||window.event;
d.jqm.close(this._jqm,e)
})
};
d.jqm={hash:{},open:function(B,A){var m=n[B],q=m.c,i="."+q.closeClass,v=(parseInt(m.w.css("z-index"))),v=(v>0)?v:3000,f=d("<div></div>").css({height:"100%",width:"100%",position:"fixed",left:0,top:0,"z-index":v-1,opacity:q.overlay/100});
if(m.a){return o
}m.t=A;
m.a=true;
m.w.css("z-index",v);
if(q.modal){if(!a[0]){k("bind")
}a.push(B)
}else{if(q.overlay>0){m.w.jqmAddClose(f)
}else{f=o
}}m.o=(f)?f.addClass(q.overlayClass).prependTo("body"):o;
if(c){d("html,body").css({height:"100%",width:"100%"});
if(f){f=f.css({position:"absolute"})[0];
for(var w in {Top:1,Left:1}){f.style.setExpression(w.toLowerCase(),"(_=(document.documentElement.scroll"+w+" || document.body.scroll"+w+"))+'px'")
}}}if(q.ajax){var e=q.target||m.w,x=q.ajax,e=(typeof e=="string")?d(e,m.w):d(e),x=(x.substr(0,1)=="@")?d(A).attr(x.substring(1)):x;
e.html(q.ajaxText).load(x,function(){if(q.onLoad){q.onLoad.call(this,m)
}if(i){m.w.jqmAddClose(d(i,m.w))
}j(m)
})
}else{if(i){m.w.jqmAddClose(d(i,m.w))
}}if(q.toTop&&m.o){m.w.before('<span id="jqmP'+m.w[0]._jqm+'"></span>').insertAfter(m.o)
}(q.onShow)?q.onShow(m):m.w.show();
j(m);
return o
},close:function(f){var e=n[f];
if(!e.a){return o
}e.a=o;
if(a[0]){a.pop();
if(!a[0]){k("unbind")
}}if(e.c.toTop&&e.o){d("#jqmP"+e.w[0]._jqm).after(e.w).remove()
}if(e.c.onHide){e.c.onHide(e)
}else{e.w.hide();
if(e.o){e.o.remove()
}}return o
},params:{}};
var p=0,n=d.jqm.hash,a=[],c=d.browser.msie&&(d.browser.version=="6.0"),o=false,g=d('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({opacity:0}),j=function(e){if(c){if(e.o){e.o.html('<p style="width:100%;height:100%"/>').prepend(g)
}else{if(!d("iframe.jqm",e.w)[0]){e.w.prepend(g)
}}}h(e)
},h=function(f){try{d(":input:visible",f.w)[0].focus()
}catch(e){}},k=function(e){d()[e]("keypress",b)[e]("keydown",b)[e]("mousedown",b)
},b=function(m){var f=n[a[a.length-1]],i=(!d(m.target).parents(".jqmID"+f.s)[0]);
if(i){h(f)
}return !i
},l=function(e,f,i){return e.each(function(){var m=this._jqm;
d(f).each(function(){if(!this[i]){this[i]=[];
d(this).click(function(){for(var q in {jqmShow:1,jqmHide:1}){for(var r in this[q]){if(n[this[q][r]]){n[this[q][r]].w[q](this)
}}}return o
})
}this[i].push(m)
})
})
}
})(jQuery);/*
 * jQuery UI 1.8.5
 *
 * Copyright 2010, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI
 */
(function(d,b){function a(c){return !d(c).parents().andSelf().filter(function(){return d.curCSS(this,"visibility")==="hidden"||d.expr.filters.hidden(this)
}).length
}d.ui=d.ui||{};
if(!d.ui.version){d.extend(d.ui,{version:"1.8.5",keyCode:{ALT:18,BACKSPACE:8,CAPS_LOCK:20,COMMA:188,COMMAND:91,COMMAND_LEFT:91,COMMAND_RIGHT:93,CONTROL:17,DELETE:46,DOWN:40,END:35,ENTER:13,ESCAPE:27,HOME:36,INSERT:45,LEFT:37,MENU:93,NUMPAD_ADD:107,NUMPAD_DECIMAL:110,NUMPAD_DIVIDE:111,NUMPAD_ENTER:108,NUMPAD_MULTIPLY:106,NUMPAD_SUBTRACT:109,PAGE_DOWN:34,PAGE_UP:33,PERIOD:190,RIGHT:39,SHIFT:16,SPACE:32,TAB:9,UP:38,WINDOWS:91}});
d.fn.extend({_focus:d.fn.focus,focus:function(e,c){return typeof e==="number"?this.each(function(){var f=this;
setTimeout(function(){d(f).focus();
c&&c.call(f)
},e)
}):this._focus.apply(this,arguments)
},scrollParent:function(){var c;
c=d.browser.msie&&/(static|relative)/.test(this.css("position"))||/absolute/.test(this.css("position"))?this.parents().filter(function(){return/(relative|absolute|fixed)/.test(d.curCSS(this,"position",1))&&/(auto|scroll)/.test(d.curCSS(this,"overflow",1)+d.curCSS(this,"overflow-y",1)+d.curCSS(this,"overflow-x",1))
}).eq(0):this.parents().filter(function(){return/(auto|scroll)/.test(d.curCSS(this,"overflow",1)+d.curCSS(this,"overflow-y",1)+d.curCSS(this,"overflow-x",1))
}).eq(0);
return/fixed/.test(this.css("position"))||!c.length?d(document):c
},zIndex:function(e){if(e!==b){return this.css("zIndex",e)
}if(this.length){e=d(this[0]);
for(var c;
e.length&&e[0]!==document;
){c=e.css("position");
if(c==="absolute"||c==="relative"||c==="fixed"){c=parseInt(e.css("zIndex"));
if(!isNaN(c)&&c!=0){return c
}}e=e.parent()
}}return 0
},disableSelection:function(){return this.bind("mousedown.ui-disableSelection selectstart.ui-disableSelection",function(c){c.preventDefault()
})
},enableSelection:function(){return this.unbind(".ui-disableSelection")
}});
d.each(["Width","Height"],function(f,c){function l(n,i,h,e){d.each(k,function(){i-=parseFloat(d.curCSS(n,"padding"+this,true))||0;
if(h){i-=parseFloat(d.curCSS(n,"border"+this+"Width",true))||0
}if(e){i-=parseFloat(d.curCSS(n,"margin"+this,true))||0
}});
return i
}var k=c==="Width"?["Left","Right"]:["Top","Bottom"],j=c.toLowerCase(),g={innerWidth:d.fn.innerWidth,innerHeight:d.fn.innerHeight,outerWidth:d.fn.outerWidth,outerHeight:d.fn.outerHeight};
d.fn["inner"+c]=function(e){if(e===b){return g["inner"+c].call(this)
}return this.each(function(){d.style(this,j,l(this,e)+"px")
})
};
d.fn["outer"+c]=function(h,e){if(typeof h!=="number"){return g["outer"+c].call(this,h)
}return this.each(function(){d.style(this,j,l(this,h,true,e)+"px")
})
}
});
d.extend(d.expr[":"],{data:function(e,c,f){return !!d.data(e,f[3])
},focusable:function(e){var c=e.nodeName.toLowerCase(),f=d.attr(e,"tabindex");
if("area"===c){c=e.parentNode;
f=c.name;
if(!e.href||!f||c.nodeName.toLowerCase()!=="map"){return false
}e=d("img[usemap=#"+f+"]")[0];
return !!e&&a(e)
}return(/input|select|textarea|button|object/.test(c)?!e.disabled:"a"==c?e.href||!isNaN(f):!isNaN(f))&&a(e)
},tabbable:function(e){var c=d.attr(e,"tabindex");
return(isNaN(c)||c>=0)&&d(e).is(":focusable")
}});
d(function(){var e=document.createElement("div"),c=document.body;
d.extend(e.style,{minHeight:"100px",height:"auto",padding:0,borderWidth:0});
d.support.minHeight=c.appendChild(e).offsetHeight===100;
c.removeChild(e).style.display="none"
});
d.extend(d.ui,{plugin:{add:function(f,c,h){f=d.ui[f].prototype;
for(var g in h){f.plugins[g]=f.plugins[g]||[];
f.plugins[g].push([c,h[g]])
}},call:function(f,c,h){if((c=f.plugins[c])&&f.element[0].parentNode){for(var g=0;
g<c.length;
g++){f.options[c[g][0]]&&c[g][1].apply(f.element,h)
}}}},contains:function(e,c){return document.compareDocumentPosition?e.compareDocumentPosition(c)&16:e!==c&&e.contains(c)
},hasScroll:function(e,c){if(d(e).css("overflow")==="hidden"){return false
}c=c&&c==="left"?"scrollLeft":"scrollTop";
var f=false;
if(e[c]>0){return true
}e[c]=1;
f=e[c]>0;
e[c]=0;
return f
},isOverAxis:function(e,c,f){return e>c&&e<c+f
},isOver:function(f,c,l,k,j,g){return d.ui.isOverAxis(f,l,j)&&d.ui.isOverAxis(c,k,g)
}})
}})(jQuery);
(function(d,G){function L(){this.debug=false;
this._curInst=null;
this._keyEvent=false;
this._disabledInputs=[];
this._inDialog=this._datepickerShowing=false;
this._mainDivId="ui-datepicker-div";
this._inlineClass="ui-datepicker-inline";
this._appendClass="ui-datepicker-append";
this._triggerClass="ui-datepicker-trigger";
this._dialogClass="ui-datepicker-dialog";
this._disableClass="ui-datepicker-disabled";
this._unselectableClass="ui-datepicker-unselectable";
this._currentClass="ui-datepicker-current-day";
this._dayOverClass="ui-datepicker-days-cell-over";
this.regional=[];
this.regional[""]={closeText:"Done",prevText:"Prev",nextText:"Next",currentText:"Today",monthNames:["January","February","March","April","May","June","July","August","September","October","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],dayNames:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],dayNamesShort:["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],dayNamesMin:["Su","Mo","Tu","We","Th","Fr","Sa"],weekHeader:"Wk",dateFormat:"mm/dd/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
this._defaults={showOn:"focus",showAnim:"fadeIn",showOptions:{},defaultDate:null,appendText:"",buttonText:"...",buttonImage:"",buttonImageOnly:false,hideIfNoPrevNext:false,navigationAsDateFormat:false,gotoCurrent:false,changeMonth:false,changeYear:false,yearRange:"c-10:c+10",showOtherMonths:false,selectOtherMonths:false,showWeek:false,calculateWeek:this.iso8601Week,shortYearCutoff:"+10",minDate:null,maxDate:null,duration:"fast",beforeShowDay:null,beforeShow:null,onSelect:null,onChangeMonthYear:null,onClose:null,numberOfMonths:1,showCurrentAtPos:0,stepMonths:1,stepBigMonths:12,altField:"",altFormat:"",constrainInput:true,showButtonPanel:false,autoSize:false};
d.extend(this._defaults,this.regional[""]);
this.dpDiv=d('<div id="'+this._mainDivId+'" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all ui-helper-hidden-accessible"></div>')
}function E(a,b){d.extend(a,b);
for(var c in b){if(b[c]==null||b[c]==G){a[c]=b[c]
}}return a
}d.extend(d.ui,{datepicker:{version:"1.8.5"}});
var y=(new Date).getTime();
d.extend(L.prototype,{markerClassName:"hasDatepicker",log:function(){this.debug&&console.log.apply("",arguments)
},_widgetDatepicker:function(){return this.dpDiv
},setDefaults:function(a){E(this._defaults,a||{});
return this
},_attachDatepicker:function(a,b){var c=null;
for(var e in this._defaults){var f=a.getAttribute("date:"+e);
if(f){c=c||{};
try{c[e]=eval(f)
}catch(h){c[e]=f
}}}e=a.nodeName.toLowerCase();
f=e=="div"||e=="span";
if(!a.id){this.uuid+=1;
a.id="dp"+this.uuid
}var i=this._newInst(d(a),f);
i.settings=d.extend({},b||{},c||{});
if(e=="input"){this._connectDatepicker(a,i)
}else{f&&this._inlineDatepicker(a,i)
}},_newInst:function(a,b){return{id:a[0].id.replace(/([^A-Za-z0-9_])/g,"\\\\$1"),input:a,selectedDay:0,selectedMonth:0,selectedYear:0,drawMonth:0,drawYear:0,inline:b,dpDiv:!b?this.dpDiv:d('<div class="'+this._inlineClass+' ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>')}
},_connectDatepicker:function(a,b){var c=d(a);
b.append=d([]);
b.trigger=d([]);
if(!c.hasClass(this.markerClassName)){this._attachments(c,b);
c.addClass(this.markerClassName).keydown(this._doKeyDown).keypress(this._doKeyPress).keyup(this._doKeyUp).bind("setData.datepicker",function(e,f,h){b.settings[f]=h
}).bind("getData.datepicker",function(e,f){return this._get(b,f)
});
this._autoSize(b);
d.data(a,"datepicker",b)
}},_attachments:function(a,b){var c=this._get(b,"appendText"),e=this._get(b,"isRTL");
b.append&&b.append.remove();
if(c){b.append=d('<span class="'+this._appendClass+'">'+c+"</span>");
a[e?"before":"after"](b.append)
}a.unbind("focus",this._showDatepicker);
b.trigger&&b.trigger.remove();
c=this._get(b,"showOn");
if(c=="focus"||c=="both"){a.focus(this._showDatepicker)
}if(c=="button"||c=="both"){c=this._get(b,"buttonText");
var f=this._get(b,"buttonImage");
b.trigger=d(this._get(b,"buttonImageOnly")?d("<img/>").addClass(this._triggerClass).attr({src:f,alt:c,title:c}):d('<button type="button"></button>').addClass(this._triggerClass).html(f==""?c:d("<img/>").attr({src:f,alt:c,title:c})));
a[e?"before":"after"](b.trigger);
b.trigger.click(function(){d.datepicker._datepickerShowing&&d.datepicker._lastInput==a[0]?d.datepicker._hideDatepicker():d.datepicker._showDatepicker(a[0]);
return false
})
}},_autoSize:function(a){if(this._get(a,"autoSize")&&!a.inline){var b=new Date(2009,11,20),c=this._get(a,"dateFormat");
if(c.match(/[DM]/)){var e=function(f){for(var h=0,i=0,g=0;
g<f.length;
g++){if(f[g].length>h){h=f[g].length;
i=g
}}return i
};
b.setMonth(e(this._get(a,c.match(/MM/)?"monthNames":"monthNamesShort")));
b.setDate(e(this._get(a,c.match(/DD/)?"dayNames":"dayNamesShort"))+20-b.getDay())
}a.input.attr("size",this._formatDate(a,b).length)
}},_inlineDatepicker:function(a,b){var c=d(a);
if(!c.hasClass(this.markerClassName)){c.addClass(this.markerClassName).append(b.dpDiv).bind("setData.datepicker",function(e,f,h){b.settings[f]=h
}).bind("getData.datepicker",function(e,f){return this._get(b,f)
});
d.data(a,"datepicker",b);
this._setDate(b,this._getDefaultDate(b),true);
this._updateDatepicker(b);
this._updateAlternate(b)
}},_dialogDatepicker:function(a,b,c,e,f){a=this._dialogInst;
if(!a){this.uuid+=1;
this._dialogInput=d('<input type="text" id="'+("dp"+this.uuid)+'" style="position: absolute; top: -100px; width: 0px; z-index: -10;"/>');
this._dialogInput.keydown(this._doKeyDown);
d("body").append(this._dialogInput);
a=this._dialogInst=this._newInst(this._dialogInput,false);
a.settings={};
d.data(this._dialogInput[0],"datepicker",a)
}E(a.settings,e||{});
b=b&&b.constructor==Date?this._formatDate(a,b):b;
this._dialogInput.val(b);
this._pos=f?f.length?f:[f.pageX,f.pageY]:null;
if(!this._pos){this._pos=[document.documentElement.clientWidth/2-100+(document.documentElement.scrollLeft||document.body.scrollLeft),document.documentElement.clientHeight/2-150+(document.documentElement.scrollTop||document.body.scrollTop)]
}this._dialogInput.css("left",this._pos[0]+20+"px").css("top",this._pos[1]+"px");
a.settings.onSelect=c;
this._inDialog=true;
this.dpDiv.addClass(this._dialogClass);
this._showDatepicker(this._dialogInput[0]);
d.blockUI&&d.blockUI(this.dpDiv);
d.data(this._dialogInput[0],"datepicker",a);
return this
},_destroyDatepicker:function(a){var b=d(a),c=d.data(a,"datepicker");
if(b.hasClass(this.markerClassName)){var e=a.nodeName.toLowerCase();
d.removeData(a,"datepicker");
if(e=="input"){c.append.remove();
c.trigger.remove();
b.removeClass(this.markerClassName).unbind("focus",this._showDatepicker).unbind("keydown",this._doKeyDown).unbind("keypress",this._doKeyPress).unbind("keyup",this._doKeyUp)
}else{if(e=="div"||e=="span"){b.removeClass(this.markerClassName).empty()
}}}},_enableDatepicker:function(a){var b=d(a),c=d.data(a,"datepicker");
if(b.hasClass(this.markerClassName)){var e=a.nodeName.toLowerCase();
if(e=="input"){a.disabled=false;
c.trigger.filter("button").each(function(){this.disabled=false
}).end().filter("img").css({opacity:"1.0",cursor:""})
}else{if(e=="div"||e=="span"){b.children("."+this._inlineClass).children().removeClass("ui-state-disabled")
}}this._disabledInputs=d.map(this._disabledInputs,function(f){return f==a?null:f
})
}},_disableDatepicker:function(a){var b=d(a),c=d.data(a,"datepicker");
if(b.hasClass(this.markerClassName)){var e=a.nodeName.toLowerCase();
if(e=="input"){a.disabled=true;
c.trigger.filter("button").each(function(){this.disabled=true
}).end().filter("img").css({opacity:"0.5",cursor:"default"})
}else{if(e=="div"||e=="span"){b.children("."+this._inlineClass).children().addClass("ui-state-disabled")
}}this._disabledInputs=d.map(this._disabledInputs,function(f){return f==a?null:f
});
this._disabledInputs[this._disabledInputs.length]=a
}},_isDisabledDatepicker:function(a){if(!a){return false
}for(var b=0;
b<this._disabledInputs.length;
b++){if(this._disabledInputs[b]==a){return true
}}return false
},_getInst:function(a){try{return d.data(a,"datepicker")
}catch(b){throw"Missing instance data for this datepicker"
}},_optionDatepicker:function(a,b,c){var e=this._getInst(a);
if(arguments.length==2&&typeof b=="string"){return b=="defaults"?d.extend({},d.datepicker._defaults):e?b=="all"?d.extend({},e.settings):this._get(e,b):null
}var f=b||{};
if(typeof b=="string"){f={};
f[b]=c
}if(e){this._curInst==e&&this._hideDatepicker();
var h=this._getDateDatepicker(a,true);
E(e.settings,f);
this._attachments(d(a),e);
this._autoSize(e);
this._setDateDatepicker(a,h);
this._updateDatepicker(e)
}},_changeDatepicker:function(a,b,c){this._optionDatepicker(a,b,c)
},_refreshDatepicker:function(a){(a=this._getInst(a))&&this._updateDatepicker(a)
},_setDateDatepicker:function(a,b){if(a=this._getInst(a)){this._setDate(a,b);
this._updateDatepicker(a);
this._updateAlternate(a)
}},_getDateDatepicker:function(a,b){(a=this._getInst(a))&&!a.inline&&this._setDateFromField(a,b);
return a?this._getDate(a):null
},_doKeyDown:function(a){var b=d.datepicker._getInst(a.target),c=true,e=b.dpDiv.is(".ui-datepicker-rtl");
b._keyEvent=true;
if(d.datepicker._datepickerShowing){switch(a.keyCode){case 9:d.datepicker._hideDatepicker();
c=false;
break;
case 13:c=d("td."+d.datepicker._dayOverClass,b.dpDiv).add(d("td."+d.datepicker._currentClass,b.dpDiv));
c[0]?d.datepicker._selectDay(a.target,b.selectedMonth,b.selectedYear,c[0]):d.datepicker._hideDatepicker();
return false;
case 27:d.datepicker._hideDatepicker();
break;
case 33:d.datepicker._adjustDate(a.target,a.ctrlKey?-d.datepicker._get(b,"stepBigMonths"):-d.datepicker._get(b,"stepMonths"),"M");
break;
case 34:d.datepicker._adjustDate(a.target,a.ctrlKey?+d.datepicker._get(b,"stepBigMonths"):+d.datepicker._get(b,"stepMonths"),"M");
break;
case 35:if(a.ctrlKey||a.metaKey){d.datepicker._clearDate(a.target)
}c=a.ctrlKey||a.metaKey;
break;
case 36:if(a.ctrlKey||a.metaKey){d.datepicker._gotoToday(a.target)
}c=a.ctrlKey||a.metaKey;
break;
case 37:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,e?+1:-1,"D")
}c=a.ctrlKey||a.metaKey;
if(a.originalEvent.altKey){d.datepicker._adjustDate(a.target,a.ctrlKey?-d.datepicker._get(b,"stepBigMonths"):-d.datepicker._get(b,"stepMonths"),"M")
}break;
case 38:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,-7,"D")
}c=a.ctrlKey||a.metaKey;
break;
case 39:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,e?-1:+1,"D")
}c=a.ctrlKey||a.metaKey;
if(a.originalEvent.altKey){d.datepicker._adjustDate(a.target,a.ctrlKey?+d.datepicker._get(b,"stepBigMonths"):+d.datepicker._get(b,"stepMonths"),"M")
}break;
case 40:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,+7,"D")
}c=a.ctrlKey||a.metaKey;
break;
default:c=false
}}else{if(a.keyCode==36&&a.ctrlKey){d.datepicker._showDatepicker(this)
}else{c=false
}}if(c){a.preventDefault();
a.stopPropagation()
}},_doKeyPress:function(a){var b=d.datepicker._getInst(a.target);
if(d.datepicker._get(b,"constrainInput")){b=d.datepicker._possibleChars(d.datepicker._get(b,"dateFormat"));
var c=String.fromCharCode(a.charCode==G?a.keyCode:a.charCode);
return a.ctrlKey||c<" "||!b||b.indexOf(c)>-1
}},_doKeyUp:function(a){a=d.datepicker._getInst(a.target);
if(a.input.val()!=a.lastVal){try{if(d.datepicker.parseDate(d.datepicker._get(a,"dateFormat"),a.input?a.input.val():null,d.datepicker._getFormatConfig(a))){d.datepicker._setDateFromField(a);
d.datepicker._updateAlternate(a);
d.datepicker._updateDatepicker(a)
}}catch(b){d.datepicker.log(b)
}}return true
},_showDatepicker:function(a){a=a.target||a;
if(a.nodeName.toLowerCase()!="input"){a=d("input",a.parentNode)[0]
}if(!(d.datepicker._isDisabledDatepicker(a)||d.datepicker._lastInput==a)){var b=d.datepicker._getInst(a);
d.datepicker._curInst&&d.datepicker._curInst!=b&&d.datepicker._curInst.dpDiv.stop(true,true);
var c=d.datepicker._get(b,"beforeShow");
E(b.settings,c?c.apply(a,[a,b]):{});
b.lastVal=null;
d.datepicker._lastInput=a;
d.datepicker._setDateFromField(b);
if(d.datepicker._inDialog){a.value=""
}if(!d.datepicker._pos){d.datepicker._pos=d.datepicker._findPos(a);
d.datepicker._pos[1]+=a.offsetHeight
}var e=false;
d(a).parents().each(function(){e|=d(this).css("position")=="fixed";
return !e
});
if(e&&d.browser.opera){d.datepicker._pos[0]-=document.documentElement.scrollLeft;
d.datepicker._pos[1]-=document.documentElement.scrollTop
}c={left:d.datepicker._pos[0],top:d.datepicker._pos[1]};
d.datepicker._pos=null;
b.dpDiv.css({position:"absolute",display:"block",top:"-1000px"});
d.datepicker._updateDatepicker(b);
c=d.datepicker._checkOffset(b,c,e);
b.dpDiv.css({position:d.datepicker._inDialog&&d.blockUI?"static":e?"fixed":"absolute",display:"none",left:c.left+"px",top:c.top+"px"});
if(!b.inline){c=d.datepicker._get(b,"showAnim");
var f=d.datepicker._get(b,"duration"),h=function(){d.datepicker._datepickerShowing=true;
var i=d.datepicker._getBorders(b.dpDiv);
b.dpDiv.find("iframe.ui-datepicker-cover").css({left:-i[0],top:-i[1],width:b.dpDiv.outerWidth(),height:b.dpDiv.outerHeight()})
};
b.dpDiv.zIndex(d(a).zIndex()+1);
d.effects&&d.effects[c]?b.dpDiv.show(c,d.datepicker._get(b,"showOptions"),f,h):b.dpDiv[c||"show"](c?f:null,h);
if(!c||!f){h()
}b.input.is(":visible")&&!b.input.is(":disabled")&&b.input.focus();
d.datepicker._curInst=b
}}},_updateDatepicker:function(a){var b=this,c=d.datepicker._getBorders(a.dpDiv);
a.dpDiv.empty().append(this._generateHTML(a)).find("iframe.ui-datepicker-cover").css({left:-c[0],top:-c[1],width:a.dpDiv.outerWidth(),height:a.dpDiv.outerHeight()}).end().find("button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a").bind("mouseout",function(){d(this).removeClass("ui-state-hover");
this.className.indexOf("ui-datepicker-prev")!=-1&&d(this).removeClass("ui-datepicker-prev-hover");
this.className.indexOf("ui-datepicker-next")!=-1&&d(this).removeClass("ui-datepicker-next-hover")
}).bind("mouseover",function(){if(!b._isDisabledDatepicker(a.inline?a.dpDiv.parent()[0]:a.input[0])){d(this).parents(".ui-datepicker-calendar").find("a").removeClass("ui-state-hover");
d(this).addClass("ui-state-hover");
this.className.indexOf("ui-datepicker-prev")!=-1&&d(this).addClass("ui-datepicker-prev-hover");
this.className.indexOf("ui-datepicker-next")!=-1&&d(this).addClass("ui-datepicker-next-hover")
}}).end().find("."+this._dayOverClass+" a").trigger("mouseover").end();
c=this._getNumberOfMonths(a);
var e=c[1];
e>1?a.dpDiv.addClass("ui-datepicker-multi-"+e).css("width",17*e+"em"):a.dpDiv.removeClass("ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4").width("");
a.dpDiv[(c[0]!=1||c[1]!=1?"add":"remove")+"Class"]("ui-datepicker-multi");
a.dpDiv[(this._get(a,"isRTL")?"add":"remove")+"Class"]("ui-datepicker-rtl");
a==d.datepicker._curInst&&d.datepicker._datepickerShowing&&a.input&&a.input.is(":visible")&&!a.input.is(":disabled")&&a.input.focus()
},_getBorders:function(a){var b=function(c){return{thin:1,medium:2,thick:3}[c]||c
};
return[parseFloat(b(a.css("border-left-width"))),parseFloat(b(a.css("border-top-width")))]
},_checkOffset:function(a,b,c){var e=a.dpDiv.outerWidth(),f=a.dpDiv.outerHeight(),h=a.input?a.input.outerWidth():0,i=a.input?a.input.outerHeight():0,g=document.documentElement.clientWidth+d(document).scrollLeft(),k=document.documentElement.clientHeight+d(document).scrollTop();
b.left-=this._get(a,"isRTL")?e-h:0;
b.left-=c&&b.left==a.input.offset().left?d(document).scrollLeft():0;
b.top-=c&&b.top==a.input.offset().top+i?d(document).scrollTop():0;
b.left-=Math.min(b.left,b.left+e>g&&g>e?Math.abs(b.left+e-g):0);
b.top-=Math.min(b.top,b.top+f>k&&k>f?Math.abs(f+i):0);
return b
},_findPos:function(a){for(var b=this._get(this._getInst(a),"isRTL");
a&&(a.type=="hidden"||a.nodeType!=1);
){a=a[b?"previousSibling":"nextSibling"]
}a=d(a).offset();
return[a.left,a.top]
},_hideDatepicker:function(a){var b=this._curInst;
if(!(!b||a&&b!=d.data(a,"datepicker"))){if(this._datepickerShowing){a=this._get(b,"showAnim");
var c=this._get(b,"duration"),e=function(){d.datepicker._tidyDialog(b);
this._curInst=null
};
d.effects&&d.effects[a]?b.dpDiv.hide(a,d.datepicker._get(b,"showOptions"),c,e):b.dpDiv[a=="slideDown"?"slideUp":a=="fadeIn"?"fadeOut":"hide"](a?c:null,e);
a||e();
if(a=this._get(b,"onClose")){a.apply(b.input?b.input[0]:null,[b.input?b.input.val():"",b])
}this._datepickerShowing=false;
this._lastInput=null;
if(this._inDialog){this._dialogInput.css({position:"absolute",left:"0",top:"-100px"});
if(d.blockUI){d.unblockUI();
d("body").append(this.dpDiv)
}}this._inDialog=false
}}},_tidyDialog:function(a){a.dpDiv.removeClass(this._dialogClass).unbind(".ui-datepicker-calendar")
},_checkExternalClick:function(a){if(d.datepicker._curInst){a=d(a.target);
a[0].id!=d.datepicker._mainDivId&&a.parents("#"+d.datepicker._mainDivId).length==0&&!a.hasClass(d.datepicker.markerClassName)&&!a.hasClass(d.datepicker._triggerClass)&&d.datepicker._datepickerShowing&&!(d.datepicker._inDialog&&d.blockUI)&&d.datepicker._hideDatepicker()
}},_adjustDate:function(a,b,c){a=d(a);
var e=this._getInst(a[0]);
if(!this._isDisabledDatepicker(a[0])){this._adjustInstDate(e,b+(c=="M"?this._get(e,"showCurrentAtPos"):0),c);
this._updateDatepicker(e)
}},_gotoToday:function(a){a=d(a);
var b=this._getInst(a[0]);
if(this._get(b,"gotoCurrent")&&b.currentDay){b.selectedDay=b.currentDay;
b.drawMonth=b.selectedMonth=b.currentMonth;
b.drawYear=b.selectedYear=b.currentYear
}else{var c=new Date;
b.selectedDay=c.getDate();
b.drawMonth=b.selectedMonth=c.getMonth();
b.drawYear=b.selectedYear=c.getFullYear()
}this._notifyChange(b);
this._adjustDate(a)
},_selectMonthYear:function(a,b,c){a=d(a);
var e=this._getInst(a[0]);
e._selectingMonthYear=false;
e["selected"+(c=="M"?"Month":"Year")]=e["draw"+(c=="M"?"Month":"Year")]=parseInt(b.options[b.selectedIndex].value,10);
this._notifyChange(e);
this._adjustDate(a)
},_clickMonthYear:function(a){var b=this._getInst(d(a)[0]);
b.input&&b._selectingMonthYear&&setTimeout(function(){b.input.focus()
},0);
b._selectingMonthYear=!b._selectingMonthYear
},_selectDay:function(a,b,c,e){var f=d(a);
if(!(d(e).hasClass(this._unselectableClass)||this._isDisabledDatepicker(f[0]))){f=this._getInst(f[0]);
f.selectedDay=f.currentDay=d("a",e).html();
f.selectedMonth=f.currentMonth=b;
f.selectedYear=f.currentYear=c;
this._selectDate(a,this._formatDate(f,f.currentDay,f.currentMonth,f.currentYear))
}},_clearDate:function(a){a=d(a);
this._getInst(a[0]);
this._selectDate(a,"")
},_selectDate:function(a,b){a=this._getInst(d(a)[0]);
b=b!=null?b:this._formatDate(a);
a.input&&a.input.val(b);
this._updateAlternate(a);
var c=this._get(a,"onSelect");
if(c){c.apply(a.input?a.input[0]:null,[b,a])
}else{a.input&&a.input.trigger("change")
}if(a.inline){this._updateDatepicker(a)
}else{this._hideDatepicker();
this._lastInput=a.input[0];
typeof a.input[0]!="object"&&a.input.focus();
this._lastInput=null
}},_updateAlternate:function(a){var b=this._get(a,"altField");
if(b){var c=this._get(a,"altFormat")||this._get(a,"dateFormat"),e=this._getDate(a),f=this.formatDate(c,e,this._getFormatConfig(a));
d(b).each(function(){d(this).val(f)
})
}},noWeekends:function(a){a=a.getDay();
return[a>0&&a<6,""]
},iso8601Week:function(a){a=new Date(a.getTime());
a.setDate(a.getDate()+4-(a.getDay()||7));
var b=a.getTime();
a.setMonth(0);
a.setDate(1);
return Math.floor(Math.round((b-a)/86400000)/7)+1
},parseDate:function(a,b,c){if(a==null||b==null){throw"Invalid arguments"
}b=typeof b=="object"?b.toString():b+"";
if(b==""){return null
}for(var e=(c?c.shortYearCutoff:null)||this._defaults.shortYearCutoff,f=(c?c.dayNamesShort:null)||this._defaults.dayNamesShort,h=(c?c.dayNames:null)||this._defaults.dayNames,i=(c?c.monthNamesShort:null)||this._defaults.monthNamesShort,g=(c?c.monthNames:null)||this._defaults.monthNames,k=c=-1,l=-1,u=-1,j=false,o=function(p){(p=z+1<a.length&&a.charAt(z+1)==p)&&z++;
return p
},m=function(p){o(p);
p=new RegExp("^\\d{1,"+(p=="@"?14:p=="!"?20:p=="y"?4:p=="o"?3:2)+"}");
p=b.substring(s).match(p);
if(!p){throw"Missing number at position "+s
}s+=p[0].length;
return parseInt(p[0],10)
},n=function(p,w,H){p=o(p)?H:w;
for(w=0;
w<p.length;
w++){if(b.substr(s,p[w].length).toLowerCase()==p[w].toLowerCase()){s+=p[w].length;
return w+1
}}throw"Unknown name at position "+s
},r=function(){if(b.charAt(s)!=a.charAt(z)){throw"Unexpected literal at position "+s
}s++
},s=0,z=0;
z<a.length;
z++){if(j){if(a.charAt(z)=="'"&&!o("'")){j=false
}else{r()
}}else{switch(a.charAt(z)){case"d":l=m("d");
break;
case"D":n("D",f,h);
break;
case"o":u=m("o");
break;
case"m":k=m("m");
break;
case"M":k=n("M",i,g);
break;
case"y":c=m("y");
break;
case"@":var v=new Date(m("@"));
c=v.getFullYear();
k=v.getMonth()+1;
l=v.getDate();
break;
case"!":v=new Date((m("!")-this._ticksTo1970)/10000);
c=v.getFullYear();
k=v.getMonth()+1;
l=v.getDate();
break;
case"'":if(o("'")){r()
}else{j=true
}break;
default:r()
}}}if(c==-1){c=(new Date).getFullYear()
}else{if(c<100){c+=(new Date).getFullYear()-(new Date).getFullYear()%100+(c<=e?0:-100)
}}if(u>-1){k=1;
l=u;
do{e=this._getDaysInMonth(c,k-1);
if(l<=e){break
}k++;
l-=e
}while(1)
}v=this._daylightSavingAdjust(new Date(c,k-1,l));
if(v.getFullYear()!=c||v.getMonth()+1!=k||v.getDate()!=l){throw"Invalid date"
}return v
},ATOM:"yy-mm-dd",COOKIE:"D, dd M yy",ISO_8601:"yy-mm-dd",RFC_822:"D, d M y",RFC_850:"DD, dd-M-y",RFC_1036:"D, d M y",RFC_1123:"D, d M yy",RFC_2822:"D, d M yy",RSS:"D, d M y",TICKS:"!",TIMESTAMP:"@",W3C:"yy-mm-dd",_ticksTo1970:(718685+Math.floor(492.5)-Math.floor(19.7)+Math.floor(4.925))*24*60*60*10000000,formatDate:function(a,b,c){if(!b){return""
}var e=(c?c.dayNamesShort:null)||this._defaults.dayNamesShort,f=(c?c.dayNames:null)||this._defaults.dayNames,h=(c?c.monthNamesShort:null)||this._defaults.monthNamesShort;
c=(c?c.monthNames:null)||this._defaults.monthNames;
var i=function(o){(o=j+1<a.length&&a.charAt(j+1)==o)&&j++;
return o
},g=function(o,m,n){m=""+m;
if(i(o)){for(;
m.length<n;
){m="0"+m
}}return m
},k=function(o,m,n,r){return i(o)?r[m]:n[m]
},l="",u=false;
if(b){for(var j=0;
j<a.length;
j++){if(u){if(a.charAt(j)=="'"&&!i("'")){u=false
}else{l+=a.charAt(j)
}}else{switch(a.charAt(j)){case"d":l+=g("d",b.getDate(),2);
break;
case"D":l+=k("D",b.getDay(),e,f);
break;
case"o":l+=g("o",(b.getTime()-(new Date(b.getFullYear(),0,0)).getTime())/86400000,3);
break;
case"m":l+=g("m",b.getMonth()+1,2);
break;
case"M":l+=k("M",b.getMonth(),h,c);
break;
case"y":l+=i("y")?b.getFullYear():(b.getYear()%100<10?"0":"")+b.getYear()%100;
break;
case"@":l+=b.getTime();
break;
case"!":l+=b.getTime()*10000+this._ticksTo1970;
break;
case"'":if(i("'")){l+="'"
}else{u=true
}break;
default:l+=a.charAt(j)
}}}}return l
},_possibleChars:function(a){for(var b="",c=false,e=function(h){(h=f+1<a.length&&a.charAt(f+1)==h)&&f++;
return h
},f=0;
f<a.length;
f++){if(c){if(a.charAt(f)=="'"&&!e("'")){c=false
}else{b+=a.charAt(f)
}}else{switch(a.charAt(f)){case"d":case"m":case"y":case"@":b+="0123456789";
break;
case"D":case"M":return null;
case"'":if(e("'")){b+="'"
}else{c=true
}break;
default:b+=a.charAt(f)
}}}return b
},_get:function(a,b){return a.settings[b]!==G?a.settings[b]:this._defaults[b]
},_setDateFromField:function(a,b){if(a.input.val()!=a.lastVal){var c=this._get(a,"dateFormat"),e=a.lastVal=a.input?a.input.val():null,f,h;
f=h=this._getDefaultDate(a);
var i=this._getFormatConfig(a);
try{f=this.parseDate(c,e,i)||h
}catch(g){this.log(g);
e=b?"":e
}a.selectedDay=f.getDate();
a.drawMonth=a.selectedMonth=f.getMonth();
a.drawYear=a.selectedYear=f.getFullYear();
a.currentDay=e?f.getDate():0;
a.currentMonth=e?f.getMonth():0;
a.currentYear=e?f.getFullYear():0;
this._adjustInstDate(a)
}},_getDefaultDate:function(a){return this._restrictMinMax(a,this._determineDate(a,this._get(a,"defaultDate"),new Date))
},_determineDate:function(a,b,c){var e=function(h){var i=new Date;
i.setDate(i.getDate()+h);
return i
},f=function(h){try{return d.datepicker.parseDate(d.datepicker._get(a,"dateFormat"),h,d.datepicker._getFormatConfig(a))
}catch(i){}var g=(h.toLowerCase().match(/^c/)?d.datepicker._getDate(a):null)||new Date,k=g.getFullYear(),l=g.getMonth();
g=g.getDate();
for(var u=/([+-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g,j=u.exec(h);
j;
){switch(j[2]||"d"){case"d":case"D":g+=parseInt(j[1],10);
break;
case"w":case"W":g+=parseInt(j[1],10)*7;
break;
case"m":case"M":l+=parseInt(j[1],10);
g=Math.min(g,d.datepicker._getDaysInMonth(k,l));
break;
case"y":case"Y":k+=parseInt(j[1],10);
g=Math.min(g,d.datepicker._getDaysInMonth(k,l));
break
}j=u.exec(h)
}return new Date(k,l,g)
};
if(b=(b=b==null?c:typeof b=="string"?f(b):typeof b=="number"?isNaN(b)?c:e(b):b)&&b.toString()=="Invalid Date"?c:b){b.setHours(0);
b.setMinutes(0);
b.setSeconds(0);
b.setMilliseconds(0)
}return this._daylightSavingAdjust(b)
},_daylightSavingAdjust:function(a){if(!a){return null
}a.setHours(a.getHours()>12?a.getHours()+2:0);
return a
},_setDate:function(a,b,c){var e=!b,f=a.selectedMonth,h=a.selectedYear;
b=this._restrictMinMax(a,this._determineDate(a,b,new Date));
a.selectedDay=a.currentDay=b.getDate();
a.drawMonth=a.selectedMonth=a.currentMonth=b.getMonth();
a.drawYear=a.selectedYear=a.currentYear=b.getFullYear();
if((f!=a.selectedMonth||h!=a.selectedYear)&&!c){this._notifyChange(a)
}this._adjustInstDate(a);
if(a.input){a.input.val(e?"":this._formatDate(a))
}},_getDate:function(a){return !a.currentYear||a.input&&a.input.val()==""?null:this._daylightSavingAdjust(new Date(a.currentYear,a.currentMonth,a.currentDay))
},_generateHTML:function(a){var b=new Date;
b=this._daylightSavingAdjust(new Date(b.getFullYear(),b.getMonth(),b.getDate()));
var c=this._get(a,"isRTL"),e=this._get(a,"showButtonPanel"),f=this._get(a,"hideIfNoPrevNext"),h=this._get(a,"navigationAsDateFormat"),i=this._getNumberOfMonths(a),g=this._get(a,"showCurrentAtPos"),k=this._get(a,"stepMonths"),l=i[0]!=1||i[1]!=1,u=this._daylightSavingAdjust(!a.currentDay?new Date(9999,9,9):new Date(a.currentYear,a.currentMonth,a.currentDay)),j=this._getMinMaxDate(a,"min"),o=this._getMinMaxDate(a,"max");
g=a.drawMonth-g;
var m=a.drawYear;
if(g<0){g+=12;
m--
}if(o){var n=this._daylightSavingAdjust(new Date(o.getFullYear(),o.getMonth()-i[0]*i[1]+1,o.getDate()));
for(n=j&&n<j?j:n;
this._daylightSavingAdjust(new Date(m,g,1))>n;
){g--;
if(g<0){g=11;
m--
}}}a.drawMonth=g;
a.drawYear=m;
n=this._get(a,"prevText");
n=!h?n:this.formatDate(n,this._daylightSavingAdjust(new Date(m,g-k,1)),this._getFormatConfig(a));
n=this._canAdjustMonth(a,-1,m,g)?'<a class="ui-datepicker-prev ui-corner-all" onclick="DP_jQuery_'+y+".datepicker._adjustDate('#"+a.id+"', -"+k+", 'M');\" title=\""+n+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"e":"w")+'">'+n+"</span></a>":f?"":'<a class="ui-datepicker-prev ui-corner-all ui-state-disabled" title="'+n+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"e":"w")+'">'+n+"</span></a>";
var r=this._get(a,"nextText");
r=!h?r:this.formatDate(r,this._daylightSavingAdjust(new Date(m,g+k,1)),this._getFormatConfig(a));
f=this._canAdjustMonth(a,+1,m,g)?'<a class="ui-datepicker-next ui-corner-all" onclick="DP_jQuery_'+y+".datepicker._adjustDate('#"+a.id+"', +"+k+", 'M');\" title=\""+r+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"w":"e")+'">'+r+"</span></a>":f?"":'<a class="ui-datepicker-next ui-corner-all ui-state-disabled" title="'+r+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"w":"e")+'">'+r+"</span></a>";
k=this._get(a,"currentText");
r=this._get(a,"gotoCurrent")&&a.currentDay?u:b;
k=!h?k:this.formatDate(k,r,this._getFormatConfig(a));
h=!a.inline?'<button type="button" class="ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all" onclick="DP_jQuery_'+y+'.datepicker._hideDatepicker();">'+this._get(a,"closeText")+"</button>":"";
e=e?'<div class="ui-datepicker-buttonpane ui-widget-content">'+(c?h:"")+(this._isInRange(a,r)?'<button type="button" class="ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all" onclick="DP_jQuery_'+y+".datepicker._gotoToday('#"+a.id+"');\">"+k+"</button>":"")+(c?"":h)+"</div>":"";
h=parseInt(this._get(a,"firstDay"),10);
h=isNaN(h)?0:h;
k=this._get(a,"showWeek");
r=this._get(a,"dayNames");
this._get(a,"dayNamesShort");
var s=this._get(a,"dayNamesMin"),z=this._get(a,"monthNames"),v=this._get(a,"monthNamesShort"),p=this._get(a,"beforeShowDay"),w=this._get(a,"showOtherMonths"),H=this._get(a,"selectOtherMonths");
this._get(a,"calculateWeek");
for(var M=this._getDefaultDate(a),I="",C=0;
C<i[0];
C++){for(var N="",D=0;
D<i[1];
D++){var J=this._daylightSavingAdjust(new Date(m,g,a.selectedDay)),t=" ui-corner-all",x="";
if(l){x+='<div class="ui-datepicker-group';
if(i[1]>1){switch(D){case 0:x+=" ui-datepicker-group-first";
t=" ui-corner-"+(c?"right":"left");
break;
case i[1]-1:x+=" ui-datepicker-group-last";
t=" ui-corner-"+(c?"left":"right");
break;
default:x+=" ui-datepicker-group-middle";
t="";
break
}}x+='">'
}x+='<div class="ui-datepicker-header ui-widget-header ui-helper-clearfix'+t+'">'+(/all|left/.test(t)&&C==0?c?f:n:"")+(/all|right/.test(t)&&C==0?c?n:f:"")+this._generateMonthYearHeader(a,g,m,j,o,C>0||D>0,z,v)+'</div><table class="ui-datepicker-calendar"><thead><tr>';
var A=k?'<th class="ui-datepicker-week-col">'+this._get(a,"weekHeader")+"</th>":"";
for(t=0;
t<7;
t++){var q=(t+h)%7;
A+="<th"+((t+h+6)%7>=5?' class="ui-datepicker-week-end"':"")+'><span title="'+r[q]+'">'+s[q]+"</span></th>"
}x+=A+"</tr></thead><tbody>";
A=this._getDaysInMonth(m,g);
if(m==a.selectedYear&&g==a.selectedMonth){a.selectedDay=Math.min(a.selectedDay,A)
}t=(this._getFirstDayOfMonth(m,g)-h+7)%7;
A=l?6:Math.ceil((t+A)/7);
q=this._daylightSavingAdjust(new Date(m,g,1-t));
for(var O=0;
O<A;
O++){x+="<tr>";
var P=!k?"":'<td class="ui-datepicker-week-col">'+this._get(a,"calculateWeek")(q)+"</td>";
for(t=0;
t<7;
t++){var F=p?p.apply(a.input?a.input[0]:null,[q]):[true,""],B=q.getMonth()!=g,K=B&&!H||!F[0]||j&&q<j||o&&q>o;
P+='<td class="'+((t+h+6)%7>=5?" ui-datepicker-week-end":"")+(B?" ui-datepicker-other-month":"")+(q.getTime()==J.getTime()&&g==a.selectedMonth&&a._keyEvent||M.getTime()==q.getTime()&&M.getTime()==J.getTime()?" "+this._dayOverClass:"")+(K?" "+this._unselectableClass+" ui-state-disabled":"")+(B&&!w?"":" "+F[1]+(q.getTime()==u.getTime()?" "+this._currentClass:"")+(q.getTime()==b.getTime()?" ui-datepicker-today":""))+'"'+((!B||w)&&F[2]?' title="'+F[2]+'"':"")+(K?"":' onclick="DP_jQuery_'+y+".datepicker._selectDay('#"+a.id+"',"+q.getMonth()+","+q.getFullYear()+', this);return false;"')+">"+(B&&!w?"&#xa0;":K?'<span class="ui-state-default">'+q.getDate()+"</span>":'<a class="ui-state-default'+(q.getTime()==b.getTime()?" ui-state-highlight":"")+(q.getTime()==J.getTime()?" ui-state-active":"")+(B?" ui-priority-secondary":"")+'" href="#">'+q.getDate()+"</a>")+"</td>";
q.setDate(q.getDate()+1);
q=this._daylightSavingAdjust(q)
}x+=P+"</tr>"
}g++;
if(g>11){g=0;
m++
}x+="</tbody></table>"+(l?"</div>"+(i[0]>0&&D==i[1]-1?'<div class="ui-datepicker-row-break"></div>':""):"");
N+=x
}I+=N
}I+=e+(d.browser.msie&&parseInt(d.browser.version,10)<7&&!a.inline?'<iframe src="javascript:false;" class="ui-datepicker-cover" frameborder="0"></iframe>':"");
a._keyEvent=false;
return I
},_generateMonthYearHeader:function(a,b,c,e,f,h,i,g){var k=this._get(a,"changeMonth"),l=this._get(a,"changeYear"),u=this._get(a,"showMonthAfterYear"),j='<div class="ui-datepicker-title">',o="";
if(h||!k){o+='<span class="ui-datepicker-month">'+i[b]+"</span>"
}else{i=e&&e.getFullYear()==c;
var m=f&&f.getFullYear()==c;
o+='<select class="ui-datepicker-month" onchange="DP_jQuery_'+y+".datepicker._selectMonthYear('#"+a.id+"', this, 'M');\" onclick=\"DP_jQuery_"+y+".datepicker._clickMonthYear('#"+a.id+"');\">";
for(var n=0;
n<12;
n++){if((!i||n>=e.getMonth())&&(!m||n<=f.getMonth())){o+='<option value="'+n+'"'+(n==b?' selected="selected"':"")+">"+g[n]+"</option>"
}}o+="</select>"
}u||(j+=o+(h||!(k&&l)?"&#xa0;":""));
if(h||!l){j+='<span class="ui-datepicker-year">'+c+"</span>"
}else{g=this._get(a,"yearRange").split(":");
var r=(new Date).getFullYear();
i=function(s){s=s.match(/c[+-].*/)?c+parseInt(s.substring(1),10):s.match(/[+-].*/)?r+parseInt(s,10):parseInt(s,10);
return isNaN(s)?r:s
};
b=i(g[0]);
g=Math.max(b,i(g[1]||""));
b=e?Math.max(b,e.getFullYear()):b;
g=f?Math.min(g,f.getFullYear()):g;
for(j+='<select class="ui-datepicker-year" onchange="DP_jQuery_'+y+".datepicker._selectMonthYear('#"+a.id+"', this, 'Y');\" onclick=\"DP_jQuery_"+y+".datepicker._clickMonthYear('#"+a.id+"');\">";
b<=g;
b++){j+='<option value="'+b+'"'+(b==c?' selected="selected"':"")+">"+b+"</option>"
}j+="</select>"
}j+=this._get(a,"yearSuffix");
if(u){j+=(h||!(k&&l)?"&#xa0;":"")+o
}j+="</div>";
return j
},_adjustInstDate:function(a,b,c){var e=a.drawYear+(c=="Y"?b:0),f=a.drawMonth+(c=="M"?b:0);
b=Math.min(a.selectedDay,this._getDaysInMonth(e,f))+(c=="D"?b:0);
e=this._restrictMinMax(a,this._daylightSavingAdjust(new Date(e,f,b)));
a.selectedDay=e.getDate();
a.drawMonth=a.selectedMonth=e.getMonth();
a.drawYear=a.selectedYear=e.getFullYear();
if(c=="M"||c=="Y"){this._notifyChange(a)
}},_restrictMinMax:function(a,b){var c=this._getMinMaxDate(a,"min");
a=this._getMinMaxDate(a,"max");
b=c&&b<c?c:b;
return b=a&&b>a?a:b
},_notifyChange:function(a){var b=this._get(a,"onChangeMonthYear");
if(b){b.apply(a.input?a.input[0]:null,[a.selectedYear,a.selectedMonth+1,a])
}},_getNumberOfMonths:function(a){a=this._get(a,"numberOfMonths");
return a==null?[1,1]:typeof a=="number"?[1,a]:a
},_getMinMaxDate:function(a,b){return this._determineDate(a,this._get(a,b+"Date"),null)
},_getDaysInMonth:function(a,b){return 32-(new Date(a,b,32)).getDate()
},_getFirstDayOfMonth:function(a,b){return(new Date(a,b,1)).getDay()
},_canAdjustMonth:function(a,b,c,e){var f=this._getNumberOfMonths(a);
c=this._daylightSavingAdjust(new Date(c,e+(b<0?b:f[0]*f[1]),1));
b<0&&c.setDate(this._getDaysInMonth(c.getFullYear(),c.getMonth()));
return this._isInRange(a,c)
},_isInRange:function(a,b){var c=this._getMinMaxDate(a,"min");
a=this._getMinMaxDate(a,"max");
return(!c||b.getTime()>=c.getTime())&&(!a||b.getTime()<=a.getTime())
},_getFormatConfig:function(a){var b=this._get(a,"shortYearCutoff");
b=typeof b!="string"?b:(new Date).getFullYear()%100+parseInt(b,10);
return{shortYearCutoff:b,dayNamesShort:this._get(a,"dayNamesShort"),dayNames:this._get(a,"dayNames"),monthNamesShort:this._get(a,"monthNamesShort"),monthNames:this._get(a,"monthNames")}
},_formatDate:function(a,b,c,e){if(!b){a.currentDay=a.selectedDay;
a.currentMonth=a.selectedMonth;
a.currentYear=a.selectedYear
}b=b?typeof b=="object"?b:this._daylightSavingAdjust(new Date(e,c,b)):this._daylightSavingAdjust(new Date(a.currentYear,a.currentMonth,a.currentDay));
return this.formatDate(this._get(a,"dateFormat"),b,this._getFormatConfig(a))
}});
d.fn.datepicker=function(a){if(!d.datepicker.initialized){d(document).mousedown(d.datepicker._checkExternalClick).find("body").append(d.datepicker.dpDiv);
d.datepicker.initialized=true
}var b=Array.prototype.slice.call(arguments,1);
if(typeof a=="string"&&(a=="isDisabled"||a=="getDate"||a=="widget")){return d.datepicker["_"+a+"Datepicker"].apply(d.datepicker,[this[0]].concat(b))
}if(a=="option"&&arguments.length==2&&typeof arguments[1]=="string"){return d.datepicker["_"+a+"Datepicker"].apply(d.datepicker,[this[0]].concat(b))
}return this.each(function(){typeof a=="string"?d.datepicker["_"+a+"Datepicker"].apply(d.datepicker,[this].concat(b)):d.datepicker._attachDatepicker(this,a)
})
};
d.datepicker=new L;
d.datepicker.initialized=false;
d.datepicker.uuid=(new Date).getTime();
d.datepicker.version="1.8.5";
window["DP_jQuery_"+y]=d
})(jQuery);﻿;
jQuery(function(a){a.datepicker.regional.af={closeText:"Selekteer",prevText:"Vorige",nextText:"Volgende",currentText:"Vandag",monthNames:["Januarie","Februarie","Maart","April","Mei","Junie","Julie","Augustus","September","Oktober","November","Desember"],monthNamesShort:["Jan","Feb","Mrt","Apr","Mei","Jun","Jul","Aug","Sep","Okt","Nov","Des"],dayNames:["Sondag","Maandag","Dinsdag","Woensdag","Donderdag","Vrydag","Saterdag"],dayNamesShort:["Son","Maa","Din","Woe","Don","Vry","Sat"],dayNamesMin:["So","Ma","Di","Wo","Do","Vr","Sa"],weekHeader:"Wk",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.af)
});
﻿;
jQuery(function(a){a.datepicker.regional.ar={closeText:"إغلاق",prevText:"&#x3c;السابق",nextText:"التالي&#x3e;",currentText:"اليوم",monthNames:["كانون الثاني","شباط","آذار","نيسان","آذار","حزيران","تموز","آب","أيلول","تشرين الأول","تشرين الثاني","كانون الأول"],monthNamesShort:["1","2","3","4","5","6","7","8","9","10","11","12"],dayNames:["السبت","الأحد","الاثنين","الثلاثاء","الأربعاء","الخميس","الجمعة"],dayNamesShort:["سبت","أحد","اثنين","ثلاثاء","أربعاء","خميس","جمعة"],dayNamesMin:["سبت","أحد","اثنين","ثلاثاء","أربعاء","خميس","جمعة"],weekHeader:"أسبوع",dateFormat:"dd/mm/yy",firstDay:0,isRTL:true,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.ar)
});
﻿;
jQuery(function(a){a.datepicker.regional.az={closeText:"Bağla",prevText:"&#x3c;Geri",nextText:"İrəli&#x3e;",currentText:"Bugün",monthNames:["Yanvar","Fevral","Mart","Aprel","May","İyun","İyul","Avqust","Sentyabr","Oktyabr","Noyabr","Dekabr"],monthNamesShort:["Yan","Fev","Mar","Apr","May","İyun","İyul","Avq","Sen","Okt","Noy","Dek"],dayNames:["Bazar","Bazar ertəsi","Çərşənbə axşamı","Çərşənbə","Cümə axşamı","Cümə","Şənbə"],dayNamesShort:["B","Be","Ça","Ç","Ca","C","Ş"],dayNamesMin:["B","B","Ç","С","Ç","C","Ş"],weekHeader:"Hf",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.az)
});
﻿;
jQuery(function(a){a.datepicker.regional.bg={closeText:"затвори",prevText:"&#x3c;назад",nextText:"напред&#x3e;",nextBigText:"&#x3e;&#x3e;",currentText:"днес",monthNames:["Януари","Февруари","Март","Април","Май","Юни","Юли","Август","Септември","Октомври","Ноември","Декември"],monthNamesShort:["Яну","Фев","Мар","Апр","Май","Юни","Юли","Авг","Сеп","Окт","Нов","Дек"],dayNames:["Неделя","Понеделник","Вторник","Сряда","Четвъртък","Петък","Събота"],dayNamesShort:["Нед","Пон","Вто","Сря","Чет","Пет","Съб"],dayNamesMin:["Не","По","Вт","Ср","Че","Пе","Съ"],weekHeader:"Wk",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.bg)
});
﻿;
jQuery(function(a){a.datepicker.regional.bs={closeText:"Zatvori",prevText:"&#x3c;",nextText:"&#x3e;",currentText:"Danas",monthNames:["Januar","Februar","Mart","April","Maj","Juni","Juli","August","Septembar","Oktobar","Novembar","Decembar"],monthNamesShort:["Jan","Feb","Mar","Apr","Maj","Jun","Jul","Aug","Sep","Okt","Nov","Dec"],dayNames:["Nedelja","Ponedeljak","Utorak","Srijeda","Četvrtak","Petak","Subota"],dayNamesShort:["Ned","Pon","Uto","Sri","Čet","Pet","Sub"],dayNamesMin:["Ne","Po","Ut","Sr","Če","Pe","Su"],weekHeader:"Wk",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.bs)
});
jQuery(function(a){a.datepicker.regional.ca={closeText:"Tancar",prevText:"&#x3c;Ant",nextText:"Seg&#x3e;",currentText:"Avui",monthNames:["Gener","Febrer","Mar&ccedil;","Abril","Maig","Juny","Juliol","Agost","Setembre","Octubre","Novembre","Desembre"],monthNamesShort:["Gen","Feb","Mar","Abr","Mai","Jun","Jul","Ago","Set","Oct","Nov","Des"],dayNames:["Diumenge","Dilluns","Dimarts","Dimecres","Dijous","Divendres","Dissabte"],dayNamesShort:["Dug","Dln","Dmt","Dmc","Djs","Dvn","Dsb"],dayNamesMin:["Dg","Dl","Dt","Dc","Dj","Dv","Ds"],weekHeader:"Sm",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.ca)
});
﻿;
jQuery(function(a){a.datepicker.regional.cs={closeText:"Zavřít",prevText:"&#x3c;Dříve",nextText:"Později&#x3e;",currentText:"Nyní",monthNames:["leden","únor","březen","duben","květen","červen","červenec","srpen","září","říjen","listopad","prosinec"],monthNamesShort:["led","úno","bře","dub","kvě","čer","čvc","srp","zář","říj","lis","pro"],dayNames:["neděle","pondělí","úterý","středa","čtvrtek","pátek","sobota"],dayNamesShort:["ne","po","út","st","čt","pá","so"],dayNamesMin:["ne","po","út","st","čt","pá","so"],weekHeader:"Týd",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.cs)
});
﻿;
jQuery(function(a){a.datepicker.regional.da={closeText:"Luk",prevText:"&#x3c;Forrige",nextText:"Næste&#x3e;",currentText:"Idag",monthNames:["Januar","Februar","Marts","April","Maj","Juni","Juli","August","September","Oktober","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","Maj","Jun","Jul","Aug","Sep","Okt","Nov","Dec"],dayNames:["Søndag","Mandag","Tirsdag","Onsdag","Torsdag","Fredag","Lørdag"],dayNamesShort:["Søn","Man","Tir","Ons","Tor","Fre","Lør"],dayNamesMin:["Sø","Ma","Ti","On","To","Fr","Lø"],weekHeader:"Uge",dateFormat:"dd-mm-yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.da)
});
﻿;
jQuery(function(a){a.datepicker.regional.de={closeText:"schließen",prevText:"&#x3c;zurück",nextText:"Vor&#x3e;",currentText:"heute",monthNames:["Januar","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember"],monthNamesShort:["Jan","Feb","Mär","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Dez"],dayNames:["Sonntag","Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag"],dayNamesShort:["So","Mo","Di","Mi","Do","Fr","Sa"],dayNamesMin:["So","Mo","Di","Mi","Do","Fr","Sa"],weekHeader:"Wo",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.de)
});
﻿;
jQuery(function(a){a.datepicker.regional.el={closeText:"Κλείσιμο",prevText:"Προηγούμενος",nextText:"Επόμενος",currentText:"Τρέχων Μήνας",monthNames:["Ιανουάριος","Φεβρουάριος","Μάρτιος","Απρίλιος","Μάιος","Ιούνιος","Ιούλιος","Αύγουστος","Σεπτέμβριος","Οκτώβριος","Νοέμβριος","Δεκέμβριος"],monthNamesShort:["Ιαν","Φεβ","Μαρ","Απρ","Μαι","Ιουν","Ιουλ","Αυγ","Σεπ","Οκτ","Νοε","Δεκ"],dayNames:["Κυριακή","Δευτέρα","Τρίτη","Τετάρτη","Πέμπτη","Παρασκευή","Σάββατο"],dayNamesShort:["Κυρ","Δευ","Τρι","Τετ","Πεμ","Παρ","Σαβ"],dayNamesMin:["Κυ","Δε","Τρ","Τε","Πε","Πα","Σα"],weekHeader:"Εβδ",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.el)
});
﻿;
jQuery(function(a){a.datepicker.regional["en-GB"]={closeText:"Done",prevText:"Prev",nextText:"Next",currentText:"Today",monthNames:["January","February","March","April","May","June","July","August","September","October","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],dayNames:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],dayNamesShort:["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],dayNamesMin:["Su","Mo","Tu","We","Th","Fr","Sa"],weekHeader:"Wk",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional["en-GB"])
});
﻿;
jQuery(function(a){a.datepicker.regional.eo={closeText:"Fermi",prevText:"&lt;Anta",nextText:"Sekv&gt;",currentText:"Nuna",monthNames:["Januaro","Februaro","Marto","Aprilo","Majo","Junio","Julio","Aŭgusto","Septembro","Oktobro","Novembro","Decembro"],monthNamesShort:["Jan","Feb","Mar","Apr","Maj","Jun","Jul","Aŭg","Sep","Okt","Nov","Dec"],dayNames:["Dimanĉo","Lundo","Mardo","Merkredo","Ĵaŭdo","Vendredo","Sabato"],dayNamesShort:["Dim","Lun","Mar","Mer","Ĵaŭ","Ven","Sab"],dayNamesMin:["Di","Lu","Ma","Me","Ĵa","Ve","Sa"],weekHeader:"Sb",dateFormat:"dd/mm/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.eo)
});
jQuery(function(a){a.datepicker.regional.es={closeText:"Cerrar",prevText:"&#x3c;Ant",nextText:"Sig&#x3e;",currentText:"Hoy",monthNames:["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"],monthNamesShort:["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"],dayNames:["Domingo","Lunes","Martes","Mi&eacute;rcoles","Jueves","Viernes","S&aacute;bado"],dayNamesShort:["Dom","Lun","Mar","Mi&eacute;","Juv","Vie","S&aacute;b"],dayNamesMin:["Do","Lu","Ma","Mi","Ju","Vi","S&aacute;"],weekHeader:"Sm",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.es)
});
﻿;
jQuery(function(a){a.datepicker.regional.et={closeText:"Sulge",prevText:"Eelnev",nextText:"Järgnev",currentText:"Täna",monthNames:["Jaanuar","Veebruar","Märts","Aprill","Mai","Juuni","Juuli","August","September","Oktoober","November","Detsember"],monthNamesShort:["Jaan","Veebr","Märts","Apr","Mai","Juuni","Juuli","Aug","Sept","Okt","Nov","Dets"],dayNames:["Pühapäev","Esmaspäev","Teisipäev","Kolmapäev","Neljapäev","Reede","Laupäev"],dayNamesShort:["Pühap","Esmasp","Teisip","Kolmap","Neljap","Reede","Laup"],dayNamesMin:["P","E","T","K","N","R","L"],weekHeader:"Sm",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.et)
});
﻿;
jQuery(function(a){a.datepicker.regional.eu={closeText:"Egina",prevText:"&#x3c;Aur",nextText:"Hur&#x3e;",currentText:"Gaur",monthNames:["Urtarrila","Otsaila","Martxoa","Apirila","Maiatza","Ekaina","Uztaila","Abuztua","Iraila","Urria","Azaroa","Abendua"],monthNamesShort:["Urt","Ots","Mar","Api","Mai","Eka","Uzt","Abu","Ira","Urr","Aza","Abe"],dayNames:["Igandea","Astelehena","Asteartea","Asteazkena","Osteguna","Ostirala","Larunbata"],dayNamesShort:["Iga","Ast","Ast","Ast","Ost","Ost","Lar"],dayNamesMin:["Ig","As","As","As","Os","Os","La"],weekHeader:"Wk",dateFormat:"yy/mm/dd",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.eu)
});
﻿;
jQuery(function(a){a.datepicker.regional.fa={closeText:"بستن",prevText:"&#x3c;قبلي",nextText:"بعدي&#x3e;",currentText:"امروز",monthNames:["فروردين","ارديبهشت","خرداد","تير","مرداد","شهريور","مهر","آبان","آذر","دي","بهمن","اسفند"],monthNamesShort:["1","2","3","4","5","6","7","8","9","10","11","12"],dayNames:["يکشنبه","دوشنبه","سه‌شنبه","چهارشنبه","پنجشنبه","جمعه","شنبه"],dayNamesShort:["ي","د","س","چ","پ","ج","ش"],dayNamesMin:["ي","د","س","چ","پ","ج","ش"],weekHeader:"هف",dateFormat:"yy/mm/dd",firstDay:6,isRTL:true,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.fa)
});
jQuery(function(a){a.datepicker.regional.fi={closeText:"Sulje",prevText:"&laquo;Edellinen",nextText:"Seuraava&raquo;",currentText:"T&auml;n&auml;&auml;n",monthNames:["Tammikuu","Helmikuu","Maaliskuu","Huhtikuu","Toukokuu","Kes&auml;kuu","Hein&auml;kuu","Elokuu","Syyskuu","Lokakuu","Marraskuu","Joulukuu"],monthNamesShort:["Tammi","Helmi","Maalis","Huhti","Touko","Kes&auml;","Hein&auml;","Elo","Syys","Loka","Marras","Joulu"],dayNamesShort:["Su","Ma","Ti","Ke","To","Pe","Su"],dayNames:["Sunnuntai","Maanantai","Tiistai","Keskiviikko","Torstai","Perjantai","Lauantai"],dayNamesMin:["Su","Ma","Ti","Ke","To","Pe","La"],weekHeader:"Vk",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.fi)
});
﻿;
jQuery(function(a){a.datepicker.regional.fo={closeText:"Lat aftur",prevText:"&#x3c;Fyrra",nextText:"Næsta&#x3e;",currentText:"Í dag",monthNames:["Januar","Februar","Mars","Apríl","Mei","Juni","Juli","August","September","Oktober","November","Desember"],monthNamesShort:["Jan","Feb","Mar","Apr","Mei","Jun","Jul","Aug","Sep","Okt","Nov","Des"],dayNames:["Sunnudagur","Mánadagur","Týsdagur","Mikudagur","Hósdagur","Fríggjadagur","Leyardagur"],dayNamesShort:["Sun","Mán","Týs","Mik","Hós","Frí","Ley"],dayNamesMin:["Su","Má","Tý","Mi","Hó","Fr","Le"],weekHeader:"Vk",dateFormat:"dd-mm-yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.fo)
});
﻿;
jQuery(function(a){a.datepicker.regional["fr-CH"]={closeText:"Fermer",prevText:"&#x3c;Préc",nextText:"Suiv&#x3e;",currentText:"Courant",monthNames:["Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"],monthNamesShort:["Jan","Fév","Mar","Avr","Mai","Jun","Jul","Aoû","Sep","Oct","Nov","Déc"],dayNames:["Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"],dayNamesShort:["Dim","Lun","Mar","Mer","Jeu","Ven","Sam"],dayNamesMin:["Di","Lu","Ma","Me","Je","Ve","Sa"],weekHeader:"Sm",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional["fr-CH"])
});
﻿;
jQuery(function(a){a.datepicker.regional.fr={closeText:"Fermer",prevText:"&#x3c;Préc",nextText:"Suiv&#x3e;",currentText:"Courant",monthNames:["Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"],monthNamesShort:["Jan","Fév","Mar","Avr","Mai","Jun","Jul","Aoû","Sep","Oct","Nov","Déc"],dayNames:["Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"],dayNamesShort:["Dim","Lun","Mar","Mer","Jeu","Ven","Sam"],dayNamesMin:["Di","Lu","Ma","Me","Je","Ve","Sa"],weekHeader:"Sm",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.fr)
});
﻿;
jQuery(function(a){a.datepicker.regional.he={closeText:"סגור",prevText:"&#x3c;הקודם",nextText:"הבא&#x3e;",currentText:"היום",monthNames:["ינואר","פברואר","מרץ","אפריל","מאי","יוני","יולי","אוגוסט","ספטמבר","אוקטובר","נובמבר","דצמבר"],monthNamesShort:["1","2","3","4","5","6","7","8","9","10","11","12"],dayNames:["ראשון","שני","שלישי","רביעי","חמישי","שישי","שבת"],dayNamesShort:["א'","ב'","ג'","ד'","ה'","ו'","שבת"],dayNamesMin:["א'","ב'","ג'","ד'","ה'","ו'","שבת"],weekHeader:"Wk",dateFormat:"dd/mm/yy",firstDay:0,isRTL:true,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.he)
});
﻿;
jQuery(function(a){a.datepicker.regional.hr={closeText:"Zatvori",prevText:"&#x3c;",nextText:"&#x3e;",currentText:"Danas",monthNames:["Siječanj","Veljača","Ožujak","Travanj","Svibanj","Lipanj","Srpanj","Kolovoz","Rujan","Listopad","Studeni","Prosinac"],monthNamesShort:["Sij","Velj","Ožu","Tra","Svi","Lip","Srp","Kol","Ruj","Lis","Stu","Pro"],dayNames:["Nedjelja","Ponedjeljak","Utorak","Srijeda","Četvrtak","Petak","Subota"],dayNamesShort:["Ned","Pon","Uto","Sri","Čet","Pet","Sub"],dayNamesMin:["Ne","Po","Ut","Sr","Če","Pe","Su"],weekHeader:"Tje",dateFormat:"dd.mm.yy.",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.hr)
});
jQuery(function(a){a.datepicker.regional.hu={closeText:"bezárás",prevText:"&laquo;&nbsp;vissza",nextText:"előre&nbsp;&raquo;",currentText:"ma",monthNames:["Január","Február","Március","Április","Május","Június","Július","Augusztus","Szeptember","Október","November","December"],monthNamesShort:["Jan","Feb","Már","Ápr","Máj","Jún","Júl","Aug","Szep","Okt","Nov","Dec"],dayNames:["Vasárnap","Hétfö","Kedd","Szerda","Csütörtök","Péntek","Szombat"],dayNamesShort:["Vas","Hét","Ked","Sze","Csü","Pén","Szo"],dayNamesMin:["V","H","K","Sze","Cs","P","Szo"],weekHeader:"Hé",dateFormat:"yy-mm-dd",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.hu)
});
jQuery(function(a){a.datepicker.regional.hy={closeText:"Փակել",prevText:"&#x3c;Նախ.",nextText:"Հաջ.&#x3e;",currentText:"Այսօր",monthNames:["Հունվար","Փետրվար","Մարտ","Ապրիլ","Մայիս","Հունիս","Հուլիս","Օգոստոս","Սեպտեմբեր","Հոկտեմբեր","Նոյեմբեր","Դեկտեմբեր"],monthNamesShort:["Հունվ","Փետր","Մարտ","Ապր","Մայիս","Հունիս","Հուլ","Օգս","Սեպ","Հոկ","Նոյ","Դեկ"],dayNames:["կիրակի","եկուշաբթի","երեքշաբթի","չորեքշաբթի","հինգշաբթի","ուրբաթ","շաբաթ"],dayNamesShort:["կիր","երկ","երք","չրք","հնգ","ուրբ","շբթ"],dayNamesMin:["կիր","երկ","երք","չրք","հնգ","ուրբ","շբթ"],weekHeader:"ՇԲՏ",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.hy)
});
jQuery(function(a){a.datepicker.regional.id={closeText:"Tutup",prevText:"&#x3c;mundur",nextText:"maju&#x3e;",currentText:"hari ini",monthNames:["Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","Nopember","Desember"],monthNamesShort:["Jan","Feb","Mar","Apr","Mei","Jun","Jul","Agus","Sep","Okt","Nop","Des"],dayNames:["Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"],dayNamesShort:["Min","Sen","Sel","Rab","kam","Jum","Sab"],dayNamesMin:["Mg","Sn","Sl","Rb","Km","jm","Sb"],weekHeader:"Mg",dateFormat:"dd/mm/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.id)
});
jQuery(function(a){a.datepicker.regional.is={closeText:"Loka",prevText:"&#x3c; Fyrri",nextText:"N&aelig;sti &#x3e;",currentText:"&Iacute; dag",monthNames:["Jan&uacute;ar","Febr&uacute;ar","Mars","Apr&iacute;l","Ma&iacute","J&uacute;n&iacute;","J&uacute;l&iacute;","&Aacute;g&uacute;st","September","Okt&oacute;ber","N&oacute;vember","Desember"],monthNamesShort:["Jan","Feb","Mar","Apr","Ma&iacute;","J&uacute;n","J&uacute;l","&Aacute;g&uacute;","Sep","Okt","N&oacute;v","Des"],dayNames:["Sunnudagur","M&aacute;nudagur","&THORN;ri&eth;judagur","Mi&eth;vikudagur","Fimmtudagur","F&ouml;studagur","Laugardagur"],dayNamesShort:["Sun","M&aacute;n","&THORN;ri","Mi&eth;","Fim","F&ouml;s","Lau"],dayNamesMin:["Su","M&aacute;","&THORN;r","Mi","Fi","F&ouml;","La"],weekHeader:"Vika",dateFormat:"dd/mm/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.is)
});
jQuery(function(a){a.datepicker.regional.it={closeText:"Chiudi",prevText:"&#x3c;Prec",nextText:"Succ&#x3e;",currentText:"Oggi",monthNames:["Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"],monthNamesShort:["Gen","Feb","Mar","Apr","Mag","Giu","Lug","Ago","Set","Ott","Nov","Dic"],dayNames:["Domenica","Luned&#236","Marted&#236","Mercoled&#236","Gioved&#236","Venerd&#236","Sabato"],dayNamesShort:["Dom","Lun","Mar","Mer","Gio","Ven","Sab"],dayNamesMin:["Do","Lu","Ma","Me","Gi","Ve","Sa"],weekHeader:"Sm",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.it)
});
﻿;
jQuery(function(a){a.datepicker.regional.ja={closeText:"閉じる",prevText:"&#x3c;前",nextText:"次&#x3e;",currentText:"今日",monthNames:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],monthNamesShort:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],dayNames:["日曜日","月曜日","火曜日","水曜日","木曜日","金曜日","土曜日"],dayNamesShort:["日","月","火","水","木","金","土"],dayNamesMin:["日","月","火","水","木","金","土"],weekHeader:"週",dateFormat:"yy/mm/dd",firstDay:0,isRTL:false,showMonthAfterYear:true,yearSuffix:"年"};
a.datepicker.setDefaults(a.datepicker.regional.ja)
});
jQuery(function(a){a.datepicker.regional.ko={closeText:"닫기",prevText:"이전달",nextText:"다음달",currentText:"오늘",monthNames:["1월(JAN)","2월(FEB)","3월(MAR)","4월(APR)","5월(MAY)","6월(JUN)","7월(JUL)","8월(AUG)","9월(SEP)","10월(OCT)","11월(NOV)","12월(DEC)"],monthNamesShort:["1월(JAN)","2월(FEB)","3월(MAR)","4월(APR)","5월(MAY)","6월(JUN)","7월(JUL)","8월(AUG)","9월(SEP)","10월(OCT)","11월(NOV)","12월(DEC)"],dayNames:["일","월","화","수","목","금","토"],dayNamesShort:["일","월","화","수","목","금","토"],dayNamesMin:["일","월","화","수","목","금","토"],weekHeader:"Wk",dateFormat:"yy-mm-dd",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:"년"};
a.datepicker.setDefaults(a.datepicker.regional.ko)
});
jQuery(function(a){a.datepicker.regional.kz={closeText:"Жабу",prevText:"&#x3c;Алдыңғы",nextText:"Келесі&#x3e;",currentText:"Бүгін",monthNames:["Қаңтар","Ақпан","Наурыз","Сәуір","Мамыр","Маусым","Шілде","Тамыз","Қыркүйек","Қазан","Қараша","Желтоқсан"],monthNamesShort:["Қаң","Ақп","Нау","Сәу","Мам","Мау","Шіл","Там","Қыр","Қаз","Қар","Жел"],dayNames:["Жексенбі","Дүйсенбі","Сейсенбі","Сәрсенбі","Бейсенбі","Жұма","Сенбі"],dayNamesShort:["жкс","дсн","ссн","срс","бсн","жма","снб"],dayNamesMin:["Жк","Дс","Сс","Ср","Бс","Жм","Сн"],weekHeader:"Не",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.kz)
});
jQuery(function(a){a.datepicker.regional.lt={closeText:"Uždaryti",prevText:"&#x3c;Atgal",nextText:"Pirmyn&#x3e;",currentText:"Šiandien",monthNames:["Sausis","Vasaris","Kovas","Balandis","Gegužė","Birželis","Liepa","Rugpjūtis","Rugsėjis","Spalis","Lapkritis","Gruodis"],monthNamesShort:["Sau","Vas","Kov","Bal","Geg","Bir","Lie","Rugp","Rugs","Spa","Lap","Gru"],dayNames:["sekmadienis","pirmadienis","antradienis","trečiadienis","ketvirtadienis","penktadienis","šeštadienis"],dayNamesShort:["sek","pir","ant","tre","ket","pen","šeš"],dayNamesMin:["Se","Pr","An","Tr","Ke","Pe","Še"],weekHeader:"Wk",dateFormat:"yy-mm-dd",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.lt)
});
jQuery(function(a){a.datepicker.regional.lv={closeText:"Aizvērt",prevText:"Iepr",nextText:"Nāka",currentText:"Šodien",monthNames:["Janvāris","Februāris","Marts","Aprīlis","Maijs","Jūnijs","Jūlijs","Augusts","Septembris","Oktobris","Novembris","Decembris"],monthNamesShort:["Jan","Feb","Mar","Apr","Mai","Jūn","Jūl","Aug","Sep","Okt","Nov","Dec"],dayNames:["svētdiena","pirmdiena","otrdiena","trešdiena","ceturtdiena","piektdiena","sestdiena"],dayNamesShort:["svt","prm","otr","tre","ctr","pkt","sst"],dayNamesMin:["Sv","Pr","Ot","Tr","Ct","Pk","Ss"],weekHeader:"Nav",dateFormat:"dd-mm-yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.lv)
});
jQuery(function(a){a.datepicker.regional.ms={closeText:"Tutup",prevText:"&#x3c;Sebelum",nextText:"Selepas&#x3e;",currentText:"hari ini",monthNames:["Januari","Februari","Mac","April","Mei","Jun","Julai","Ogos","September","Oktober","November","Disember"],monthNamesShort:["Jan","Feb","Mac","Apr","Mei","Jun","Jul","Ogo","Sep","Okt","Nov","Dis"],dayNames:["Ahad","Isnin","Selasa","Rabu","Khamis","Jumaat","Sabtu"],dayNamesShort:["Aha","Isn","Sel","Rab","kha","Jum","Sab"],dayNamesMin:["Ah","Is","Se","Ra","Kh","Ju","Sa"],weekHeader:"Mg",dateFormat:"dd/mm/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.ms)
});
﻿;
jQuery(function(a){a.datepicker.regional.nl={closeText:"Sluiten",prevText:"←",nextText:"→",currentText:"Vandaag",monthNames:["januari","februari","maart","april","mei","juni","juli","augustus","september","oktober","november","december"],monthNamesShort:["jan","feb","maa","apr","mei","jun","jul","aug","sep","okt","nov","dec"],dayNames:["zondag","maandag","dinsdag","woensdag","donderdag","vrijdag","zaterdag"],dayNamesShort:["zon","maa","din","woe","don","vri","zat"],dayNamesMin:["zo","ma","di","wo","do","vr","za"],weekHeader:"Wk",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.nl)
});
jQuery(function(a){a.datepicker.regional.no={closeText:"Lukk",prevText:"&laquo;Forrige",nextText:"Neste&raquo;",currentText:"I dag",monthNames:["Januar","Februar","Mars","April","Mai","Juni","Juli","August","September","Oktober","November","Desember"],monthNamesShort:["Jan","Feb","Mar","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Des"],dayNamesShort:["Søn","Man","Tir","Ons","Tor","Fre","Lør"],dayNames:["Søndag","Mandag","Tirsdag","Onsdag","Torsdag","Fredag","Lørdag"],dayNamesMin:["Sø","Ma","Ti","On","To","Fr","Lø"],weekHeader:"Uke",dateFormat:"yy-mm-dd",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.no)
});
jQuery(function(a){a.datepicker.regional.pl={closeText:"Zamknij",prevText:"&#x3c;Poprzedni",nextText:"Następny&#x3e;",currentText:"Dziś",monthNames:["Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"],monthNamesShort:["Sty","Lu","Mar","Kw","Maj","Cze","Lip","Sie","Wrz","Pa","Lis","Gru"],dayNames:["Niedziela","Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota"],dayNamesShort:["Nie","Pn","Wt","Śr","Czw","Pt","So"],dayNamesMin:["N","Pn","Wt","Śr","Cz","Pt","So"],weekHeader:"Tydz",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.pl)
});
jQuery(function(a){a.datepicker.regional["pt-BR"]={closeText:"Fechar",prevText:"&#x3c;Anterior",nextText:"Pr&oacute;ximo&#x3e;",currentText:"Hoje",monthNames:["Janeiro","Fevereiro","Mar&ccedil;o","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"],monthNamesShort:["Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"],dayNames:["Domingo","Segunda-feira","Ter&ccedil;a-feira","Quarta-feira","Quinta-feira","Sexta-feira","S&aacute;bado"],dayNamesShort:["Dom","Seg","Ter","Qua","Qui","Sex","S&aacute;b"],dayNamesMin:["Dom","Seg","Ter","Qua","Qui","Sex","S&aacute;b"],weekHeader:"Sm",dateFormat:"dd/mm/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional["pt-BR"])
});
﻿;
jQuery(function(a){a.datepicker.regional.ro={closeText:"Închide",prevText:"&laquo; Luna precedentă",nextText:"Luna următoare &raquo;",currentText:"Azi",monthNames:["Ianuarie","Februarie","Martie","Aprilie","Mai","Iunie","Iulie","August","Septembrie","Octombrie","Noiembrie","Decembrie"],monthNamesShort:["Ian","Feb","Mar","Apr","Mai","Iun","Iul","Aug","Sep","Oct","Nov","Dec"],dayNames:["Duminică","Luni","Marţi","Miercuri","Joi","Vineri","Sâmbătă"],dayNamesShort:["Dum","Lun","Mar","Mie","Joi","Vin","Sâm"],dayNamesMin:["Du","Lu","Ma","Mi","Jo","Vi","Sâ"],weekHeader:"Săpt",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.ro)
});
jQuery(function(a){a.datepicker.regional.ru={closeText:"Закрыть",prevText:"&#x3c;Пред",nextText:"След&#x3e;",currentText:"Сегодня",monthNames:["Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"],monthNamesShort:["Янв","Фев","Мар","Апр","Май","Июн","Июл","Авг","Сен","Окт","Ноя","Дек"],dayNames:["воскресенье","понедельник","вторник","среда","четверг","пятница","суббота"],dayNamesShort:["вск","пнд","втр","срд","чтв","птн","сбт"],dayNamesMin:["Вс","Пн","Вт","Ср","Чт","Пт","Сб"],weekHeader:"Не",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.ru)
});
jQuery(function(a){a.datepicker.regional.sk={closeText:"Zavrieť",prevText:"&#x3c;Predchádzajúci",nextText:"Nasledujúci&#x3e;",currentText:"Dnes",monthNames:["Január","Február","Marec","Apríl","Máj","Jún","Júl","August","September","Október","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","Máj","Jún","Júl","Aug","Sep","Okt","Nov","Dec"],dayNames:["Nedel'a","Pondelok","Utorok","Streda","Štvrtok","Piatok","Sobota"],dayNamesShort:["Ned","Pon","Uto","Str","Štv","Pia","Sob"],dayNamesMin:["Ne","Po","Ut","St","Št","Pia","So"],weekHeader:"Ty",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.sk)
});
jQuery(function(a){a.datepicker.regional.sl={closeText:"Zapri",prevText:"&lt;Prej&#x161;nji",nextText:"Naslednji&gt;",currentText:"Trenutni",monthNames:["Januar","Februar","Marec","April","Maj","Junij","Julij","Avgust","September","Oktober","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","Maj","Jun","Jul","Avg","Sep","Okt","Nov","Dec"],dayNames:["Nedelja","Ponedeljek","Torek","Sreda","&#x10C;etrtek","Petek","Sobota"],dayNamesShort:["Ned","Pon","Tor","Sre","&#x10C;et","Pet","Sob"],dayNamesMin:["Ne","Po","To","Sr","&#x10C;e","Pe","So"],weekHeader:"Teden",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.sl)
});
﻿;
jQuery(function(a){a.datepicker.regional.sq={closeText:"mbylle",prevText:"&#x3c;mbrapa",nextText:"Përpara&#x3e;",currentText:"sot",monthNames:["Janar","Shkurt","Mars","Prill","Maj","Qershor","Korrik","Gusht","Shtator","Tetor","Nëntor","Dhjetor"],monthNamesShort:["Jan","Shk","Mar","Pri","Maj","Qer","Kor","Gus","Sht","Tet","Nën","Dhj"],dayNames:["E Diel","E Hënë","E Martë","E Mërkurë","E Enjte","E Premte","E Shtune"],dayNamesShort:["Di","Hë","Ma","Më","En","Pr","Sh"],dayNamesMin:["Di","Hë","Ma","Më","En","Pr","Sh"],weekHeader:"Ja",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.sq)
});
﻿;
jQuery(function(a){a.datepicker.regional["sr-SR"]={closeText:"Zatvori",prevText:"&#x3c;",nextText:"&#x3e;",currentText:"Danas",monthNames:["Januar","Februar","Mart","April","Maj","Jun","Jul","Avgust","Septembar","Oktobar","Novembar","Decembar"],monthNamesShort:["Jan","Feb","Mar","Apr","Maj","Jun","Jul","Avg","Sep","Okt","Nov","Dec"],dayNames:["Nedelja","Ponedeljak","Utorak","Sreda","Četvrtak","Petak","Subota"],dayNamesShort:["Ned","Pon","Uto","Sre","Čet","Pet","Sub"],dayNamesMin:["Ne","Po","Ut","Sr","Če","Pe","Su"],weekHeader:"Sed",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional["sr-SR"])
});
﻿;
jQuery(function(a){a.datepicker.regional.sr={closeText:"Затвори",prevText:"&#x3c;",nextText:"&#x3e;",currentText:"Данас",monthNames:["Јануар","Фебруар","Март","Април","Мај","Јун","Јул","Август","Септембар","Октобар","Новембар","Децембар"],monthNamesShort:["Јан","Феб","Мар","Апр","Мај","Јун","Јул","Авг","Сеп","Окт","Нов","Дец"],dayNames:["Недеља","Понедељак","Уторак","Среда","Четвртак","Петак","Субота"],dayNamesShort:["Нед","Пон","Уто","Сре","Чет","Пет","Суб"],dayNamesMin:["Не","По","Ут","Ср","Че","Пе","Су"],weekHeader:"Сед",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.sr)
});
﻿;
jQuery(function(a){a.datepicker.regional.sv={closeText:"Stäng",prevText:"&laquo;Förra",nextText:"Nästa&raquo;",currentText:"Idag",monthNames:["Januari","Februari","Mars","April","Maj","Juni","Juli","Augusti","September","Oktober","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","Maj","Jun","Jul","Aug","Sep","Okt","Nov","Dec"],dayNamesShort:["Sön","Mån","Tis","Ons","Tor","Fre","Lör"],dayNames:["Söndag","Måndag","Tisdag","Onsdag","Torsdag","Fredag","Lördag"],dayNamesMin:["Sö","Må","Ti","On","To","Fr","Lö"],weekHeader:"Ve",dateFormat:"yy-mm-dd",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.sv)
});
﻿;
jQuery(function(a){a.datepicker.regional.ta={closeText:"மூடு",prevText:"முன்னையது",nextText:"அடுத்தது",currentText:"இன்று",monthNames:["தை","மாசி","பங்குனி","சித்திரை","வைகாசி","ஆனி","ஆடி","ஆவணி","புரட்டாசி","ஐப்பசி","கார்த்திகை","மார்கழி"],monthNamesShort:["தை","மாசி","பங்","சித்","வைகா","ஆனி","ஆடி","ஆவ","புர","ஐப்","கார்","மார்"],dayNames:["ஞாயிற்றுக்கிழமை","திங்கட்கிழமை","செவ்வாய்க்கிழமை","புதன்கிழமை","வியாழக்கிழமை","வெள்ளிக்கிழமை","சனிக்கிழமை"],dayNamesShort:["ஞாயிறு","திங்கள்","செவ்வாய்","புதன்","வியாழன்","வெள்ளி","சனி"],dayNamesMin:["ஞா","தி","செ","பு","வி","வெ","ச"],weekHeader:"Не",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.ta)
});
﻿;
jQuery(function(a){a.datepicker.regional.th={closeText:"ปิด",prevText:"&laquo;&nbsp;ย้อน",nextText:"ถัดไป&nbsp;&raquo;",currentText:"วันนี้",monthNames:["มกราคม","กุมภาพันธ์","มีนาคม","เมษายน","พฤษภาคม","มิถุนายน","กรกฏาคม","สิงหาคม","กันยายน","ตุลาคม","พฤศจิกายน","ธันวาคม"],monthNamesShort:["ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."],dayNames:["อาทิตย์","จันทร์","อังคาร","พุธ","พฤหัสบดี","ศุกร์","เสาร์"],dayNamesShort:["อา.","จ.","อ.","พ.","พฤ.","ศ.","ส."],dayNamesMin:["อา.","จ.","อ.","พ.","พฤ.","ศ.","ส."],weekHeader:"Wk",dateFormat:"dd/mm/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.th)
});
jQuery(function(a){a.datepicker.regional.tr={closeText:"kapat",prevText:"&#x3c;geri",nextText:"ileri&#x3e",currentText:"bugün",monthNames:["Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"],monthNamesShort:["Oca","Şub","Mar","Nis","May","Haz","Tem","Ağu","Eyl","Eki","Kas","Ara"],dayNames:["Pazar","Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi"],dayNamesShort:["Pz","Pt","Sa","Ça","Pe","Cu","Ct"],dayNamesMin:["Pz","Pt","Sa","Ça","Pe","Cu","Ct"],weekHeader:"Hf",dateFormat:"dd.mm.yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.tr)
});
jQuery(function(a){a.datepicker.regional.uk={closeText:"Закрити",prevText:"&#x3c;",nextText:"&#x3e;",currentText:"Сьогодні",monthNames:["Січень","Лютий","Березень","Квітень","Травень","Червень","Липень","Серпень","Вересень","Жовтень","Листопад","Грудень"],monthNamesShort:["Січ","Лют","Бер","Кві","Тра","Чер","Лип","Сер","Вер","Жов","Лис","Гру"],dayNames:["неділя","понеділок","вівторок","середа","четвер","п’ятниця","субота"],dayNamesShort:["нед","пнд","вів","срд","чтв","птн","сбт"],dayNamesMin:["Нд","Пн","Вт","Ср","Чт","Пт","Сб"],weekHeader:"Не",dateFormat:"dd/mm/yy",firstDay:1,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.uk)
});
﻿;
jQuery(function(a){a.datepicker.regional.vi={closeText:"Đóng",prevText:"&#x3c;Trước",nextText:"Tiếp&#x3e;",currentText:"Hôm nay",monthNames:["Tháng Một","Tháng Hai","Tháng Ba","Tháng Tư","Tháng Năm","Tháng Sáu","Tháng Bảy","Tháng Tám","Tháng Chín","Tháng Mười","Tháng Mười Một","Tháng Mười Hai"],monthNamesShort:["Tháng 1","Tháng 2","Tháng 3","Tháng 4","Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12"],dayNames:["Chủ Nhật","Thứ Hai","Thứ Ba","Thứ Tư","Thứ Năm","Thứ Sáu","Thứ Bảy"],dayNamesShort:["CN","T2","T3","T4","T5","T6","T7"],dayNamesMin:["CN","T2","T3","T4","T5","T6","T7"],weekHeader:"Tu",dateFormat:"dd/mm/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
a.datepicker.setDefaults(a.datepicker.regional.vi)
});
jQuery(function(a){a.datepicker.regional["zh-CN"]={closeText:"关闭",prevText:"&#x3c;上月",nextText:"下月&#x3e;",currentText:"今天",monthNames:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],monthNamesShort:["一","二","三","四","五","六","七","八","九","十","十一","十二"],dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],dayNamesShort:["周日","周一","周二","周三","周四","周五","周六"],dayNamesMin:["日","一","二","三","四","五","六"],weekHeader:"周",dateFormat:"yy-mm-dd",firstDay:1,isRTL:false,showMonthAfterYear:true,yearSuffix:"年"};
a.datepicker.setDefaults(a.datepicker.regional["zh-CN"])
});
jQuery(function(a){a.datepicker.regional["zh-HK"]={closeText:"關閉",prevText:"&#x3c;上月",nextText:"下月&#x3e;",currentText:"今天",monthNames:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],monthNamesShort:["一","二","三","四","五","六","七","八","九","十","十一","十二"],dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],dayNamesShort:["周日","周一","周二","周三","周四","周五","周六"],dayNamesMin:["日","一","二","三","四","五","六"],weekHeader:"周",dateFormat:"dd-mm-yy",firstDay:0,isRTL:false,showMonthAfterYear:true,yearSuffix:"年"};
a.datepicker.setDefaults(a.datepicker.regional["zh-HK"])
});
﻿;
jQuery(function(a){a.datepicker.regional["zh-TW"]={closeText:"關閉",prevText:"&#x3c;上月",nextText:"下月&#x3e;",currentText:"今天",monthNames:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],monthNamesShort:["一","二","三","四","五","六","七","八","九","十","十一","十二"],dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],dayNamesShort:["周日","周一","周二","周三","周四","周五","周六"],dayNamesMin:["日","一","二","三","四","五","六"],weekHeader:"周",dateFormat:"yy/mm/dd",firstDay:1,isRTL:false,showMonthAfterYear:true,yearSuffix:"年"};
a.datepicker.setDefaults(a.datepicker.regional["zh-TW"])
});(function(a){a.fn.extend({autocomplete:function(c,b){urlOrData="NOURL";
var d=typeof urlOrData=="string";
b=a.extend({},a.Autocompleter.defaults,{url:d?urlOrData:null,data:d?null:urlOrData,searchFunction:c,delay:d?a.Autocompleter.defaults.delay:10,max:b&&!b.scroll?10:150},b);
b.highlight=b.highlight||function(e){return e
};
b.formatMatch=b.formatMatch||b.formatItem;
return this.each(function(){new a.Autocompleter(this,b)
})
},result:function(b){return this.bind("result",b)
},search:function(b){return this.trigger("search",[b])
},flushCache:function(){return this.trigger("flushCache")
},setOptions:function(b){return this.trigger("setOptions",[b])
},unautocomplete:function(){return this.trigger("unautocomplete")
},showResults:function(b){return this.trigger("showResults",[b])
}});
a.Autocompleter=function(l,g){var c={UP:38,DOWN:40,DEL:46,TAB:9,RETURN:13,ESC:27,COMMA:188,PAGEUP:33,PAGEDOWN:34,BACKSPACE:8};
var b=a(l).attr("autocomplete","off").addClass(g.inputClass);
var j;
var p="";
var m=a.Autocompleter.Cache(g);
var e=0;
var u;
var x={mouseDownOnSelect:false};
var r=a.Autocompleter.Select(g,l,d,x);
var w;
a.browser.opera&&a(l.form).bind("submit.autocomplete",function(){if(w){w=false;
return false
}});
b.bind((a.browser.opera?"keypress":"keydown")+".autocomplete",function(y){e=1;
u=y.keyCode;
switch(y.keyCode){case c.UP:y.preventDefault();
if(r.visible()){r.prev()
}else{t(0,true)
}break;
case c.DOWN:y.preventDefault();
if(r.visible()){r.next()
}else{t(0,true)
}break;
case c.PAGEUP:y.preventDefault();
if(r.visible()){r.pageUp()
}else{t(0,true)
}break;
case c.PAGEDOWN:y.preventDefault();
if(r.visible()){r.pageDown()
}else{t(0,true)
}break;
case g.multiple&&a.trim(g.multipleSeparator)==","&&c.COMMA:case c.TAB:case c.RETURN:if(d()){y.preventDefault();
w=true;
return false
}break;
case c.ESC:r.hide();
break;
default:clearTimeout(j);
j=setTimeout(t,g.delay);
break
}}).focus(function(){e++
}).blur(function(){e=0;
if(!x.mouseDownOnSelect){s()
}}).click(function(){if(e++>1&&!r.visible()){t(0,true)
}}).bind("search",function(){var y=(arguments.length>1)?arguments[1]:null;
function z(D,C){var A;
if(C&&C.length){for(var B=0;
B<C.length;
B++){if(C[B].result.toLowerCase()==D.toLowerCase()){A=C[B];
break
}}}if(typeof y=="function"){y(A)
}else{b.trigger("result",A&&[A.data,A.value])
}}a.each(h(b.val()),function(A,B){f(B,z,z)
})
}).bind("flushCache",function(){m.flush()
}).bind("setOptions",function(){a.extend(g,arguments[1]);
if("data" in arguments[1]){m.populate()
}}).bind("unautocomplete",function(){r.unbind();
b.unbind();
a(l.form).unbind(".autocomplete")
}).bind("showResults",function(){var y=arguments[1];
QCD.info(y);
k("aa",y)
});
function d(){var B=r.selected();
if(!B){return false
}var y=B.result;
p=y;
if(g.multiple){var E=h(b.val());
if(E.length>1){var A=g.multipleSeparator.length;
var D=a(l).selection().start;
var C,z=0;
a.each(E,function(F,G){z+=G.length;
if(D<=z){C=F;
return false
}z+=A
});
E[C]=y;
y=E.join(g.multipleSeparator)
}y+=g.multipleSeparator
}b.val(y);
v();
b.trigger("result",[B.data,B.value]);
return true
}function t(A,z){if(u==c.DEL){r.hide();
return
}var y=b.val();
if(!z&&y==p){return
}p=y;
y=i(y);
if(y.length>=g.minChars){b.addClass(g.loadingClass);
if(!g.matchCase){y=y.toLowerCase()
}f(y,k,v)
}else{n();
r.hide()
}}function h(y){if(!y){return[""]
}if(!g.multiple){return[a.trim(y)]
}return a.map(y.split(g.multipleSeparator),function(z){return a.trim(y).length?a.trim(z):null
})
}function i(y){if(!g.multiple){return y
}var A=h(y);
if(A.length==1){return A[0]
}var z=a(l).selection().start;
if(z==y.length){A=h(y)
}else{A=h(y.replace(y.substring(z),""))
}return A[A.length-1]
}function q(y,z){if(g.autoFill&&(i(b.val()).toLowerCase()==y.toLowerCase())&&u!=c.BACKSPACE){b.val(b.val()+z.substring(i(p).length));
a(l).selection(p.length,p.length+z.length)
}}function s(){clearTimeout(j);
j=setTimeout(v,200)
}function v(){var y=r.visible();
r.hide();
clearTimeout(j);
n();
if(g.mustMatch){b.search(function(z){if(!z){if(g.multiple){var A=h(b.val()).slice(0,-1);
b.val(A.join(g.multipleSeparator)+(A.length?g.multipleSeparator:""))
}else{b.val("");
b.trigger("result",null)
}}})
}}function k(z,y){if(y&&y.length&&e){n();
r.display(y,z);
q(z,y[0].value);
r.show()
}else{v()
}}function f(z,B,y){if(!g.matchCase){z=z.toLowerCase()
}var A=m.load(z);
g.searchFunction(i(z))
}function o(B){var y=[];
var A=B.split("\n");
for(var z=0;
z<A.length;
z++){var C=a.trim(A[z]);
if(C){C=C.split("|");
y[y.length]={data:C,value:C[0],result:g.formatResult&&g.formatResult(C,C[0])||C[0]}
}}return y
}function n(){b.removeClass(g.loadingClass)
}};
a.Autocompleter.defaults={inputClass:"ac_input",resultsClass:"ac_results",loadingClass:"ac_loading",minChars:1,delay:400,matchCase:false,matchSubset:true,matchContains:false,cacheLength:10,max:100,mustMatch:false,extraParams:{},selectFirst:true,formatItem:function(b){return b[0]
},formatMatch:null,autoFill:false,width:0,multiple:false,multipleSeparator:", ",highlight:function(c,b){return c.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)("+b.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1")+")(?![^<>]*>)(?![^&;]+;)","gi"),"<strong>$1</strong>")
},scroll:true,scrollHeight:180};
a.Autocompleter.Cache=function(c){var f={};
var d=0;
function h(l,k){if(!c.matchCase){l=l.toLowerCase()
}var j=l.indexOf(k);
if(c.matchContains=="word"){j=l.toLowerCase().search("\\b"+k.toLowerCase())
}if(j==-1){return false
}return j==0||c.matchContains
}function g(j,i){if(d>c.cacheLength){b()
}if(!f[j]){d++
}f[j]=i
}function e(){if(!c.data){return false
}var k={},j=0;
if(!c.url){c.cacheLength=1
}k[""]=[];
for(var m=0,l=c.data.length;
m<l;
m++){var p=c.data[m];
p=(typeof p=="string")?[p]:p;
var o=c.formatMatch(p,m+1,c.data.length);
if(o===false){continue
}var n=o.charAt(0).toLowerCase();
if(!k[n]){k[n]=[]
}var q={value:o,data:p,result:c.formatResult&&c.formatResult(p)||o};
k[n].push(q);
if(j++<c.max){k[""].push(q)
}}a.each(k,function(r,s){c.cacheLength++;
g(r,s)
})
}setTimeout(e,25);
function b(){f={};
d=0
}return{flush:b,add:g,populate:e,load:function(n){if(!c.cacheLength||!d){return null
}if(!c.url&&c.matchContains){var m=[];
for(var j in f){if(j.length>0){var o=f[j];
a.each(o,function(p,k){if(h(k.value,n)){m.push(k)
}})
}}return m
}else{if(f[n]){return f[n]
}else{if(c.matchSubset){for(var l=n.length-1;
l>=c.minChars;
l--){var o=f[n.substr(0,l)];
if(o){var m=[];
a.each(o,function(p,k){if(h(k.value,n)){m[m.length]=k
}});
return m
}}}}}return null
}}
};
a.Autocompleter.Select=function(e,j,l,p){var i={ACTIVE:"ac_over"};
var k,f=-1,r,m="",s=true,c,o;
function n(){if(!s){return
}c=a("<div/>").hide().addClass(e.resultsClass).css("position","absolute").appendTo(document.body);
o=a("<ul/>").appendTo(c).mouseover(function(t){if(q(t).nodeName&&q(t).nodeName.toUpperCase()=="LI"){f=a("li",o).removeClass(i.ACTIVE).index(q(t));
a(q(t)).addClass(i.ACTIVE)
}}).click(function(t){a(q(t)).addClass(i.ACTIVE);
l();
j.focus();
return false
}).mousedown(function(){p.mouseDownOnSelect=true
}).mouseup(function(){p.mouseDownOnSelect=false
});
if(e.width>0){c.css("width",e.width)
}s=false
}function q(u){var t=u.target;
while(t&&t.tagName!="LI"){t=t.parentNode
}if(!t){return[]
}return t
}function h(t){k.slice(f,f+1).removeClass(i.ACTIVE);
g(t);
var v=k.slice(f,f+1).addClass(i.ACTIVE);
if(e.scroll){var u=0;
k.slice(0,f).each(function(){u+=this.offsetHeight
});
if((u+v[0].offsetHeight-o.scrollTop())>o[0].clientHeight){o.scrollTop(u+v[0].offsetHeight-o.innerHeight())
}else{if(u<o.scrollTop()){o.scrollTop(u)
}}}}function g(t){f+=t;
if(f<0){f=k.size()-1
}else{if(f>=k.size()){f=0
}}}function b(t){return e.max&&e.max<t?e.max:t
}function d(){o.empty();
var u=b(r.length);
for(var v=0;
v<u;
v++){if(!r[v]){continue
}var w=e.formatItem(r[v].data,v+1,u,r[v].value,m);
if(w===false){continue
}var t=a("<li/>").html(e.highlight(w,m)).addClass(v%2==0?"ac_even":"ac_odd").appendTo(o)[0];
a.data(t,"ac_data",r[v])
}k=o.find("li");
if(e.selectFirst){k.slice(0,1).addClass(i.ACTIVE);
f=0
}if(a.fn.bgiframe){o.bgiframe()
}}return{display:function(u,t){n();
r=u;
m=t;
d()
},next:function(){h(1)
},prev:function(){h(-1)
},pageUp:function(){if(f!=0&&f-8<0){h(-f)
}else{h(-8)
}},pageDown:function(){if(f!=k.size()-1&&f+8>k.size()){h(k.size()-1-f)
}else{h(8)
}},hide:function(){c&&c.hide();
k&&k.removeClass(i.ACTIVE);
f=-1
},visible:function(){return c&&c.is(":visible")
},current:function(){return this.visible()&&(k.filter("."+i.ACTIVE)[0]||e.selectFirst&&k[0])
},show:function(){var v=a(j).offset();
c.css({width:typeof e.width=="string"||e.width>0?e.width:a(j).width(),top:v.top+j.offsetHeight,left:v.left}).show();
if(e.scroll){o.scrollTop(0);
o.css({maxHeight:e.scrollHeight,overflow:"auto"});
if(a.browser.msie&&typeof document.body.style.maxHeight==="undefined"){var t=0;
k.each(function(){t+=this.offsetHeight
});
var u=t>e.scrollHeight;
o.css("height",u?e.scrollHeight:t);
if(!u){k.width(o.width()-parseInt(k.css("padding-left"))-parseInt(k.css("padding-right")))
}}}},selected:function(){var t=k&&k.filter("."+i.ACTIVE).removeClass(i.ACTIVE);
return t&&t.length&&a.data(t[0],"ac_data")
},emptyList:function(){o&&o.empty()
},unbind:function(){c&&c.remove()
}}
};
a.fn.selection=function(i,b){if(i!==undefined){return this.each(function(){if(this.createTextRange){var j=this.createTextRange();
if(b===undefined||i==b){j.move("character",i);
j.select()
}else{j.collapse(true);
j.moveStart("character",i);
j.moveEnd("character",b);
j.select()
}}else{if(this.setSelectionRange){this.setSelectionRange(i,b)
}else{if(this.selectionStart){this.selectionStart=i;
this.selectionEnd=b
}}}})
}var g=this[0];
if(g.createTextRange){var c=document.selection.createRange(),h=g.value,f="<->",d=c.text.length;
c.text=f;
var e=g.value.indexOf(f);
g.value=h;
this.selection(e,e+d);
return{start:e,end:e+d}
}else{if(g.selectionStart!==undefined){return{start:g.selectionStart,end:g.selectionEnd}
}}}
})(jQuery);/*
 * jQuery blockUI plugin
 * Version 2.33 (29-MAR-2010)
 * @requires jQuery v1.2.3 or later
 *
 * Examples at: http://malsup.com/jquery/block/
 * Copyright (c) 2007-2008 M. Alsup
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Thanks to Amir-Hossein Sobhi for some excellent contributions!
 */
﻿;
(function(i){if(/1\.(0|1|2)\.(0|1|2)/.test(i.fn.jquery)||/^1.1/.test(i.fn.jquery)){alert("blockUI requires jQuery v1.2.3 or later!  You are using v"+i.fn.jquery);
return
}i.fn._fadeIn=i.fn.fadeIn;
var c=function(){};
var j=document.documentMode||0;
var e=i.browser.msie&&((i.browser.version<8&&!j)||j<8);
var f=i.browser.msie&&/MSIE 6.0/.test(navigator.userAgent)&&!j;
i.blockUI=function(p){d(window,p)
};
i.unblockUI=function(p){h(window,p)
};
i.growlUI=function(t,r,s,p){var q=i('<div class="growlUI"></div>');
if(t){q.append("<h1>"+t+"</h1>")
}if(r){q.append("<h2>"+r+"</h2>")
}if(s==undefined){s=3000
}i.blockUI({message:q,fadeIn:700,fadeOut:1000,centerY:false,timeout:s,showOverlay:false,onUnblock:p,css:i.blockUI.defaults.growlCSS})
};
i.fn.block=function(p){return this.unblock({fadeOut:0}).each(function(){if(i.css(this,"position")=="static"){this.style.position="relative"
}if(i.browser.msie){this.style.zoom=1
}d(this,p)
})
};
i.fn.unblock=function(p){return this.each(function(){h(this,p)
})
};
i.blockUI.version=2.33;
i.blockUI.defaults={message:"<h1>Please wait...</h1>",title:null,draggable:true,theme:false,css:{padding:0,margin:0,width:"30%",top:"40%",left:"35%",textAlign:"center",color:"#000",border:"3px solid #aaa",backgroundColor:"#fff",cursor:"wait"},themedCSS:{width:"30%",top:"40%",left:"35%"},overlayCSS:{backgroundColor:"#000",opacity:0.6,cursor:"wait"},growlCSS:{width:"350px",top:"10px",left:"",right:"10px",border:"none",padding:"5px",opacity:0.6,cursor:"default",color:"#fff",backgroundColor:"#000","-webkit-border-radius":"10px","-moz-border-radius":"10px","border-radius":"10px"},iframeSrc:/^https/i.test(window.location.href||"")?"javascript:false":"about:blank",forceIframe:false,baseZ:1000,centerX:true,centerY:true,allowBodyStretch:true,bindEvents:true,constrainTabKey:true,fadeIn:200,fadeOut:400,timeout:0,showOverlay:true,focusInput:true,applyPlatformOpacityRules:true,onBlock:null,onUnblock:null,quirksmodeOffsetHack:4};
var b=null;
var g=[];
function d(r,F){var A=(r==window);
var w=F&&F.message!==undefined?F.message:undefined;
F=i.extend({},i.blockUI.defaults,F||{});
F.overlayCSS=i.extend({},i.blockUI.defaults.overlayCSS,F.overlayCSS||{});
var C=i.extend({},i.blockUI.defaults.css,F.css||{});
var N=i.extend({},i.blockUI.defaults.themedCSS,F.themedCSS||{});
w=w===undefined?F.message:w;
if(A&&b){h(window,{fadeOut:0})
}if(w&&typeof w!="string"&&(w.parentNode||w.jquery)){var I=w.jquery?w[0]:w;
var P={};
i(r).data("blockUI.history",P);
P.el=I;
P.parent=I.parentNode;
P.display=I.style.display;
P.position=I.style.position;
if(P.parent){P.parent.removeChild(I)
}}var B=F.baseZ;
var M=(i.browser.msie||F.forceIframe)?i('<iframe class="blockUI" style="z-index:'+(B++)+';display:none;border:none;margin:0;padding:0;position:absolute;width:100%;height:100%;top:0;left:0" src="'+F.iframeSrc+'"></iframe>'):i('<div class="blockUI" style="display:none"></div>');
var L=i('<div class="blockUI blockOverlay" style="z-index:'+(B++)+';display:none;border:none;margin:0;padding:0;width:100%;height:100%;top:0;left:0"></div>');
var K,G;
if(F.theme&&A){G='<div class="blockUI blockMsg blockPage ui-dialog ui-widget ui-corner-all" style="z-index:'+B+';display:none;position:fixed"><div class="ui-widget-header ui-dialog-titlebar blockTitle">'+(F.title||"&nbsp;")+'</div><div class="ui-widget-content ui-dialog-content"></div></div>'
}else{if(F.theme){G='<div class="blockUI blockMsg blockElement ui-dialog ui-widget ui-corner-all" style="z-index:'+B+';display:none;position:absolute"><div class="ui-widget-header ui-dialog-titlebar blockTitle">'+(F.title||"&nbsp;")+'</div><div class="ui-widget-content ui-dialog-content"></div></div>'
}else{if(A){G='<div class="blockUI blockMsg blockPage" style="z-index:'+B+';display:none;position:fixed"></div>'
}else{G='<div class="blockUI blockMsg blockElement" style="z-index:'+B+';display:none;position:absolute"></div>'
}}}K=i(G);
if(w){if(F.theme){K.css(N);
K.addClass("ui-widget-content")
}else{K.css(C)
}}if(!F.applyPlatformOpacityRules||!(i.browser.mozilla&&/Linux/.test(navigator.platform))){L.css(F.overlayCSS)
}L.css("position",A?"fixed":"absolute");
if(i.browser.msie||F.forceIframe){M.css("opacity",0)
}var y=[M,L,K],O=A?i("body"):i(r);
i.each(y,function(){this.appendTo(O)
});
if(F.theme&&F.draggable&&i.fn.draggable){K.draggable({handle:".ui-dialog-titlebar",cancel:"li"})
}var v=e&&(!i.boxModel||i("object,embed",A?null:r).length>0);
if(f||v){if(A&&F.allowBodyStretch&&i.boxModel){i("html,body").css("height","100%")
}if((f||!i.boxModel)&&!A){var E=m(r,"borderTopWidth"),J=m(r,"borderLeftWidth");
var x=E?"(0 - "+E+")":0;
var D=J?"(0 - "+J+")":0
}i.each([M,L,K],function(t,S){var z=S[0].style;
z.position="absolute";
if(t<2){A?z.setExpression("height","Math.max(document.body.scrollHeight, document.body.offsetHeight) - (jQuery.boxModel?0:"+F.quirksmodeOffsetHack+') + "px"'):z.setExpression("height",'this.parentNode.offsetHeight + "px"');
A?z.setExpression("width",'jQuery.boxModel && document.documentElement.clientWidth || document.body.clientWidth + "px"'):z.setExpression("width",'this.parentNode.offsetWidth + "px"');
if(D){z.setExpression("left",D)
}if(x){z.setExpression("top",x)
}}else{if(F.centerY){if(A){z.setExpression("top",'(document.documentElement.clientHeight || document.body.clientHeight) / 2 - (this.offsetHeight / 2) + (blah = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop) + "px"')
}z.marginTop=0
}else{if(!F.centerY&&A){var Q=(F.css&&F.css.top)?parseInt(F.css.top):0;
var R="((document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop) + "+Q+') + "px"';
z.setExpression("top",R)
}}}})
}if(w){if(F.theme){K.find(".ui-widget-content").append(w)
}else{K.append(w)
}if(w.jquery||w.nodeType){i(w).show()
}}if((i.browser.msie||F.forceIframe)&&F.showOverlay){M.show()
}if(F.fadeIn){var H=F.onBlock?F.onBlock:c;
var q=(F.showOverlay&&!w)?H:c;
var p=w?H:c;
if(F.showOverlay){L._fadeIn(F.fadeIn,q)
}if(w){K._fadeIn(F.fadeIn,p)
}}else{if(F.showOverlay){L.show()
}if(w){K.show()
}if(F.onBlock){F.onBlock()
}}l(1,r,F);
if(A){b=K[0];
g=i(":input:enabled:visible",b);
if(F.focusInput){setTimeout(o,20)
}}else{a(K[0],F.centerX,F.centerY)
}if(F.timeout){var u=setTimeout(function(){A?i.unblockUI(F):i(r).unblock(F)
},F.timeout);
i(r).data("blockUI.timeout",u)
}}function h(s,t){var r=(s==window);
var q=i(s);
var u=q.data("blockUI.history");
var v=q.data("blockUI.timeout");
if(v){clearTimeout(v);
q.removeData("blockUI.timeout")
}t=i.extend({},i.blockUI.defaults,t||{});
l(0,s,t);
var p;
if(r){p=i("body").children().filter(".blockUI").add("body > .blockUI")
}else{p=i(".blockUI",s)
}if(r){b=g=null
}if(t.fadeOut){p.fadeOut(t.fadeOut);
setTimeout(function(){k(p,u,t,s)
},t.fadeOut)
}else{k(p,u,t,s)
}}function k(p,s,r,q){p.each(function(t,u){if(this.parentNode){this.parentNode.removeChild(this)
}});
if(s&&s.el){s.el.style.display=s.display;
s.el.style.position=s.position;
if(s.parent){s.parent.appendChild(s.el)
}i(q).removeData("blockUI.history")
}if(typeof r.onUnblock=="function"){r.onUnblock(q,r)
}}function l(p,t,u){var s=t==window,r=i(t);
if(!p&&(s&&!b||!s&&!r.data("blockUI.isBlocked"))){return
}if(!s){r.data("blockUI.isBlocked",p)
}if(!u.bindEvents||(p&&!u.showOverlay)){return
}var q="mousedown mouseup keydown keypress";
p?i(document).bind(q,u,n):i(document).unbind(q,n)
}function n(s){if(s.keyCode&&s.keyCode==9){if(b&&s.data.constrainTabKey){var r=g;
var q=!s.shiftKey&&s.target==r[r.length-1];
var p=s.shiftKey&&s.target==r[0];
if(q||p){setTimeout(function(){o(p)
},10);
return false
}}}if(i(s.target).parents("div.blockMsg").length>0){return true
}return i(s.target).parents().children().filter("div.blockUI").length==0
}function o(p){if(!g){return
}var q=g[p===true?g.length-1:0];
if(q){q.focus()
}}function a(w,q,A){var z=w.parentNode,v=w.style;
var r=((z.offsetWidth-w.offsetWidth)/2)-m(z,"borderLeftWidth");
var u=((z.offsetHeight-w.offsetHeight)/2)-m(z,"borderTopWidth");
if(q){v.left=r>0?(r+"px"):"0"
}if(A){v.top=u>0?(u+"px"):"0"
}}function m(q,r){return parseInt(i.css(q,r))||0
}})(jQuery);jQuery.cookie=function(b,j,m){if(typeof j!="undefined"){m=m||{};
if(j===null){j="";
m.expires=-1
}var e="";
if(m.expires&&(typeof m.expires=="number"||m.expires.toUTCString)){var f;
if(typeof m.expires=="number"){f=new Date();
f.setTime(f.getTime()+(m.expires*24*60*60*1000))
}else{f=m.expires
}e="; expires="+f.toUTCString()
}var l=m.path?"; path="+(m.path):"";
var g=m.domain?"; domain="+(m.domain):"";
var a=m.secure?"; secure":"";
document.cookie=[b,"=",encodeURIComponent(j),e,l,g,a].join("")
}else{var d=null;
if(document.cookie&&document.cookie!=""){var k=document.cookie.split(";");
for(var h=0;
h<k.length;
h++){var c=jQuery.trim(k[h]);
if(c.substring(0,b.length+1)==(b+"=")){d=decodeURIComponent(c.substring(b.length+1));
break
}}}return d
}};(function(b){b.jgrid=b.jgrid||{};
b.extend(b.jgrid,{htmlDecode:function(f){if(f=="&nbsp;"||f=="&#160;"||f.length==1&&f.charCodeAt(0)==160){return""
}return !f?f:String(f).replace(/&amp;/g,"&").replace(/&gt;/g,">").replace(/&lt;/g,"<").replace(/&quot;/g,'"')
},htmlEncode:function(f){return !f?f:String(f).replace(/&/g,"&amp;").replace(/>/g,"&gt;").replace(/</g,"&lt;").replace(/\"/g,"&quot;")
},format:function(f){var j=b.makeArray(arguments).slice(1);
if(f===undefined){f=""
}return f.replace(/\{(\d+)\}/g,function(i,c){return j[c]
})
},getCellIndex:function(f){f=b(f);
if(f.is("tr")){return -1
}f=(!f.is("td")&&!f.is("th")?f.closest("td,th"):f)[0];
if(b.browser.msie){return b.inArray(f,f.parentNode.cells)
}return f.cellIndex
},stripHtml:function(f){f+="";
var j=/<("[^"]*"|'[^']*'|[^'">])*>/gi;
if(f){return(f=f.replace(j,""))&&f!=="&nbsp;"&&f!=="&#160;"?f.replace(/\"/g,"'"):""
}else{return f
}},stringToDoc:function(f){var j;
if(typeof f!=="string"){return f
}try{j=(new DOMParser).parseFromString(f,"text/xml")
}catch(i){j=new ActiveXObject("Microsoft.XMLDOM");
j.async=false;
j.loadXML(f)
}return j&&j.documentElement&&j.documentElement.tagName!="parsererror"?j:null
},parse:function(f){f=f;
if(f.substr(0,9)=="while(1);"){f=f.substr(9)
}if(f.substr(0,2)=="/*"){f=f.substr(2,f.length-4)
}f||(f="{}");
return b.jgrid.useJSON===true&&typeof JSON==="object"&&typeof JSON.parse==="function"?JSON.parse(f):eval("("+f+")")
},parseDate:function(f,j){var i={m:1,d:1,y:1970,h:0,i:0,s:0},c,e,k;
if(j&&j!==null&&j!==undefined){j=b.trim(j);
j=j.split(/[\\\/:_;.\t\T\s-]/);
f=f.split(/[\\\/:_;.\t\T\s-]/);
var l=b.jgrid.formatter.date.monthNames,a=b.jgrid.formatter.date.AmPm,r=function(u,B){if(u===0){if(B==12){B=0
}}else{if(B!=12){B+=12
}}return B
};
c=0;
for(e=f.length;
c<e;
c++){if(f[c]=="M"){k=b.inArray(j[c],l);
if(k!==-1&&k<12){j[c]=k+1
}}if(f[c]=="F"){k=b.inArray(j[c],l);
if(k!==-1&&k>11){j[c]=k+1-12
}}if(f[c]=="a"){k=b.inArray(j[c],a);
if(k!==-1&&k<2&&j[c]==a[k]){j[c]=k;
i.h=r(j[c],i.h)
}}if(f[c]=="A"){k=b.inArray(j[c],a);
if(k!==-1&&k>1&&j[c]==a[k]){j[c]=k-2;
i.h=r(j[c],i.h)
}}if(j[c]!==undefined){i[f[c].toLowerCase()]=parseInt(j[c],10)
}}i.m=parseInt(i.m,10)-1;
f=i.y;
if(f>=70&&f<=99){i.y=1900+i.y
}else{if(f>=0&&f<=69){i.y=2000+i.y
}}}return new Date(i.y,i.m,i.d,i.h,i.i,i.s,0)
},jqID:function(f){f+="";
return f.replace(/([\.\:\[\]])/g,"\\$1")
},getAccessor:function(f,j){var i,c,e,k;
if(typeof j==="function"){return j(f)
}i=f[j];
if(i===undefined){try{if(typeof j==="string"){e=j.split(".")
}if(k=e.length){for(i=f;
i&&k--;
){c=e.shift();
i=i[c]
}}}catch(l){}}return i
},ajaxOptions:{},from:function(f){return new (function(j,i){if(typeof j=="string"){j=b.data(j)
}var c=this,e=j,k=true,l=false,a=i,r=/[\$,%]/g,u=null,B=null,G=false,Q="",J=[],M=true;
if(typeof j=="object"&&j.push){if(j.length>0){M=typeof j[0]!="object"?false:true
}}else{throw"data provides is not an array"
}this._hasData=function(){return e===null?false:e.length===0?false:true
};
this._getStr=function(n){var m=[];
l&&m.push("jQuery.trim(");
m.push("String("+n+")");
l&&m.push(")");
k||m.push(".toLowerCase()");
return m.join("")
};
this._strComp=function(n){return typeof n=="string"?".toString()":""
};
this._group=function(n,m){return{field:n.toString(),unique:m,items:[]}
};
this._toStr=function(n){if(l){n=b.trim(n)
}k||(n=n.toLowerCase());
return n=n.toString().replace(new RegExp('\\"',"g"),'\\"')
};
this._funcLoop=function(n){var m=[];
b.each(e,function(p,A){m.push(n(A))
});
return m
};
this._append=function(n){if(a===null){a=""
}else{a+=Q===""?" && ":Q
}if(G){a+="!"
}a+="("+n+")";
G=false;
Q=""
};
this._setCommand=function(n,m){u=n;
B=m
};
this._resetNegate=function(){G=false
};
this._repeatCommand=function(n,m){if(u===null){return c
}if(n!=null&&m!=null){return u(n,m)
}if(B===null){return u(n)
}if(!M){return u(n)
}return u(B,n)
};
this._equals=function(n,m){return c._compare(n,m,1)===0
};
this._compare=function(n,m,p){if(p===undefined){p=1
}if(n===undefined){n=null
}if(m===undefined){m=null
}if(n===null&&m===null){return 0
}if(n===null&&m!==null){return 1
}if(n!==null&&m===null){return -1
}if(!k){n=n.toLowerCase();
m=m.toLowerCase()
}if(n<m){return -p
}if(n>m){return p
}return 0
};
this._performSort=function(){if(J.length!==0){e=c._doSort(e,0)
}};
this._doSort=function(n,m){var p=J[m].by,A=J[m].dir,t=J[m].type,H=J[m].datefmt;
if(m==J.length-1){return c._getOrder(n,p,A,t,H)
}m++;
n=c._getGroup(n,p,A,t,H);
p=[];
for(A=0;
A<n.length;
A++){t=c._doSort(n[A].items,m);
for(H=0;
H<t.length;
H++){p.push(t[H])
}}return p
};
this._getOrder=function(n,m,p,A,t){var H=[],S=[],Y=p=="a"?1:-1,O,fa;
if(A===undefined){A="text"
}fa=A=="float"||A=="number"||A=="currency"||A=="numeric"?function(P){P=parseFloat(String(P).replace(r,""));
return isNaN(P)?0:P
}:A=="int"||A=="integer"?function(P){return P?parseFloat(String(P).replace(r,"")):0
}:A=="date"||A=="datetime"?function(P){return b.jgrid.parseDate(t,P).getTime()
}:b.isFunction(A)?A:function(P){P||(P="");
return b.trim(String(P).toUpperCase())
};
b.each(n,function(P,ba){O=b.jgrid.getAccessor(ba,m);
if(O===undefined){O=""
}O=fa(O,ba);
S.push({vSort:O,index:P})
});
S.sort(function(P,ba){P=P.vSort;
ba=ba.vSort;
return c._compare(P,ba,Y)
});
A=0;
for(var ca=n.length;
A<ca;
){p=S[A].index;
H.push(n[p]);
A++
}return H
};
this._getGroup=function(n,m,p,A,t){var H=[],S=null,Y=null,O;
b.each(c._getOrder(n,m,p,A,t),function(fa,ca){O=b.jgrid.getAccessor(ca,m);
if(O===undefined){O=""
}if(!c._equals(Y,O)){Y=O;
S!=null&&H.push(S);
S=c._group(m,O)
}S.items.push(ca)
});
S!=null&&H.push(S);
return H
};
this.ignoreCase=function(){k=false;
return c
};
this.useCase=function(){k=true;
return c
};
this.trim=function(){l=true;
return c
};
this.noTrim=function(){l=false;
return c
};
this.combine=function(n){var m=b.from(e);
k||m.ignoreCase();
l&&m.trim();
n=n(m).showQuery();
c._append(n);
return c
};
this.execute=function(){var n=a,m=[];
if(n===null){return c
}b.each(e,function(){eval(n)&&m.push(this)
});
e=m;
return c
};
this.data=function(){return e
};
this.select=function(n){c._performSort();
if(!c._hasData()){return[]
}c.execute();
if(b.isFunction(n)){var m=[];
b.each(e,function(p,A){m.push(n(A))
});
return m
}return e
};
this.hasMatch=function(){if(!c._hasData()){return false
}c.execute();
return e.length>0
};
this.showQuery=function(n){var m=a;
if(m===null){m="no query found"
}if(b.isFunction(n)){n(m);
return c
}return m
};
this.andNot=function(n,m,p){G=!G;
return c.and(n,m,p)
};
this.orNot=function(n,m,p){G=!G;
return c.or(n,m,p)
};
this.not=function(n,m,p){return c.andNot(n,m,p)
};
this.and=function(n,m,p){Q=" && ";
if(n===undefined){return c
}return c._repeatCommand(n,m,p)
};
this.or=function(n,m,p){Q=" || ";
if(n===undefined){return c
}return c._repeatCommand(n,m,p)
};
this.isNot=function(n){G=!G;
return c.is(n)
};
this.is=function(n){c._append("this."+n);
c._resetNegate();
return c
};
this._compareValues=function(n,m,p,A,t){var H;
H=M?"this."+m:"this";
if(p===undefined){p=null
}p=p===null?m:p;
switch(t.stype===undefined?"text":t.stype){case"int":case"integer":p=isNaN(Number(p))?"0":p;
H="parseInt("+H+",10)";
p="parseInt("+p+",10)";
break;
case"float":case"number":case"numeric":p=String(p).replace(r,"");
p=isNaN(Number(p))?"0":p;
H="parseFloat("+H+")";
p="parseFloat("+p+")";
break;
case"date":case"datetime":p=String(b.jgrid.parseDate(t.newfmt||"Y-m-d",p).getTime());
H='jQuery.jgrid.parseDate("'+t.srcfmt+'",'+H+").getTime()";
break;
default:H=c._getStr(H);
p=c._getStr('"'+c._toStr(p)+'"')
}c._append(H+" "+A+" "+p);
c._setCommand(n,m);
c._resetNegate();
return c
};
this.equals=function(n,m,p){return c._compareValues(c.equals,n,m,"==",p)
};
this.greater=function(n,m,p){return c._compareValues(c.greater,n,m,">",p)
};
this.less=function(n,m,p){return c._compareValues(c.less,n,m,"<",p)
};
this.greaterOrEquals=function(n,m,p){return c._compareValues(c.greaterOrEquals,n,m,">=",p)
};
this.lessOrEquals=function(n,m,p){return c._compareValues(c.lessOrEquals,n,m,"<=",p)
};
this.startsWith=function(n,m){var p=m===undefined||m===null?n:m;
p=l?b.trim(p.toString()).length:p.toString().length;
if(M){c._append(c._getStr("this."+n)+".substr(0,"+p+") == "+c._getStr('"'+c._toStr(m)+'"'))
}else{p=l?b.trim(m.toString()).length:m.toString().length;
c._append(c._getStr("this")+".substr(0,"+p+") == "+c._getStr('"'+c._toStr(n)+'"'))
}c._setCommand(c.startsWith,n);
c._resetNegate();
return c
};
this.endsWith=function(n,m){var p=m===undefined||m===null?n:m;
p=l?b.trim(p.toString()).length:p.toString().length;
M?c._append(c._getStr("this."+n)+".substr("+c._getStr("this."+n)+".length-"+p+","+p+') == "'+c._toStr(m)+'"'):c._append(c._getStr("this")+".substr("+c._getStr("this")+'.length-"'+c._toStr(n)+'".length,"'+c._toStr(n)+'".length) == "'+c._toStr(n)+'"');
c._setCommand(c.endsWith,n);
c._resetNegate();
return c
};
this.contains=function(n,m){M?c._append(c._getStr("this."+n)+'.indexOf("'+c._toStr(m)+'",0) > -1'):c._append(c._getStr("this")+'.indexOf("'+c._toStr(n)+'",0) > -1');
c._setCommand(c.contains,n);
c._resetNegate();
return c
};
this.groupBy=function(n,m,p,A){if(!c._hasData()){return null
}return c._getGroup(e,n,m,p,A)
};
this.orderBy=function(n,m,p,A){m=m===undefined||m===null?"a":b.trim(m.toString().toLowerCase());
if(p===null||p===undefined){p="text"
}if(A===null||A===undefined){A="Y-m-d"
}if(m=="desc"||m=="descending"){m="d"
}if(m=="asc"||m=="ascending"){m="a"
}J.push({by:n,dir:m,type:p,datefmt:A});
return c
};
return c
})(f,null)
},extend:function(f){b.extend(b.fn.jqGrid,f);
this.no_legacy_api||b.fn.extend(f)
}});
b.fn.jqGrid=function(f){if(typeof f=="string"){var j=b.jgrid.getAccessor(b.fn.jqGrid,f);
if(!j){throw"jqGrid - No such method: "+f
}var i=b.makeArray(arguments).slice(1);
return j.apply(this,i)
}return this.each(function(){if(!this.grid){var c=b.extend(true,{url:"",height:150,page:1,rowNum:20,rowTotal:null,records:0,pager:"",pgbuttons:true,pginput:true,colModel:[],rowList:[],colNames:[],sortorder:"asc",sortname:"",datatype:"xml",mtype:"GET",altRows:false,selarrrow:[],savedRow:[],shrinkToFit:true,xmlReader:{},jsonReader:{},subGrid:false,subGridModel:[],reccount:0,lastpage:0,lastsort:0,selrow:null,beforeSelectRow:null,onSelectRow:null,onSortCol:null,ondblClickRow:null,onRightClickRow:null,onPaging:null,onSelectAll:null,loadComplete:null,gridComplete:null,loadError:null,loadBeforeSend:null,afterInsertRow:null,beforeRequest:null,onHeaderClick:null,viewrecords:false,loadonce:false,multiselect:false,multikey:false,editurl:null,search:false,caption:"",hidegrid:true,hiddengrid:false,postData:{},userData:{},treeGrid:false,treeGridModel:"nested",treeReader:{},treeANode:-1,ExpandColumn:null,tree_root_level:0,prmNames:{page:"page",rows:"rows",sort:"sidx",order:"sord",search:"_search",nd:"nd",id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del",subgridid:"id",npage:null,totalrows:"totalrows"},forceFit:false,gridstate:"visible",cellEdit:false,cellsubmit:"remote",nv:0,loadui:"enable",toolbar:[false,""],scroll:false,multiboxonly:false,deselectAfterSort:true,scrollrows:false,autowidth:false,scrollOffset:18,cellLayout:5,subGridWidth:20,multiselectWidth:20,gridview:false,rownumWidth:25,rownumbers:false,pagerpos:"center",recordpos:"right",footerrow:false,userDataOnFooter:false,hoverrows:true,altclass:"ui-priority-secondary",viewsortcols:[false,"vertical",true],resizeclass:"",autoencode:false,remapColumns:[],ajaxGridOptions:{},direction:"ltr",toppager:false,headertitles:false,scrollTimeout:40,data:[],_index:{},grouping:false,groupingView:{groupField:[],groupOrder:[],groupText:[],groupColumnShow:[],groupSummary:[],showSummaryOnHide:false,sortitems:[],sortnames:[],groupDataSorted:false,summary:[],summaryval:[],plusicon:"ui-icon-circlesmall-plus",minusicon:"ui-icon-circlesmall-minus"},ignoreCase:false},b.jgrid.defaults,f||{}),e={headers:[],cols:[],footers:[],dragStart:function(d,g,h){this.resizing={idx:d,startX:g.clientX,sOL:h[0]};
this.hDiv.style.cursor="col-resize";
this.curGbox=b("#rs_m"+c.id,"#gbox_"+c.id);
this.curGbox.css({display:"block",left:h[0],top:h[1],height:h[2]});
b.isFunction(c.resizeStart)&&c.resizeStart.call(this,g,d);
document.onselectstart=function(){return false
}
},dragMove:function(d){if(this.resizing){var g=d.clientX-this.resizing.startX;
d=this.headers[this.resizing.idx];
var h=c.direction==="ltr"?d.width+g:d.width-g,q;
if(h>33){this.curGbox.css({left:this.resizing.sOL+g});
if(c.forceFit===true){q=this.headers[this.resizing.idx+c.nv];
g=c.direction==="ltr"?q.width-g:q.width+g;
if(g>33){d.newWidth=h;
q.newWidth=g
}}else{this.newWidth=c.direction==="ltr"?c.tblwidth+g:c.tblwidth-g;
d.newWidth=h
}}}},dragEnd:function(){this.hDiv.style.cursor="default";
if(this.resizing){var d=this.resizing.idx,g=this.headers[d].newWidth||this.headers[d].width;
g=parseInt(g,10);
this.resizing=false;
b("#rs_m"+c.id).css("display","none");
c.colModel[d].width=g;
this.headers[d].width=g;
this.headers[d].el.style.width=g+"px";
this.cols[d].style.width=g+"px";
if(this.footers.length>0){this.footers[d].style.width=g+"px"
}if(c.forceFit===true){g=this.headers[d+c.nv].newWidth||this.headers[d+c.nv].width;
this.headers[d+c.nv].width=g;
this.headers[d+c.nv].el.style.width=g+"px";
this.cols[d+c.nv].style.width=g+"px";
if(this.footers.length>0){this.footers[d+c.nv].style.width=g+"px"
}c.colModel[d+c.nv].width=g
}else{c.tblwidth=this.newWidth||c.tblwidth;
b("table:first",this.bDiv).css("width",c.tblwidth+"px");
b("table:first",this.hDiv).css("width",c.tblwidth+"px");
this.hDiv.scrollLeft=this.bDiv.scrollLeft;
if(c.footerrow){b("table:first",this.sDiv).css("width",c.tblwidth+"px");
this.sDiv.scrollLeft=this.bDiv.scrollLeft
}}b.isFunction(c.resizeStop)&&c.resizeStop.call(this,g,d)
}this.curGbox=null;
document.onselectstart=function(){return true
}
},populateVisible:function(){e.timer&&clearTimeout(e.timer);
e.timer=null;
var d=b(e.bDiv).height();
if(d){var g=b("table:first",e.bDiv),h=b("> tbody > tr:gt(0):visible:first",g).outerHeight()||e.prevRowHeight;
if(h){e.prevRowHeight=h;
var q=c.rowNum,o=e.scrollTop=e.bDiv.scrollTop,x=Math.round(g.position().top)-o,w=x+g.height();
h=h*q;
var C,D,s;
if(w<d&&x<=0&&(c.lastpage===undefined||parseInt((w+o+h-1)/h,10)<=c.lastpage)){D=parseInt((d-w+h-1)/h,10);
if(w>=0||D<2||c.scroll===true){C=Math.round((w+o)/h)+1;
x=-1
}else{x=1
}}if(x>0){C=parseInt(o/h,10)+1;
D=parseInt((o+d)/h,10)+2-C;
s=true
}if(D){if(!(c.lastpage&&C>c.lastpage||c.lastpage==1)){if(e.hDiv.loading){e.timer=setTimeout(e.populateVisible,c.scrollTimeout)
}else{c.page=C;
if(s){e.selectionPreserver(g[0]);
e.emptyRows(e.bDiv,false)
}e.populate(D)
}}}}}},scrollGrid:function(){if(c.scroll){var d=e.bDiv.scrollTop;
if(e.scrollTop===undefined){e.scrollTop=0
}if(d!=e.scrollTop){e.scrollTop=d;
e.timer&&clearTimeout(e.timer);
e.timer=setTimeout(e.populateVisible,c.scrollTimeout)
}}e.hDiv.scrollLeft=e.bDiv.scrollLeft;
if(c.footerrow){e.sDiv.scrollLeft=e.bDiv.scrollLeft
}},selectionPreserver:function(d){var g=d.p,h=g.selrow,q=g.selarrrow?b.makeArray(g.selarrrow):null,o=d.grid.bDiv.scrollLeft,x=g.gridComplete;
g.gridComplete=function(){g.selrow=null;
g.selarrrow=[];
if(g.multiselect&&q&&q.length>0){for(var w=0;
w<q.length;
w++){q[w]!=h&&b(d).jqGrid("setSelection",q[w],false)
}}h&&b(d).jqGrid("setSelection",h,false);
d.grid.bDiv.scrollLeft=o;
g.gridComplete=x;
g.gridComplete&&x()
}
}};
if(this.tagName!="TABLE"){alert("Element is not a table")
}else{b(this).empty();
this.p=c;
var k,l,a;
if(this.p.colNames.length===0){for(k=0;
k<this.p.colModel.length;
k++){this.p.colNames[k]=this.p.colModel[k].label||this.p.colModel[k].name
}}if(this.p.colNames.length!==this.p.colModel.length){alert(b.jgrid.errors.model)
}else{var r=b("<div class='ui-jqgrid-view'></div>"),u,B=b.browser.msie?true:false,G=b.browser.safari?true:false;
a=this;
a.p.direction=b.trim(a.p.direction.toLowerCase());
if(b.inArray(a.p.direction,["ltr","rtl"])==-1){a.p.direction="ltr"
}l=a.p.direction;
b(r).insertBefore(this);
b(this).appendTo(r).removeClass("scroll");
var Q=b("<div class='ui-jqgrid ui-widget ui-widget-content ui-corner-all'></div>");
b(Q).insertBefore(r).attr({id:"gbox_"+this.id,dir:l});
b(r).appendTo(Q).attr("id","gview_"+this.id);
u=B&&b.browser.version<=6?'<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>':"";
b("<div class='ui-widget-overlay jqgrid-overlay' id='lui_"+this.id+"'></div>").append(u).insertBefore(r);
b("<div class='loading ui-state-default ui-state-active' id='load_"+this.id+"'>"+this.p.loadtext+"</div>").insertBefore(r);
b(this).attr({cellSpacing:"0",cellPadding:"0",border:"0",role:"grid","aria-multiselectable":!!this.p.multiselect,"aria-labelledby":"gbox_"+this.id});
var J=function(d,g){d=parseInt(d,10);
return isNaN(d)?g?g:0:d
},M=function(d,g,h){var q=a.p.colModel[d],o=q.align,x='style="',w=q.classes,C=q.name;
if(o){x+="text-align:"+o+";"
}if(q.hidden===true){x+="display:none;"
}if(g===0){x+="width: "+e.headers[d].width+"px;"
}x+='"'+(w!==undefined?' class="'+w+'"':"")+(q.title&&h?' title="'+b.jgrid.stripHtml(h)+'"':"");
x+=' aria-describedby="'+a.p.id+"_"+C+'"';
return x
},n=function(d){return d===undefined||d===null||d===""?"&#160;":a.p.autoencode?b.jgrid.htmlEncode(d):d+""
},m=function(d,g,h,q,o){h=a.p.colModel[h];
if(typeof h.formatter!=="undefined"){d={rowId:d,colModel:h,gid:a.p.id};
g=b.isFunction(h.formatter)?h.formatter.call(a,g,d,q,o):b.fmatter?b.fn.fmatter(h.formatter,g,d,q,o):n(g)
}else{g=n(g)
}return g
},p=function(d,g,h,q,o){d=m(d,g,h,o,"add");
return'<td role="gridcell" '+M(h,q,d)+">"+d+"</td>"
},A=function(d,g,h){d='<input role="checkbox" type="checkbox" id="jqg_'+a.p.id+"_"+d+'" class="cbox" name="jqg_'+a.p.id+"_"+d+'"/>';
g=M(g,h,"");
return'<td role="gridcell" aria-describedby="'+a.p.id+'_cb" '+g+">"+d+"</td>"
},t=function(d,g,h,q){h=(parseInt(h,10)-1)*parseInt(q,10)+1+g;
d=M(d,g,"");
return'<td role="gridcell" aria-describedby="'+a.p.id+'_rn" class="ui-state-default jqgrid-rownum" '+d+">"+h+"</td>"
},H=function(d){var g,h=[],q=0,o;
for(o=0;
o<a.p.colModel.length;
o++){g=a.p.colModel[o];
if(g.name!=="cb"&&g.name!=="subgrid"&&g.name!=="rn"){h[q]=d=="local"?g.name:d=="xml"?g.xmlmap||g.name:g.jsonmap||g.name;
q++
}}return h
},S=function(d){var g=a.p.remapColumns;
if(!g||!g.length){g=b.map(a.p.colModel,function(h,q){return q
})
}if(d){g=b.map(g,function(h){return h<d?null:h-d
})
}return g
},Y=function(d,g){if(a.p.deepempty){b("#"+a.p.id+" tbody:first tr:gt(0)").remove()
}else{var h=b("#"+a.p.id+" tbody:first tr:first")[0];
b("#"+a.p.id+" tbody:first").empty().append(h)
}if(g&&a.p.scroll){b(">div:first",d).css({height:"auto"}).children("div:first").css({height:0,display:"none"});
d.scrollTop=0
}},O=function(){var d=a.p.data.length,g,h,q;
g=a.p.rownumbers===true?1:0;
h=a.p.multiselect===true?1:0;
q=a.p.subGrid===true?1:0;
g=a.p.keyIndex===false||a.p.loadonce===true?a.p.localReader.id:a.p.colModel[a.p.keyIndex+h+q+g].name;
for(h=0;
h<d;
h++){q=b.jgrid.getAccessor(a.p.data[h],g);
a.p._index[q]=h
}},fa=function(d,g,h,q,o){var x=new Date,w=a.p.datatype!="local"&&a.p.loadonce||a.p.datatype=="xmlstring",C,D=a.p.datatype=="local"?"local":"xml";
if(w){a.p.data=[];
a.p._index={};
a.p.localReader.id=C="_id_"
}a.p.reccount=0;
if(b.isXMLDoc(d)){if(a.p.treeANode===-1&&!a.p.scroll){Y(g,false);
h=1
}else{h=h>1?h:1
}var s,v=0,y,E,I=0,F=0,K=0,z,U=[],V,T={},N,L,X=[],oa=a.p.altRows===true?" "+a.p.altclass:"";
a.p.xmlReader.repeatitems||(U=H(D));
z=a.p.keyIndex===false?a.p.xmlReader.id:a.p.keyIndex;
if(U.length>0&&!isNaN(z)){if(a.p.remapColumns&&a.p.remapColumns.length){z=b.inArray(z,a.p.remapColumns)
}z=U[z]
}D=(z+"").indexOf("[")===-1?U.length?function(da,Z){return b(z,da).text()||Z
}:function(da,Z){return b(a.p.xmlReader.cell,da).eq(z).text()||Z
}:function(da,Z){return da.getAttribute(z.replace(/[\[\]]/g,""))||Z
};
a.p.userData={};
b(a.p.xmlReader.page,d).each(function(){a.p.page=this.textContent||this.text||0
});
b(a.p.xmlReader.total,d).each(function(){a.p.lastpage=this.textContent||this.text;
if(a.p.lastpage===undefined){a.p.lastpage=1
}});
b(a.p.xmlReader.records,d).each(function(){a.p.records=this.textContent||this.text||0
});
b(a.p.xmlReader.userdata,d).each(function(){a.p.userData[this.getAttribute("name")]=this.textContent||this.text
});
d=b(a.p.xmlReader.root+" "+a.p.xmlReader.row,d);
var ga=d.length,$=0;
if(d&&ga){var ha=parseInt(a.p.rowNum,10),ra=a.p.scroll?(parseInt(a.p.page,10)-1)*ha+1:1;
if(o){ha*=o+1
}o=b.isFunction(a.p.afterInsertRow);
var ia={},xa="";
if(a.p.grouping&&a.p.groupingView.groupCollapse===true){xa=' style="display:none;"'
}for(;
$<ga;
){N=d[$];
L=D(N,ra+$);
s=h===0?0:h+1;
s=(s+$)%2==1?oa:"";
X.push("<tr"+xa+' id="'+L+'" role="row" class ="ui-widget-content jqgrow ui-row-'+a.p.direction+""+s+'">');
if(a.p.rownumbers===true){X.push(t(0,$,a.p.page,a.p.rowNum));
K=1
}if(a.p.multiselect===true){X.push(A(L,K,$));
I=1
}if(a.p.subGrid===true){X.push(b(a).jqGrid("addSubGridCell",I+K,$+h));
F=1
}if(a.p.xmlReader.repeatitems){V||(V=S(I+F+K));
var Ba=b(a.p.xmlReader.cell,N);
b.each(V,function(da){var Z=Ba[this];
if(!Z){return false
}y=Z.textContent||Z.text;
T[a.p.colModel[da+I+F+K].name]=y;
X.push(p(L,y,da+I+F+K,$+h,N))
})
}else{for(s=0;
s<U.length;
s++){y=b(U[s],N).text();
T[a.p.colModel[s+I+F+K].name]=y;
X.push(p(L,y,s+I+F+K,$+h,N))
}}X.push("</tr>");
if(a.p.grouping){s=a.p.groupingView.groupField.length;
E=[];
for(var ya=0;
ya<s;
ya++){E.push(T[a.p.groupingView.groupField[ya]])
}ia=b(a).jqGrid("groupingPrepare",X,E,ia,T);
X=[]
}if(w){T[C]=L;
a.p.data.push(T)
}if(a.p.gridview===false){if(a.p.treeGrid===true){s=a.p.treeANode>=-1?a.p.treeANode:0;
E=b(X.join(""))[0];
b(a.rows[$+s+h]).after(E);
try{b(a).jqGrid("setTreeNode",T,E)
}catch(Ia){}}else{b("tbody:first",g).append(X.join(""))
}if(a.p.subGrid===true){try{b(a).jqGrid("addSubGrid",a.rows[a.rows.length-1],I+K)
}catch(Ja){}}o&&a.p.afterInsertRow.call(a,L,T,N);
X=[]
}T={};
v++;
$++;
if(v==ha){break
}}}if(a.p.gridview===true){if(a.p.grouping&&V){b(a).jqGrid("groupingRender",ia,a.p.colModel.length);
ia=null
}else{b("tbody:first",g).append(X.join(""))
}}a.p.totaltime=new Date-x;
if(v>0){if(a.p.records===0){a.p.records=ga
}}X=null;
if(!a.p.treeGrid&&!a.p.scroll){a.grid.bDiv.scrollTop=0
}a.p.reccount=v;
a.p.treeANode=-1;
a.p.userDataOnFooter&&b(a).jqGrid("footerData","set",a.p.userData,true);
if(w){a.p.records=ga;
a.p.lastpage=Math.ceil(ga/ha)
}q||a.updatepager(false,true);
if(w){for(;
v<ga;
){N=d[v];
L=D(N,v);
if(a.p.xmlReader.repeatitems){V||(V=S(I+F+K));
var Ea=b(a.p.xmlReader.cell,N);
b.each(V,function(da){var Z=Ea[this];
if(!Z){return false
}y=Z.textContent||Z.text;
T[a.p.colModel[da+I+F+K].name]=y
})
}else{for(s=0;
s<U.length;
s++){y=b(U[s],N).text();
T[a.p.colModel[s+I+F+K].name]=y
}}T[C]=L;
a.p.data.push(T);
T={};
v++
}O()
}}},ca=function(d,g,h,q,o){var x=new Date;
if(d){if(a.p.treeANode===-1&&!a.p.scroll){Y(g,false);
h=1
}else{h=h>1?h:1
}var w,C,D=a.p.datatype!="local"&&a.p.loadonce||a.p.datatype=="jsonstring";
if(D){a.p.data=[];
a.p._index={};
w=a.p.localReader.id="_id_"
}a.p.reccount=0;
if(a.p.datatype=="local"){g=a.p.localReader;
C="local"
}else{g=a.p.jsonReader;
C="json"
}var s=0,v,y,E,I=[],F,K=0,z=0,U=0,V,T,N={},L;
E=[];
var X=a.p.altRows===true?" "+a.p.altclass:"";
a.p.page=b.jgrid.getAccessor(d,g.page)||0;
V=b.jgrid.getAccessor(d,g.total);
a.p.lastpage=V===undefined?1:V;
a.p.records=b.jgrid.getAccessor(d,g.records)||0;
a.p.userData=b.jgrid.getAccessor(d,g.userdata)||{};
g.repeatitems||(F=I=H(C));
C=a.p.keyIndex===false?g.id:a.p.keyIndex;
if(I.length>0&&!isNaN(C)){if(a.p.remapColumns&&a.p.remapColumns.length){C=b.inArray(C,a.p.remapColumns)
}C=I[C]
}if(T=b.jgrid.getAccessor(d,g.root)){V=T.length;
d=0;
var oa=parseInt(a.p.rowNum,10),ga=a.p.scroll?(parseInt(a.p.page,10)-1)*oa+1:1;
if(o){oa*=o+1
}var $=b.isFunction(a.p.afterInsertRow),ha={},ra="";
if(a.p.grouping&&a.p.groupingView.groupCollapse===true){ra=' style="display:none;"'
}for(;
d<V;
){o=T[d];
L=b.jgrid.getAccessor(o,C);
if(L===undefined){L=ga+d;
if(I.length===0){if(g.cell){L=o[g.cell][C]||L
}}}v=h===1?0:h;
v=(v+d)%2==1?X:"";
E.push("<tr"+ra+' id="'+L+'" role="row" class= "ui-widget-content jqgrow ui-row-'+a.p.direction+""+v+'">');
if(a.p.rownumbers===true){E.push(t(0,d,a.p.page,a.p.rowNum));
U=1
}if(a.p.multiselect){E.push(A(L,U,d));
K=1
}if(a.p.subGrid){E.push(b(a).jqGrid("addSubGridCell",K+U,d+h));
z=1
}if(g.repeatitems){if(g.cell){o=b.jgrid.getAccessor(o,g.cell)
}F||(F=S(K+z+U))
}for(y=0;
y<F.length;
y++){v=b.jgrid.getAccessor(o,F[y]);
E.push(p(L,v,y+K+z+U,d+h,o));
N[a.p.colModel[y+K+z+U].name]=v
}E.push("</tr>");
if(a.p.grouping){v=a.p.groupingView.groupField.length;
y=[];
for(var ia=0;
ia<v;
ia++){y.push(N[a.p.groupingView.groupField[ia]])
}ha=b(a).jqGrid("groupingPrepare",E,y,ha,N);
E=[]
}if(D){N[w]=L;
a.p.data.push(N)
}if(a.p.gridview===false){if(a.p.treeGrid===true){v=a.p.treeANode>=-1?a.p.treeANode:0;
E=b(E.join(""))[0];
b(a.rows[d+v+h]).after(E);
try{b(a).jqGrid("setTreeNode",N,E)
}catch(xa){}}else{b("#"+a.p.id+" tbody:first").append(E.join(""))
}if(a.p.subGrid===true){try{b(a).jqGrid("addSubGrid",a.rows[a.rows.length-1],K+U)
}catch(Ba){}}$&&a.p.afterInsertRow.call(a,L,N,o);
E=[]
}N={};
s++;
d++;
if(s==oa){break
}}if(a.p.gridview===true){a.p.grouping&&F?b(a).jqGrid("groupingRender",ha,a.p.colModel.length):b("#"+a.p.id+" tbody:first").append(E.join(""))
}a.p.totaltime=new Date-x;
if(s>0){if(a.p.records===0){a.p.records=V
}}if(!a.p.treeGrid&&!a.p.scroll){a.grid.bDiv.scrollTop=0
}a.p.reccount=s;
a.p.treeANode=-1;
a.p.userDataOnFooter&&b(a).jqGrid("footerData","set",a.p.userData,true);
if(D){a.p.records=V;
a.p.lastpage=Math.ceil(V/oa)
}q||a.updatepager(false,true);
if(D){for(;
s<V;
){o=T[s];
L=b.jgrid.getAccessor(o,C);
if(L===undefined){L=ga+s;
if(I.length===0){if(g.cell){L=o[g.cell][C]||L
}}}if(o){if(g.repeatitems){if(g.cell){o=b.jgrid.getAccessor(o,g.cell)
}F||(F=S(K+z+U))
}for(y=0;
y<F.length;
y++){v=b.jgrid.getAccessor(o,F[y]);
N[a.p.colModel[y+K+z+U].name]=v
}N[w]=L;
a.p.data.push(N);
N={}
}s++
}O()
}}}},P=function(){var d,g=false,h=[],q=[],o,x,w;
if(b.isArray(a.p.data)){var C=a.p.grouping?a.p.groupingView:false;
b.each(a.p.colModel,function(){x=this.sorttype||"text";
if(x=="date"||x=="datetime"){if(this.formatter&&typeof this.formatter==="string"&&this.formatter=="date"){o=this.formatoptions&&this.formatoptions.srcformat?this.formatoptions.srcformat:b.jgrid.formatter.date.srcformat;
w=this.formatoptions&&this.formatoptions.newformat?this.formatoptions.newformat:b.jgrid.formatter.date.newformat
}else{o=w=this.datefmt||"Y-m-d"
}h[this.name]={stype:x,srcfmt:o,newfmt:w}
}else{h[this.name]={stype:x,srcfmt:"",newfmt:""}
}if(a.p.grouping&&this.name==C.groupField[0]){q[0]=h[this.name]
}if(!g&&(this.index==a.p.sortname||this.name==a.p.sortname)){d=this.name;
g=true
}});
if(a.p.treeGrid){b(a).jqGrid("SortTree",d,a.p.sortorder,h[d].stype,h[d].srcfmt)
}else{var D={eq:function(z){return z.equals
},ne:function(z){return z.not().equals
},lt:function(z){return z.less
},le:function(z){return z.lessOrEquals
},gt:function(z){return z.greater
},ge:function(z){return z.greaterOrEquals
},cn:function(z){return z.contains
},nc:function(z){return z.not().contains
},bw:function(z){return z.startsWith
},bn:function(z){return z.not().startsWith
},en:function(z){return z.not().endsWith
},ew:function(z){return z.endsWith
},ni:function(z){return z.not().equals
},"in":function(z){return z.equals
}},s=b.jgrid.from(a.p.data);
if(a.p.ignoreCase){s=s.ignoreCase()
}if(a.p.search===true){var v=a.p.postData.filters,y;
if(v){if(typeof v=="string"){v=b.jgrid.parse(v)
}for(var E=0,I=v.rules.length,F;
E<I;
E++){F=v.rules[E];
y=v.groupOp;
if(D[F.op]&&F.field&&F.data&&y){s=y.toUpperCase()=="OR"?D[F.op](s)(F.field,F.data,h[F.field]).or():D[F.op](s)(F.field,F.data,h[F.field])
}}}else{try{s=D[a.p.postData.searchOper](s)(a.p.postData.searchField,a.p.postData.searchString,h[a.p.postData.searchField])
}catch(K){}}}if(a.p.grouping){s.orderBy(C.groupField[0],C.groupOrder[0],q[0].stype,q[0].srcfmt);
C.groupDataSorted=true
}if(d&&a.p.sortorder&&g){a.p.sortorder.toUpperCase()=="DESC"?s.orderBy(a.p.sortname,"d",h[d].stype,h[d].srcfmt):s.orderBy(a.p.sortname,"a",h[d].stype,h[d].srcfmt)
}D=s.select();
s=parseInt(a.p.rowNum,10);
v=D.length;
y=parseInt(a.p.page,10);
E=Math.ceil(v/s);
I={};
D=D.slice((y-1)*s,y*s);
h=s=null;
I[a.p.localReader.total]=E;
I[a.p.localReader.page]=y;
I[a.p.localReader.records]=v;
I[a.p.localReader.root]=D;
D=null;
return I
}}},ba=function(){a.grid.hDiv.loading=true;
if(!a.p.hiddengrid){switch(a.p.loadui){case"disable":break;
case"enable":b("#load_"+a.p.id).show();
break;
case"block":b("#lui_"+a.p.id).show();
b("#load_"+a.p.id).show();
break
}}},pa=function(){a.grid.hDiv.loading=false;
switch(a.p.loadui){case"disable":break;
case"enable":b("#load_"+a.p.id).hide();
break;
case"block":b("#lui_"+a.p.id).hide();
b("#load_"+a.p.id).hide();
break
}},ja=function(d){if(!a.grid.hDiv.loading){var g=a.p.scroll&&d===false,h={},q,o=a.p.prmNames;
if(a.p.page<=0){a.p.page=1
}if(o.search!==null){h[o.search]=a.p.search
}if(o.nd!==null){h[o.nd]=(new Date).getTime()
}if(o.rows!==null){h[o.rows]=a.p.rowNum
}if(o.page!==null){h[o.page]=a.p.page
}if(o.sort!==null){h[o.sort]=a.p.sortname
}if(o.order!==null){h[o.order]=a.p.sortorder
}if(a.p.rowTotal!==null&&o.totalrows!==null){h[o.totalrows]=a.p.rowTotal
}var x=a.p.loadComplete,w=b.isFunction(x);
w||(x=null);
var C=0;
d=d||1;
if(d>1){if(o.npage!==null){h[o.npage]=d;
C=d-1;
d=1
}else{x=function(s){a.p.page++;
a.grid.hDiv.loading=false;
w&&a.p.loadComplete.call(a,s);
ja(d-1)
}
}}else{o.npage!==null&&delete a.p.postData[o.npage]
}if(a.p.grouping){b(a).jqGrid("groupingSetup");
if(a.p.groupingView.groupDataSorted===true){h[o.sort]=a.p.groupingView.groupField[0]+" "+a.p.groupingView.groupOrder[0]+", "+h[o.sort]
}}b.extend(a.p.postData,h);
var D=!a.p.scroll?1:a.rows.length-1;
if(b.isFunction(a.p.datatype)){a.p.datatype.call(a,a.p.postData,"load_"+a.p.id)
}else{b.isFunction(a.p.beforeRequest)&&a.p.beforeRequest.call(a);
q=a.p.datatype.toLowerCase();
switch(q){case"json":case"jsonp":case"xml":case"script":b.ajax(b.extend({url:a.p.url,type:a.p.mtype,dataType:q,data:b.isFunction(a.p.serializeGridData)?a.p.serializeGridData.call(a,a.p.postData):a.p.postData,success:function(s){q==="xml"?fa(s,a.grid.bDiv,D,d>1,C):ca(s,a.grid.bDiv,D,d>1,C);
x&&x.call(a,s);
g&&a.grid.populateVisible();
if(a.p.loadonce||a.p.treeGrid){a.p.datatype="local"
}pa()
},error:function(s,v,y){b.isFunction(a.p.loadError)&&a.p.loadError.call(a,s,v,y);
pa()
},beforeSend:function(s){ba();
b.isFunction(a.p.loadBeforeSend)&&a.p.loadBeforeSend.call(a,s)
}},b.jgrid.ajaxOptions,a.p.ajaxGridOptions));
break;
case"xmlstring":ba();
h=b.jgrid.stringToDoc(a.p.datastr);
fa(h,a.grid.bDiv);
w&&a.p.loadComplete.call(a,h);
a.p.datatype="local";
a.p.datastr=null;
pa();
break;
case"jsonstring":ba();
h=typeof a.p.datastr=="string"?b.jgrid.parse(a.p.datastr):a.p.datastr;
ca(h,a.grid.bDiv);
w&&a.p.loadComplete.call(a,h);
a.p.datatype="local";
a.p.datastr=null;
pa();
break;
case"local":case"clientside":ba();
a.p.datatype="local";
h=P();
ca(h,a.grid.bDiv,D,d>1,C);
x&&x.call(a,h);
g&&a.grid.populateVisible();
pa();
break
}}}};
u=function(d,g){var h="",q="<table cellspacing='0' cellpadding='0' border='0' style='table-layout:auto;' class='ui-pg-table'><tbody><tr>",o="",x,w,C,D,s=function(v){var y;
if(b.isFunction(a.p.onPaging)){y=a.p.onPaging.call(a,v)
}a.p.selrow=null;
if(a.p.multiselect){a.p.selarrrow=[];
b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv).attr("checked",false)
}a.p.savedRow=[];
if(y=="stop"){return false
}return true
};
d=d.substr(1);
x="pg_"+d;
w=d+"_left";
C=d+"_center";
D=d+"_right";
b("#"+d).append("<div id='"+x+"' class='ui-pager-control' role='group'><table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table' style='width:100%;table-layout:fixed;' role='row'><tbody><tr><td id='"+w+"' align='left'></td><td id='"+C+"' align='center' style='white-space:pre;'></td><td id='"+D+"' align='right'></td></tr></tbody></table></div>").attr("dir","ltr");
if(a.p.rowList.length>0){o="<td dir='"+l+"'>";
o+="<select class='ui-pg-selbox' role='listbox'>";
for(w=0;
w<a.p.rowList.length;
w++){o+='<option role="option" value="'+a.p.rowList[w]+'"'+(a.p.rowNum==a.p.rowList[w]?' selected="selected"':"")+">"+a.p.rowList[w]+"</option>"
}o+="</select></td>"
}if(l=="rtl"){q+=o
}if(a.p.pginput===true){h="<td dir='"+l+"'>"+b.jgrid.format(a.p.pgtext||"","<input class='ui-pg-input' type='text' size='2' maxlength='7' value='0' role='textbox'/>","<span id='sp_1'></span>")+"</td>"
}if(a.p.pgbuttons===true){w=["first"+g,"prev"+g,"next"+g,"last"+g];
l=="rtl"&&w.reverse();
q+="<td id='"+w[0]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-first'></span></td>";
q+="<td id='"+w[1]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-prev'></span></td>";
q+=h!==""?"<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>"+h+"<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>":"";
q+="<td id='"+w[2]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-next'></span></td>";
q+="<td id='"+w[3]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-end'></span></td>"
}else{if(h!==""){q+=h
}}if(l=="ltr"){q+=o
}q+="</tr></tbody></table>";
a.p.viewrecords===true&&b("td#"+d+"_"+a.p.recordpos,"#"+x).append("<div dir='"+l+"' style='text-align:"+a.p.recordpos+"' class='ui-paging-info'></div>");
b("td#"+d+"_"+a.p.pagerpos,"#"+x).append(q);
o=b(".ui-jqgrid").css("font-size")||"11px";
b(document.body).append("<div id='testpg' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+o+";visibility:hidden;' ></div>");
q=b(q).clone().appendTo("#testpg").width();
b("#testpg").remove();
if(q>0){if(h!=""){q+=50
}b("td#"+d+"_"+a.p.pagerpos,"#"+x).width(q)
}a.p._nvtd=[];
a.p._nvtd[0]=q?Math.floor((a.p.width-q)/2):Math.floor(a.p.width/3);
a.p._nvtd[1]=0;
q=null;
b(".ui-pg-selbox","#"+x).bind("change",function(){a.p.page=Math.round(a.p.rowNum*(a.p.page-1)/this.value-0.5)+1;
a.p.rowNum=this.value;
if(g){b(".ui-pg-selbox",a.p.pager).val(this.value)
}else{a.p.toppager&&b(".ui-pg-selbox",a.p.toppager).val(this.value)
}if(!s("records")){return false
}ja();
return false
});
if(a.p.pgbuttons===true){b(".ui-pg-button","#"+x).hover(function(){if(b(this).hasClass("ui-state-disabled")){this.style.cursor="default"
}else{b(this).addClass("ui-state-hover");
this.style.cursor="pointer"
}},function(){if(!b(this).hasClass("ui-state-disabled")){b(this).removeClass("ui-state-hover");
this.style.cursor="default"
}});
b("#first"+g+", #prev"+g+", #next"+g+", #last"+g,"#"+d).click(function(){var v=J(a.p.page,1),y=J(a.p.lastpage,1),E=false,I=true,F=true,K=true,z=true;
if(y===0||y===1){z=K=F=I=false
}else{if(y>1&&v>=1){if(v===1){F=I=false
}else{if(!(v>1&&v<y)){if(v===y){z=K=false
}}}}else{if(y>1&&v===0){z=K=false;
v=y-1
}}}if(this.id==="first"+g&&I){a.p.page=1;
E=true
}if(this.id==="prev"+g&&F){a.p.page=v-1;
E=true
}if(this.id==="next"+g&&K){a.p.page=v+1;
E=true
}if(this.id==="last"+g&&z){a.p.page=y;
E=true
}if(E){if(!s(this.id)){return false
}ja()
}return false
})
}a.p.pginput===true&&b("input.ui-pg-input","#"+x).keypress(function(v){if((v.charCode?v.charCode:v.keyCode?v.keyCode:0)==13){a.p.page=b(this).val()>0?b(this).val():a.p.page;
if(!s("user")){return false
}ja();
return false
}return this
})
};
var Ca=function(d,g,h,q){if(a.p.colModel[g].sortable){if(!(a.p.savedRow.length>0)){if(!h){if(a.p.lastsort==g){if(a.p.sortorder=="asc"){a.p.sortorder="desc"
}else{if(a.p.sortorder=="desc"){a.p.sortorder="asc"
}}}else{a.p.sortorder=a.p.colModel[g].firstsortorder||"asc"
}a.p.page=1
}if(q){if(a.p.lastsort==g&&a.p.sortorder==q&&!h){return
}else{a.p.sortorder=q
}}h=b("thead:first",a.grid.hDiv).get(0);
b("tr th:eq("+a.p.lastsort+") span.ui-grid-ico-sort",h).addClass("ui-state-disabled");
b("tr th:eq("+a.p.lastsort+")",h).attr("aria-selected","false");
b("tr th:eq("+g+") span.ui-icon-"+a.p.sortorder,h).removeClass("ui-state-disabled");
b("tr th:eq("+g+")",h).attr("aria-selected","true");
if(!a.p.viewsortcols[0]){if(a.p.lastsort!=g){b("tr th:eq("+a.p.lastsort+") span.s-ico",h).hide();
b("tr th:eq("+g+") span.s-ico",h).show()
}}d=d.substring(5);
a.p.sortname=a.p.colModel[g].index||d;
h=a.p.sortorder;
if(b.isFunction(a.p.onSortCol)){if(a.p.onSortCol.call(a,d,g,h)=="stop"){a.p.lastsort=g;
return
}}if(a.p.datatype=="local"){a.p.deselectAfterSort&&b(a).jqGrid("resetSelection")
}else{a.p.selrow=null;
a.p.multiselect&&b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv).attr("checked",false);
a.p.selarrrow=[];
a.p.savedRow=[]
}if(a.p.scroll){h=a.grid.bDiv.scrollLeft;
Y(a.grid.bDiv,true);
a.grid.hDiv.scrollLeft=h
}a.p.subGrid&&a.p.datatype=="local"&&b("td.sgexpanded","#"+a.p.id).each(function(){b(this).trigger("click")
});
ja();
a.p.lastsort=g;
if(a.p.sortname!=d&&g){a.p.lastsort=g
}}}},Fa=function(d){var g=d,h;
for(h=d+1;
h<a.p.colModel.length;
h++){if(a.p.colModel[h].hidden!==true){g=h;
break
}}return g-d
},Ga=function(d){var g,h={},q=G?0:a.p.cellLayout;
for(g=h[0]=h[1]=h[2]=0;
g<=d;
g++){if(a.p.colModel[g].hidden===false){h[0]+=a.p.colModel[g].width+q
}}if(a.p.direction=="rtl"){h[0]=a.p.width-h[0]
}h[0]-=a.grid.bDiv.scrollLeft;
if(b(a.grid.cDiv).is(":visible")){h[1]+=b(a.grid.cDiv).height()+parseInt(b(a.grid.cDiv).css("padding-top"),10)+parseInt(b(a.grid.cDiv).css("padding-bottom"),10)
}if(a.p.toolbar[0]===true&&(a.p.toolbar[1]=="top"||a.p.toolbar[1]=="both")){h[1]+=b(a.grid.uDiv).height()+parseInt(b(a.grid.uDiv).css("border-top-width"),10)+parseInt(b(a.grid.uDiv).css("border-bottom-width"),10)
}if(a.p.toppager){h[1]+=b(a.grid.topDiv).height()+parseInt(b(a.grid.topDiv).css("border-bottom-width"),10)
}h[2]+=b(a.grid.bDiv).height()+b(a.grid.hDiv).height();
return h
};
this.p.id=this.id;
if(b.inArray(a.p.multikey,["shiftKey","altKey","ctrlKey"])==-1){a.p.multikey=false
}a.p.keyIndex=false;
for(k=0;
k<a.p.colModel.length;
k++){if(a.p.colModel[k].key===true){a.p.keyIndex=k;
break
}}a.p.sortorder=a.p.sortorder.toLowerCase();
if(a.p.grouping===true){a.p.scroll=false;
a.p.rownumbers=false;
a.p.subGrid=false;
a.p.treeGrid=false;
a.p.gridview=true
}if(this.p.treeGrid===true){try{b(this).jqGrid("setTreeGrid")
}catch(Ka){}if(a.p.datatype!="local"){a.p.localReader={id:"_id_"}
}}if(this.p.subGrid){try{b(a).jqGrid("setSubGrid")
}catch(La){}}if(this.p.multiselect){this.p.colNames.unshift("<input role='checkbox' id='cb_"+this.p.id+"' class='cbox' type='checkbox'/>");
this.p.colModel.unshift({name:"cb",width:G?a.p.multiselectWidth+a.p.cellLayout:a.p.multiselectWidth,sortable:false,resizable:false,hidedlg:true,search:false,align:"center",fixed:true})
}if(this.p.rownumbers){this.p.colNames.unshift("");
this.p.colModel.unshift({name:"rn",width:a.p.rownumWidth,sortable:false,resizable:false,hidedlg:true,search:false,align:"center",fixed:true})
}a.p.xmlReader=b.extend(true,{root:"rows",row:"row",page:"rows>page",total:"rows>total",records:"rows>records",repeatitems:true,cell:"cell",id:"[id]",userdata:"userdata",subgrid:{root:"rows",row:"row",repeatitems:true,cell:"cell"}},a.p.xmlReader);
a.p.jsonReader=b.extend(true,{root:"rows",page:"page",total:"total",records:"records",repeatitems:true,cell:"cell",id:"id",userdata:"userdata",subgrid:{root:"rows",repeatitems:true,cell:"cell"}},a.p.jsonReader);
a.p.localReader=b.extend(true,{root:"rows",page:"page",total:"total",records:"records",repeatitems:false,cell:"cell",id:"id",userdata:"userdata",subgrid:{root:"rows",repeatitems:true,cell:"cell"}},a.p.localReader);
if(a.p.scroll){a.p.pgbuttons=false;
a.p.pginput=false;
a.p.rowList=[]
}a.p.data.length&&O();
var aa="<thead><tr class='ui-jqgrid-labels' role='rowheader'>",Da,ma,sa,qa,ta,W,R,na;
ma=na="";
if(a.p.shrinkToFit===true&&a.p.forceFit===true){for(k=a.p.colModel.length-1;
k>=0;
k--){if(!a.p.colModel[k].hidden){a.p.colModel[k].resizable=false;
break
}}}if(a.p.viewsortcols[1]=="horizontal"){na=" ui-i-asc";
ma=" ui-i-desc"
}Da=B?"class='ui-th-div-ie'":"";
na="<span class='s-ico' style='display:none'><span sort='asc' class='ui-grid-ico-sort ui-icon-asc"+na+" ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-"+l+"'></span>";
na+="<span sort='desc' class='ui-grid-ico-sort ui-icon-desc"+ma+" ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-"+l+"'></span></span>";
for(k=0;
k<this.p.colNames.length;
k++){ma=a.p.headertitles?' title="'+b.jgrid.stripHtml(a.p.colNames[k])+'"':"";
aa+="<th id='"+a.p.id+"_"+a.p.colModel[k].name+"' role='columnheader' class='ui-state-default ui-th-column ui-th-"+l+"'"+ma+">";
ma=a.p.colModel[k].index||a.p.colModel[k].name;
aa+="<div id='jqgh_"+a.p.colModel[k].name+"' "+Da+">"+a.p.colNames[k];
a.p.colModel[k].width=a.p.colModel[k].width?parseInt(a.p.colModel[k].width,10):150;
if(typeof a.p.colModel[k].title!=="boolean"){a.p.colModel[k].title=true
}if(ma==a.p.sortname){a.p.lastsort=k
}aa+=na+"</div></th>"
}aa+="</tr></thead>";
na=null;
b(this).append(aa);
b("thead tr:first th",this).hover(function(){b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
if(this.p.multiselect){var za=[],ua;
b("#cb_"+b.jgrid.jqID(a.p.id),this).bind("click",function(){if(this.checked){b("[id^=jqg_"+a.p.id+"_]").attr("checked",true);
b(a.rows).each(function(d){if(d>0){if(!b(this).hasClass("subgrid")&&!b(this).hasClass("jqgroup")){b(this).addClass("ui-state-highlight").attr("aria-selected","true");
a.p.selarrrow[d]=a.p.selrow=this.id
}}});
ua=true;
za=[]
}else{b("[id^=jqg_"+a.p.id+"_]").attr("checked",false);
b(a.rows).each(function(d){if(!b(this).hasClass("subgrid")){b(this).removeClass("ui-state-highlight").attr("aria-selected","false");
za[d]=this.id
}});
a.p.selarrrow=[];
a.p.selrow=null;
ua=false
}if(b.isFunction(a.p.onSelectAll)){a.p.onSelectAll.call(a,ua?a.p.selarrrow:za,ua)
}})
}if(a.p.autowidth===true){aa=b(Q).innerWidth();
a.p.width=aa>0?aa:"nw"
}(function(){var d=0,g=a.p.cellLayout,h=0,q,o=a.p.scrollOffset,x,w=false,C,D=0,s=0,v=0,y;
if(G){g=0
}b.each(a.p.colModel,function(){if(typeof this.hidden==="undefined"){this.hidden=false
}if(this.hidden===false){d+=J(this.width,0);
if(this.fixed){D+=this.width;
s+=this.width+g
}else{h++
}v++
}});
if(isNaN(a.p.width)){a.p.width=e.width=d
}else{e.width=a.p.width
}a.p.tblwidth=d;
if(a.p.shrinkToFit===false&&a.p.forceFit===true){a.p.forceFit=false
}if(a.p.shrinkToFit===true&&h>0){C=e.width-g*h-s;
if(!isNaN(a.p.height)){C-=o;
w=true
}d=0;
b.each(a.p.colModel,function(E){if(this.hidden===false&&!this.fixed){this.width=x=Math.round(C*this.width/(a.p.tblwidth-D));
d+=x;
q=E
}});
y=0;
if(w){if(e.width-s-(d+g*h)!==o){y=e.width-s-(d+g*h)-o
}}else{if(!w&&Math.abs(e.width-s-(d+g*h))!==1){y=e.width-s-(d+g*h)
}}a.p.colModel[q].width+=y;
a.p.tblwidth=d+y+D+v*g;
if(a.p.tblwidth>a.p.width){a.p.colModel[q].width-=a.p.tblwidth-parseInt(a.p.width,10);
a.p.tblwidth=a.p.width
}}})();
b(Q).css("width",e.width+"px").append("<div class='ui-jqgrid-resize-mark' id='rs_m"+a.p.id+"'>&#160;</div>");
b(r).css("width",e.width+"px");
aa=b("thead:first",a).get(0);
var va="";
if(a.p.footerrow){va+="<table role='grid' style='width:"+a.p.tblwidth+"px' class='ui-jqgrid-ftable' cellspacing='0' cellpadding='0' border='0'><tbody><tr role='row' class='ui-widget-content footrow footrow-"+l+"'>"
}r=b("tr:first",aa);
var wa="<tr class='jqgfirstrow' role='row' style='height:auto'>";
a.p.disableClick=false;
b("th",r).each(function(d){sa=a.p.colModel[d].width;
if(typeof a.p.colModel[d].resizable==="undefined"){a.p.colModel[d].resizable=true
}if(a.p.colModel[d].resizable){qa=document.createElement("span");
b(qa).html("&#160;").addClass("ui-jqgrid-resize ui-jqgrid-resize-"+l);
b.browser.opera||b(qa).css("cursor","col-resize");
b(this).addClass(a.p.resizeclass)
}else{qa=""
}b(this).css("width",sa+"px").prepend(qa);
var g="";
if(a.p.colModel[d].hidden){b(this).css("display","none");
g="display:none;"
}wa+="<td role='gridcell' style='height:0px;width:"+sa+"px;"+g+"'>";
e.headers[d]={width:sa,el:this};
ta=a.p.colModel[d].sortable;
if(typeof ta!=="boolean"){ta=a.p.colModel[d].sortable=true
}g=a.p.colModel[d].name;
g=="cb"||g=="subgrid"||g=="rn"||a.p.viewsortcols[2]&&b("div",this).addClass("ui-jqgrid-sortable");
if(ta){if(a.p.viewsortcols[0]){b("div span.s-ico",this).show();
d==a.p.lastsort&&b("div span.ui-icon-"+a.p.sortorder,this).removeClass("ui-state-disabled")
}else{if(d==a.p.lastsort){b("div span.s-ico",this).show();
b("div span.ui-icon-"+a.p.sortorder,this).removeClass("ui-state-disabled")
}}}if(a.p.footerrow){va+="<td role='gridcell' "+M(d,0,"")+">&#160;</td>"
}}).mousedown(function(d){if(b(d.target).closest("th>span.ui-jqgrid-resize").length==1){var g=b.jgrid.getCellIndex(this);
if(a.p.forceFit===true){a.p.nv=Fa(g)
}e.dragStart(g,d,Ga(g));
return false
}}).click(function(d){if(a.p.disableClick){return a.p.disableClick=false
}var g="th>div.ui-jqgrid-sortable",h,q;
a.p.viewsortcols[2]||(g="th>div>span>span.ui-grid-ico-sort");
d=b(d.target).closest(g);
if(d.length==1){g=b.jgrid.getCellIndex(this);
if(!a.p.viewsortcols[2]){h=true;
q=d.attr("sort")
}Ca(b("div",this)[0].id,g,h,q);
return false
}});
if(a.p.sortable&&b.fn.sortable){try{b(a).jqGrid("sortableColumns",r)
}catch(Ma){}}if(a.p.footerrow){va+="</tr></tbody></table>"
}wa+="</tr>";
this.appendChild(document.createElement("tbody"));
b(this).addClass("ui-jqgrid-btable").append(wa);
wa=null;
r=b("<table class='ui-jqgrid-htable' style='width:"+a.p.tblwidth+"px' role='grid' aria-labelledby='gbox_"+this.id+"' cellspacing='0' cellpadding='0' border='0'></table>").append(aa);
var ea=a.p.caption&&a.p.hiddengrid===true?true:false;
k=b("<div class='ui-jqgrid-hbox"+(l=="rtl"?"-rtl":"")+"'></div>");
aa=null;
e.hDiv=document.createElement("div");
b(e.hDiv).css({width:e.width+"px"}).addClass("ui-state-default ui-jqgrid-hdiv").append(k);
b(k).append(r);
r=null;
ea&&b(e.hDiv).hide();
if(a.p.pager){if(typeof a.p.pager=="string"){if(a.p.pager.substr(0,1)!="#"){a.p.pager="#"+a.p.pager
}}else{a.p.pager="#"+b(a.p.pager).attr("id")
}b(a.p.pager).css({width:e.width+"px"}).appendTo(Q).addClass("ui-state-default ui-jqgrid-pager ui-corner-bottom");
ea&&b(a.p.pager).hide();
u(a.p.pager,"")
}a.p.cellEdit===false&&a.p.hoverrows===true&&b(a).bind("mouseover",function(d){R=b(d.target).closest("tr.jqgrow");
b(R).attr("class")!=="subgrid"&&b(R).addClass("ui-state-hover");
return false
}).bind("mouseout",function(d){R=b(d.target).closest("tr.jqgrow");
b(R).removeClass("ui-state-hover");
return false
});
var ka,la;
b(a).before(e.hDiv).click(function(d){W=d.target;
var g=b(W).hasClass("cbox");
R=b(W,a.rows).closest("tr.jqgrow");
if(b(R).length===0){return this
}var h=true;
if(b.isFunction(a.p.beforeSelectRow)){h=a.p.beforeSelectRow.call(a,R[0].id,d)
}if(W.tagName=="A"||(W.tagName=="INPUT"||W.tagName=="TEXTAREA"||W.tagName=="OPTION"||W.tagName=="SELECT")&&!g){return this
}if(h===true){if(a.p.cellEdit===true){if(a.p.multiselect&&g){b(a).jqGrid("setSelection",R[0].id,true)
}else{ka=R[0].rowIndex;
la=b.jgrid.getCellIndex(W);
try{b(a).jqGrid("editCell",ka,la,true)
}catch(q){}}}else{if(a.p.multikey){if(d[a.p.multikey]){b(a).jqGrid("setSelection",R[0].id,true)
}else{if(a.p.multiselect&&g){g=b("[id^=jqg_"+a.p.id+"_]").attr("checked");
b("[id^=jqg_"+a.p.id+"_]").attr("checked",!g)
}}}else{if(a.p.multiselect&&a.p.multiboxonly){if(!g){b(a.p.selarrrow).each(function(o,x){o=a.rows.namedItem(x);
b(o).removeClass("ui-state-highlight");
b("#jqg_"+a.p.id+"_"+b.jgrid.jqID(x)).attr("checked",false)
});
a.p.selarrrow=[];
b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv).attr("checked",false)
}}b(a).jqGrid("setSelection",R[0].id,true)
}}if(b.isFunction(a.p.onCellSelect)){ka=R[0].id;
la=b.jgrid.getCellIndex(W);
a.p.onCellSelect.call(a,ka,la,b(W).html(),d)
}d.stopPropagation()
}else{return this
}}).bind("reloadGrid",function(d,g){if(a.p.treeGrid===true){a.p.datatype=a.p.treedatatype
}g&&g.current&&a.grid.selectionPreserver(a);
if(a.p.datatype=="local"){b(a).jqGrid("resetSelection");
a.p.data.length&&O()
}else{if(!a.p.treeGrid){a.p.selrow=null;
if(a.p.multiselect){a.p.selarrrow=[];
b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv).attr("checked",false)
}a.p.savedRow=[]
}}a.p.scroll&&Y(a.grid.bDiv,true);
if(g&&g.page){d=g.page;
if(d>a.p.lastpage){d=a.p.lastpage
}if(d<1){d=1
}a.p.page=d;
a.grid.bDiv.scrollTop=a.grid.prevRowHeight?(d-1)*a.grid.prevRowHeight*a.p.rowNum:0
}if(a.grid.prevRowHeight&&a.p.scroll){delete a.p.lastpage;
a.grid.populateVisible()
}else{a.grid.populate()
}return false
});
b.isFunction(this.p.ondblClickRow)&&b(this).dblclick(function(d){W=d.target;
R=b(W,a.rows).closest("tr.jqgrow");
if(b(R).length===0){return false
}ka=R[0].rowIndex;
la=b.jgrid.getCellIndex(W);
a.p.ondblClickRow.call(a,b(R).attr("id"),ka,la,d);
return false
});
b.isFunction(this.p.onRightClickRow)&&b(this).bind("contextmenu",function(d){W=d.target;
R=b(W,a.rows).closest("tr.jqgrow");
if(b(R).length===0){return false
}a.p.multiselect||b(a).jqGrid("setSelection",R[0].id,true);
ka=R[0].rowIndex;
la=b.jgrid.getCellIndex(W);
a.p.onRightClickRow.call(a,b(R).attr("id"),ka,la,d);
return false
});
e.bDiv=document.createElement("div");
b(e.bDiv).append(b('<div style="position:relative;'+(B&&b.browser.version<8?"height:0.01%;":"")+'"></div>').append("<div></div>").append(this)).addClass("ui-jqgrid-bdiv").css({height:a.p.height+(isNaN(a.p.height)?"":"px"),width:e.width+"px"}).scroll(e.scrollGrid);
b("table:first",e.bDiv).css({width:a.p.tblwidth+"px"});
if(B){b("tbody",this).size()==2&&b("tbody:gt(0)",this).remove();
a.p.multikey&&b(e.bDiv).bind("selectstart",function(){return false
})
}else{a.p.multikey&&b(e.bDiv).bind("mousedown",function(){return false
})
}ea&&b(e.bDiv).hide();
e.cDiv=document.createElement("div");
var Aa=a.p.hidegrid===true?b("<a role='link' href='javascript:void(0)'/>").addClass("ui-jqgrid-titlebar-close HeaderButton").hover(function(){Aa.addClass("ui-state-hover")
},function(){Aa.removeClass("ui-state-hover")
}).append("<span class='ui-icon ui-icon-circle-triangle-n'></span>").css(l=="rtl"?"left":"right","0px"):"";
b(e.cDiv).append(Aa).append("<span class='ui-jqgrid-title"+(l=="rtl"?"-rtl":"")+"'>"+a.p.caption+"</span>").addClass("ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix");
b(e.cDiv).insertBefore(e.hDiv);
if(a.p.toolbar[0]){e.uDiv=document.createElement("div");
if(a.p.toolbar[1]=="top"){b(e.uDiv).insertBefore(e.hDiv)
}else{a.p.toolbar[1]=="bottom"&&b(e.uDiv).insertAfter(e.hDiv)
}if(a.p.toolbar[1]=="both"){e.ubDiv=document.createElement("div");
b(e.uDiv).insertBefore(e.hDiv).addClass("ui-userdata ui-state-default").attr("id","t_"+this.id);
b(e.ubDiv).insertAfter(e.hDiv).addClass("ui-userdata ui-state-default").attr("id","tb_"+this.id);
ea&&b(e.ubDiv).hide()
}else{b(e.uDiv).width(e.width).addClass("ui-userdata ui-state-default").attr("id","t_"+this.id)
}ea&&b(e.uDiv).hide()
}if(a.p.toppager){a.p.toppager=a.p.id+"_toppager";
e.topDiv=b("<div id='"+a.p.toppager+"'></div>")[0];
a.p.toppager="#"+a.p.toppager;
b(e.topDiv).insertBefore(e.hDiv).addClass("ui-state-default ui-jqgrid-toppager").width(e.width);
u(a.p.toppager,"_t")
}if(a.p.footerrow){e.sDiv=b("<div class='ui-jqgrid-sdiv'></div>")[0];
k=b("<div class='ui-jqgrid-hbox"+(l=="rtl"?"-rtl":"")+"'></div>");
b(e.sDiv).append(k).insertAfter(e.hDiv).width(e.width);
b(k).append(va);
e.footers=b(".ui-jqgrid-ftable",e.sDiv)[0].rows[0].cells;
if(a.p.rownumbers){e.footers[0].className="ui-state-default jqgrid-rownum"
}ea&&b(e.sDiv).hide()
}k=null;
if(a.p.caption){var Ha=a.p.datatype;
if(a.p.hidegrid===true){b(".ui-jqgrid-titlebar-close",e.cDiv).click(function(d){var g=b.isFunction(a.p.onHeaderClick);
if(a.p.gridstate=="visible"){b(".ui-jqgrid-bdiv, .ui-jqgrid-hdiv","#gview_"+a.p.id).slideUp("fast");
a.p.pager&&b(a.p.pager).slideUp("fast");
a.p.toppager&&b(a.p.toppager).slideUp("fast");
if(a.p.toolbar[0]===true){a.p.toolbar[1]=="both"&&b(e.ubDiv).slideUp("fast");
b(e.uDiv).slideUp("fast")
}a.p.footerrow&&b(".ui-jqgrid-sdiv","#gbox_"+a.p.id).slideUp("fast");
b("span",this).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");
a.p.gridstate="hidden";
b("#gbox_"+a.p.id).hasClass("ui-resizable")&&b(".ui-resizable-handle","#gbox_"+a.p.id).hide();
if(g){ea||a.p.onHeaderClick.call(a,a.p.gridstate,d)
}}else{if(a.p.gridstate=="hidden"){b(".ui-jqgrid-hdiv, .ui-jqgrid-bdiv","#gview_"+a.p.id).slideDown("fast");
a.p.pager&&b(a.p.pager).slideDown("fast");
a.p.toppager&&b(a.p.toppager).slideDown("fast");
if(a.p.toolbar[0]===true){a.p.toolbar[1]=="both"&&b(e.ubDiv).slideDown("fast");
b(e.uDiv).slideDown("fast")
}a.p.footerrow&&b(".ui-jqgrid-sdiv","#gbox_"+a.p.id).slideDown("fast");
b("span",this).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");
if(ea){a.p.datatype=Ha;
ja();
ea=false
}a.p.gridstate="visible";
b("#gbox_"+a.p.id).hasClass("ui-resizable")&&b(".ui-resizable-handle","#gbox_"+a.p.id).show();
g&&a.p.onHeaderClick.call(a,a.p.gridstate,d)
}}return false
});
if(ea){a.p.datatype="local";
b(".ui-jqgrid-titlebar-close",e.cDiv).trigger("click")
}}}else{b(e.cDiv).hide()
}b(e.hDiv).after(e.bDiv).mousemove(function(d){if(e.resizing){e.dragMove(d);
return false
}});
b(".ui-jqgrid-labels",e.hDiv).bind("selectstart",function(){return false
});
b(document).mouseup(function(){if(e.resizing){e.dragEnd();
return false
}return true
});
a.formatCol=M;
a.sortData=Ca;
a.updatepager=function(d,g){var h,q,o,x,w,C,D,s="";
o=parseInt(a.p.page,10)-1;
if(o<0){o=0
}o*=parseInt(a.p.rowNum,10);
w=o+a.p.reccount;
if(a.p.scroll){h=b("tbody:first > tr:gt(0)",a.grid.bDiv);
o=w-h.length;
a.p.reccount=h.length;
if(q=h.outerHeight()||a.grid.prevRowHeight){h=o*q;
q=parseInt(a.p.records,10)*q;
b(">div:first",a.grid.bDiv).css({height:q}).children("div:first").css({height:h,display:h?"":"none"})
}a.grid.bDiv.scrollLeft=a.grid.hDiv.scrollLeft
}s=a.p.pager?a.p.pager:"";
s+=a.p.toppager?s?","+a.p.toppager:a.p.toppager:"";
if(s){D=b.jgrid.formatter.integer||{};
h=J(a.p.page);
q=J(a.p.lastpage);
b(".selbox",s).attr("disabled",false);
if(a.p.pginput===true){b(".ui-pg-input",s).val(a.p.page);
b("#sp_1",s).html(b.fmatter?b.fmatter.util.NumberFormat(a.p.lastpage,D):a.p.lastpage)
}if(a.p.viewrecords){if(a.p.reccount===0){b(".ui-paging-info",s).html(a.p.emptyrecords)
}else{x=o+1;
C=a.p.records;
if(b.fmatter){x=b.fmatter.util.NumberFormat(x,D);
w=b.fmatter.util.NumberFormat(w,D);
C=b.fmatter.util.NumberFormat(C,D)
}b(".ui-paging-info",s).html(b.jgrid.format(a.p.recordtext,x,w,C))
}}if(a.p.pgbuttons===true){if(h<=0){h=q=0
}if(h==1||h===0){b("#first, #prev",a.p.pager).addClass("ui-state-disabled").removeClass("ui-state-hover");
a.p.toppager&&b("#first_t, #prev_t",a.p.toppager).addClass("ui-state-disabled").removeClass("ui-state-hover")
}else{b("#first, #prev",a.p.pager).removeClass("ui-state-disabled");
a.p.toppager&&b("#first_t, #prev_t",a.p.toppager).removeClass("ui-state-disabled")
}if(h==q||h===0){b("#next, #last",a.p.pager).addClass("ui-state-disabled").removeClass("ui-state-hover");
a.p.toppager&&b("#next_t, #last_t",a.p.toppager).addClass("ui-state-disabled").removeClass("ui-state-hover")
}else{b("#next, #last",a.p.pager).removeClass("ui-state-disabled");
a.p.toppager&&b("#next_t, #last_t",a.p.toppager).removeClass("ui-state-disabled")
}}}d===true&&a.p.rownumbers===true&&b("td.jqgrid-rownum",a.rows).each(function(v){b(this).html(o+1+v)
});
g&&a.p.jqgdnd&&b(a).jqGrid("gridDnD","updateDnD");
b.isFunction(a.p.gridComplete)&&a.p.gridComplete.call(a)
};
a.refreshIndex=O;
a.formatter=function(d,g,h,q,o){return m(d,g,h,q,o)
};
b.extend(e,{populate:ja,emptyRows:Y});
this.grid=e;
a.addXmlData=function(d){fa(d,a.grid.bDiv)
};
a.addJSONData=function(d){ca(d,a.grid.bDiv)
};
this.grid.cols=this.rows[0].cells;
ja();
a.p.hiddengrid=false;
b(window).unload(function(){a=null
})
}}}})
};
b.jgrid.extend({getGridParam:function(f){var j=this[0];
if(j&&j.grid){return f?typeof j.p[f]!="undefined"?j.p[f]:null:j.p
}},setGridParam:function(f){return this.each(function(){this.grid&&typeof f==="object"&&b.extend(true,this.p,f)
})
},getDataIDs:function(){var f=[],j=0,i,c=0;
this.each(function(){if((i=this.rows.length)&&i>0){for(;
j<i;
){if(b(this.rows[j]).hasClass("jqgrow")){f[c]=this.rows[j].id;
c++
}j++
}}});
return f
},setSelection:function(f,j){return this.each(function(){function i(l){var a=b(c.grid.bDiv)[0].clientHeight,r=b(c.grid.bDiv)[0].scrollTop,u=c.rows[l].offsetTop;
l=c.rows[l].clientHeight;
if(u+l>=a+r){b(c.grid.bDiv)[0].scrollTop=u-(a+r)+l+r
}else{if(u<a+r){if(u<r){b(c.grid.bDiv)[0].scrollTop=u
}}}}var c=this,e,k;
if(f!==undefined){j=j===false?false:true;
if(e=c.rows.namedItem(f+"")){if(c.p.scrollrows===true){k=c.rows.namedItem(f).rowIndex;
k>=0&&i(k)
}if(c.p.multiselect){c.p.selrow=e.id;
k=b.inArray(c.p.selrow,c.p.selarrrow);
if(k===-1){e.className!=="ui-subgrid"&&b(e).addClass("ui-state-highlight").attr("aria-selected","true");
e=true;
b("#jqg_"+c.p.id+"_"+b.jgrid.jqID(c.p.selrow)).attr("checked",e);
c.p.selarrrow.push(c.p.selrow);
c.p.onSelectRow&&j&&c.p.onSelectRow.call(c,c.p.selrow,e)
}else{e.className!=="ui-subgrid"&&b(e).removeClass("ui-state-highlight").attr("aria-selected","false");
e=false;
b("#jqg_"+c.p.id+"_"+b.jgrid.jqID(c.p.selrow)).attr("checked",e);
c.p.selarrrow.splice(k,1);
c.p.onSelectRow&&j&&c.p.onSelectRow.call(c,c.p.selrow,e);
e=c.p.selarrrow[0];
c.p.selrow=e===undefined?null:e
}}else{if(e.className!=="ui-subgrid"){c.p.selrow&&b(c.rows.namedItem(c.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected","false");
c.p.selrow=e.id;
b(e).addClass("ui-state-highlight").attr("aria-selected","true");
c.p.onSelectRow&&j&&c.p.onSelectRow.call(c,c.p.selrow,true)
}}}}})
},resetSelection:function(){return this.each(function(){var f=this,j;
if(f.p.multiselect){b(f.p.selarrrow).each(function(i,c){j=f.rows.namedItem(c);
b(j).removeClass("ui-state-highlight").attr("aria-selected","false");
b("#jqg_"+f.p.id+"_"+b.jgrid.jqID(c)).attr("checked",false)
});
b("#cb_"+b.jgrid.jqID(f.p.id)).attr("checked",false);
f.p.selarrrow=[]
}else{if(f.p.selrow){b("#"+f.p.id+" tbody:first tr#"+b.jgrid.jqID(f.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected","false");
f.p.selrow=null
}}f.p.savedRow=[]
})
},getRowData:function(f){var j={},i,c=false,e,k=0;
this.each(function(){var l=this,a,r;
if(typeof f=="undefined"){c=true;
i=[];
e=l.rows.length
}else{r=l.rows.namedItem(f);
if(!r){return j
}e=2
}for(;
k<e;
){if(c){r=l.rows[k]
}if(b(r).hasClass("jqgrow")){b("td",r).each(function(u){a=l.p.colModel[u].name;
if(a!=="cb"&&a!=="subgrid"&&a!=="rn"){if(l.p.treeGrid===true&&a==l.p.ExpandColumn){j[a]=b.jgrid.htmlDecode(b("span:first",this).html())
}else{try{j[a]=b.unformat(this,{rowId:r.id,colModel:l.p.colModel[u]},u)
}catch(B){j[a]=b.jgrid.htmlDecode(b(this).html())
}}}});
if(c){i.push(j);
j={}
}}k++
}});
return i?i:j
},delRowData:function(f){var j=false,i,c;
this.each(function(){var e=this;
if(i=e.rows.namedItem(f)){b(i).remove();
e.p.records--;
e.p.reccount--;
e.updatepager(true,false);
j=true;
if(e.p.multiselect){c=b.inArray(f,e.p.selarrrow);
c!=-1&&e.p.selarrrow.splice(c,1)
}if(f==e.p.selrow){e.p.selrow=null
}}else{return false
}if(e.p.datatype=="local"){var k=e.p._index[f];
if(typeof k!="undefined"){e.p.data.splice(k,1);
e.refreshIndex()
}}if(e.p.altRows===true&&j){var l=e.p.altclass;
b(e.rows).each(function(a){a%2==1?b(this).addClass(l):b(this).removeClass(l)
})
}});
return j
},setRowData:function(f,j,i){var c,e=true,k;
this.each(function(){if(!this.grid){return false
}var l=this,a,r,u=typeof i,B={};
r=l.rows.namedItem(f);
if(!r){return false
}if(j){try{b(this.p.colModel).each(function(J){c=this.name;
if(j[c]!==undefined){B[c]=this.formatter&&typeof this.formatter==="string"&&this.formatter=="date"?b.unformat.date(j[c],this):j[c];
a=l.formatter(f,j[c],J,j,"edit");
k=this.title?{title:b.jgrid.stripHtml(a)}:{};
l.p.treeGrid===true&&c==l.p.ExpandColumn?b("td:eq("+J+") > span:first",r).html(a).attr(k):b("td:eq("+J+")",r).html(a).attr(k)
}});
if(l.p.datatype=="local"){var G=l.p._index[f];
if(typeof G!="undefined"){l.p.data[G]=b.extend(true,l.p.data[G],B)
}B=null
}}catch(Q){e=false
}}if(e){if(u==="string"){b(r).addClass(i)
}else{u==="object"&&b(r).css(i)
}}});
return e
},addRowData:function(f,j,i,c){i||(i="last");
var e=false,k,l,a,r,u,B,G,Q,J="",M,n,m,p,A;
if(j){if(b.isArray(j)){M=true;
i="last";
n=f
}else{j=[j];
M=false
}this.each(function(){var t=this,H=j.length;
u=t.p.rownumbers===true?1:0;
a=t.p.multiselect===true?1:0;
r=t.p.subGrid===true?1:0;
if(!M){if(typeof f!="undefined"){f+=""
}else{f=t.p.records+1+"";
if(t.p.keyIndex!==false){n=t.p.colModel[t.p.keyIndex+a+r+u].name;
if(typeof j[0][n]!="undefined"){f=j[0][n]
}}}}m=t.p.altclass;
for(var S=0,Y="",O={},fa=b.isFunction(t.p.afterInsertRow)?true:false;
S<H;
){p=j[S];
l="";
if(M){try{f=p[n]
}catch(ca){f=t.p.records+1+""
}Y=t.p.altRows===true?(t.rows.length-1)%2===0?m:"":""
}if(u){J=t.formatCol(0,1,"");
l+='<td role="gridcell" aria-describedby="'+t.p.id+'_rn" class="ui-state-default jqgrid-rownum" '+J+">0</td>"
}if(a){Q='<input role="checkbox" type="checkbox" id="jqg_'+t.p.id+"_"+f+'" class="cbox"/>';
J=t.formatCol(u,1,"");
l+='<td role="gridcell" aria-describedby="'+t.p.id+'_cb" '+J+">"+Q+"</td>"
}if(r){l+=b(t).jqGrid("addSubGridCell",a+u,1)
}for(G=a+r+u;
G<t.p.colModel.length;
G++){A=t.p.colModel[G];
k=A.name;
O[k]=A.formatter&&typeof A.formatter==="string"&&A.formatter=="date"?b.unformat.date(p[k],A):p[k];
Q=t.formatter(f,b.jgrid.getAccessor(p,k),G,p,"edit");
J=t.formatCol(G,1,Q);
l+='<td role="gridcell" aria-describedby="'+t.p.id+"_"+k+'" '+J+">"+Q+"</td>"
}l='<tr id="'+f+'" role="row" class="ui-widget-content jqgrow ui-row-'+t.p.direction+" "+Y+'">'+l+"</tr>";
if(t.p.subGrid===true){l=b(l)[0];
b(t).jqGrid("addSubGrid",l,a+u)
}if(t.rows.length===0){b("table:first",t.grid.bDiv).append(l)
}else{switch(i){case"last":b(t.rows[t.rows.length-1]).after(l);
break;
case"first":b(t.rows[0]).after(l);
break;
case"after":if(B=t.rows.namedItem(c)){b(t.rows[B.rowIndex+1]).hasClass("ui-subgrid")?b(t.rows[B.rowIndex+1]).after(l):b(B).after(l)
}break;
case"before":if(B=t.rows.namedItem(c)){b(B).before(l);
B=B.rowIndex
}break
}}t.p.records++;
t.p.reccount++;
fa&&t.p.afterInsertRow.call(t,f,p,p);
S++;
if(t.p.datatype=="local"){t.p._index[f]=t.p.data.length;
t.p.data.push(O);
O={}
}}if(t.p.altRows===true&&!M){if(i=="last"){(t.rows.length-1)%2==1&&b(t.rows[t.rows.length-1]).addClass(m)
}else{b(t.rows).each(function(P){P%2==1?b(this).addClass(m):b(this).removeClass(m)
})
}}t.updatepager(true,true);
e=true
})
}return e
},footerData:function(f,j,i){function c(r){for(var u in r){if(r.hasOwnProperty(u)){return false
}}return true
}var e,k=false,l={},a;
if(typeof f=="undefined"){f="get"
}if(typeof i!="boolean"){i=true
}f=f.toLowerCase();
this.each(function(){var r=this,u;
if(!r.grid||!r.p.footerrow){return false
}if(f=="set"){if(c(j)){return false
}}k=true;
b(this.p.colModel).each(function(B){e=this.name;
if(f=="set"){if(j[e]!==undefined){u=i?r.formatter("",j[e],B,j,"edit"):j[e];
a=this.title?{title:b.jgrid.stripHtml(u)}:{};
b("tr.footrow td:eq("+B+")",r.grid.sDiv).html(u).attr(a);
k=true
}}else{if(f=="get"){l[e]=b("tr.footrow td:eq("+B+")",r.grid.sDiv).html()
}}})
});
return f=="get"?l:k
},ShowHideCol:function(f,j){return this.each(function(){var i=this,c=false;
if(i.grid){if(typeof f==="string"){f=[f]
}j=j!="none"?"":"none";
var e=j===""?true:false;
b(this.p.colModel).each(function(k){if(b.inArray(this.name,f)!==-1&&this.hidden===e){b("tr",i.grid.hDiv).each(function(){b("th:eq("+k+")",this).css("display",j)
});
b(i.rows).each(function(l){b("td:eq("+k+")",i.rows[l]).css("display",j)
});
i.p.footerrow&&b("td:eq("+k+")",i.grid.sDiv).css("display",j);
if(j=="none"){i.p.tblwidth-=this.width+i.p.cellLayout
}else{i.p.tblwidth+=this.width
}this.hidden=!e;
c=true
}});
if(c===true){b("table:first",i.grid.hDiv).width(i.p.tblwidth);
b("table:first",i.grid.bDiv).width(i.p.tblwidth);
i.grid.hDiv.scrollLeft=i.grid.bDiv.scrollLeft;
if(i.p.footerrow){b("table:first",i.grid.sDiv).width(i.p.tblwidth);
i.grid.sDiv.scrollLeft=i.grid.bDiv.scrollLeft
}i.p.shrinkToFit===true&&b(i).jqGrid("setGridWidth",i.grid.width-0.001,true)
}}})
},hideCol:function(f){return this.each(function(){b(this).jqGrid("ShowHideCol",f,"none")
})
},showCol:function(f){return this.each(function(){b(this).jqGrid("ShowHideCol",f,"")
})
},remapColumns:function(f,j,i){function c(l){var a;
a=l.length?b.makeArray(l):b.extend({},l);
b.each(f,function(r){l[r]=a[this]
})
}function e(l,a){b(">tr"+(a||""),l).each(function(){var r=this,u=b.makeArray(r.cells);
b.each(f,function(){var B=u[this];
B&&r.appendChild(B)
})
})
}var k=this.get(0);
c(k.p.colModel);
c(k.p.colNames);
c(k.grid.headers);
e(b("thead:first",k.grid.hDiv),i&&":not(.ui-jqgrid-labels)");
j&&e(b("#"+k.p.id+" tbody:first"),".jqgfirstrow, tr.jqgrow, tr.jqfoot");
k.p.footerrow&&e(b("tbody:first",k.grid.sDiv));
if(k.p.remapColumns){if(k.p.remapColumns.length){c(k.p.remapColumns)
}else{k.p.remapColumns=b.makeArray(f)
}}k.p.lastsort=b.inArray(k.p.lastsort,f);
if(k.p.treeGrid){k.p.expColInd=b.inArray(k.p.expColInd,f)
}},setGridWidth:function(f,j){return this.each(function(){if(this.grid){var i=this,c,e=0,k=i.p.cellLayout,l,a=0,r=false,u=i.p.scrollOffset,B,G=0,Q=0,J=0,M;
if(typeof j!="boolean"){j=i.p.shrinkToFit
}if(!isNaN(f)){f=parseInt(f,10);
i.grid.width=i.p.width=f;
b("#gbox_"+i.p.id).css("width",f+"px");
b("#gview_"+i.p.id).css("width",f+"px");
b(i.grid.bDiv).css("width",f+"px");
b(i.grid.hDiv).css("width",f+"px");
i.p.pager&&b(i.p.pager).css("width",f+"px");
i.p.toppager&&b(i.p.toppager).css("width",f+"px");
if(i.p.toolbar[0]===true){b(i.grid.uDiv).css("width",f+"px");
i.p.toolbar[1]=="both"&&b(i.grid.ubDiv).css("width",f+"px")
}i.p.footerrow&&b(i.grid.sDiv).css("width",f+"px");
if(j===false&&i.p.forceFit===true){i.p.forceFit=false
}if(j===true){if(b.browser.safari){k=0
}b.each(i.p.colModel,function(){if(this.hidden===false){e+=parseInt(this.width,10);
if(this.fixed){Q+=this.width;
G+=this.width+k
}else{a++
}J++
}});
if(a!==0){i.p.tblwidth=e;
B=f-k*a-G;
if(!isNaN(i.p.height)){if(b(i.grid.bDiv)[0].clientHeight<b(i.grid.bDiv)[0].scrollHeight||i.rows.length===1){r=true;
B-=u
}}e=0;
var n=i.grid.cols.length>0;
b.each(i.p.colModel,function(m){if(this.hidden===false&&!this.fixed){c=Math.round(B*this.width/(i.p.tblwidth-Q));
if(!(c<0)){this.width=c;
e+=c;
i.grid.headers[m].width=c;
i.grid.headers[m].el.style.width=c+"px";
if(i.p.footerrow){i.grid.footers[m].style.width=c+"px"
}if(n){i.grid.cols[m].style.width=c+"px"
}l=m
}}});
M=0;
if(r){if(f-G-(e+k*a)!==u){M=f-G-(e+k*a)-u
}}else{if(Math.abs(f-G-(e+k*a))!==1){M=f-G-(e+k*a)
}}i.p.colModel[l].width+=M;
i.p.tblwidth=e+M+Q+k*J;
if(i.p.tblwidth>f){r=i.p.tblwidth-parseInt(f,10);
i.p.tblwidth=f;
c=i.p.colModel[l].width-=r
}else{c=i.p.colModel[l].width
}i.grid.headers[l].width=c;
i.grid.headers[l].el.style.width=c+"px";
if(n){i.grid.cols[l].style.width=c+"px"
}b("table:first",i.grid.bDiv).css("width",i.p.tblwidth+"px");
b("table:first",i.grid.hDiv).css("width",i.p.tblwidth+"px");
i.grid.hDiv.scrollLeft=i.grid.bDiv.scrollLeft;
if(i.p.footerrow){i.grid.footers[l].style.width=c+"px";
b("table:first",i.grid.sDiv).css("width",i.p.tblwidth+"px")
}}}}}})
},setGridHeight:function(f){return this.each(function(){var j=this;
if(j.grid){b(j.grid.bDiv).css({height:f+(isNaN(f)?"":"px")});
j.p.height=f;
j.p.scroll&&j.grid.populateVisible()
}})
},setCaption:function(f){return this.each(function(){this.p.caption=f;
b("span.ui-jqgrid-title",this.grid.cDiv).html(f);
b(this.grid.cDiv).show()
})
},setLabel:function(f,j,i,c){return this.each(function(){var e=this,k=-1;
if(e.grid){if(isNaN(f)){b(e.p.colModel).each(function(r){if(this.name==f){k=r;
return false
}})
}else{k=parseInt(f,10)
}if(k>=0){var l=b("tr.ui-jqgrid-labels th:eq("+k+")",e.grid.hDiv);
if(j){var a=b(".s-ico",l);
b("[id^=jqgh_]",l).empty().html(j).append(a);
e.p.colNames[k]=j
}if(i){typeof i==="string"?b(l).addClass(i):b(l).css(i)
}typeof c==="object"&&b(l).attr(c)
}}})
},setCell:function(f,j,i,c,e,k){return this.each(function(){var l=this,a=-1,r,u;
if(l.grid){if(isNaN(j)){b(l.p.colModel).each(function(G){if(this.name==j){a=G;
return false
}})
}else{a=parseInt(j,10)
}if(a>=0){if(r=l.rows.namedItem(f)){var B=b("td:eq("+a+")",r);
if(i!==""||k===true){r=l.formatter(f,i,a,r,"edit");
u=l.p.colModel[a].title?{title:b.jgrid.stripHtml(r)}:{};
l.p.treeGrid&&b(".tree-wrap",b(B)).length>0?b("span",b(B)).html(r).attr(u):b(B).html(r).attr(u);
if(l.p.datatype=="local"){r=l.p.colModel[a];
i=r.formatter&&typeof r.formatter==="string"&&r.formatter=="date"?b.unformat.date(i,r):i;
if(u=l.p._index[f]){l.p.data[u][r.name]=i
}}}if(typeof c==="string"){b(B).addClass(c)
}else{c&&b(B).css(c)
}typeof e==="object"&&b(B).attr(e)
}}}})
},getCell:function(f,j){var i=false;
this.each(function(){var c=this,e=-1;
if(c.grid){if(isNaN(j)){b(c.p.colModel).each(function(a){if(this.name===j){e=a;
return false
}})
}else{e=parseInt(j,10)
}if(e>=0){var k=c.rows.namedItem(f);
if(k){try{i=b.unformat(b("td:eq("+e+")",k),{rowId:k.id,colModel:c.p.colModel[e]},e)
}catch(l){i=b.jgrid.htmlDecode(b("td:eq("+e+")",k).html())
}}}}});
return i
},getCol:function(f,j,i){var c=[],e,k=0;
j=typeof j!="boolean"?false:j;
if(typeof i=="undefined"){i=false
}this.each(function(){var l=this,a=-1;
if(l.grid){if(isNaN(f)){b(l.p.colModel).each(function(G){if(this.name===f){a=G;
return false
}})
}else{a=parseInt(f,10)
}if(a>=0){var r=l.rows.length,u=0;
if(r&&r>0){for(;
u<r;
){if(b(l.rows[u]).hasClass("jqgrow")){try{e=b.unformat(b(l.rows[u].cells[a]),{rowId:l.rows[u].id,colModel:l.p.colModel[a]},a)
}catch(B){e=b.jgrid.htmlDecode(l.rows[u].cells[a].innerHTML)
}if(i){k+=parseFloat(e)
}else{if(j){c.push({id:l.rows[u].id,value:e})
}else{c[u]=e
}}}u++
}if(i){switch(i.toLowerCase()){case"sum":c=k;
break;
case"avg":c=k/r;
break;
case"count":c=r;
break
}}}}}});
return c
},clearGridData:function(f){return this.each(function(){var j=this;
if(j.grid){if(typeof f!="boolean"){f=false
}if(j.p.deepempty){b("#"+j.p.id+" tbody:first tr:gt(0)").remove()
}else{var i=b("#"+j.p.id+" tbody:first tr:first")[0];
b("#"+j.p.id+" tbody:first").empty().append(i)
}j.p.footerrow&&f&&b(".ui-jqgrid-ftable td",j.grid.sDiv).html("&#160;");
j.p.selrow=null;
j.p.selarrrow=[];
j.p.savedRow=[];
j.p.records=0;
j.p.page=1;
j.p.lastpage=0;
j.p.reccount=0;
j.p.data=[];
j.p_index={};
j.updatepager(true,false)
}})
},getInd:function(f,j){var i=false,c;
this.each(function(){if(c=this.rows.namedItem(f)){i=j===true?c:c.rowIndex
}});
return i
}})
})(jQuery);
(function(a){a.fmatter={};
a.extend(a.fmatter,{isBoolean:function(b){return typeof b==="boolean"
},isObject:function(b){return b&&(typeof b==="object"||a.isFunction(b))||false
},isString:function(b){return typeof b==="string"
},isNumber:function(b){return typeof b==="number"&&isFinite(b)
},isNull:function(b){return b===null
},isUndefined:function(b){return typeof b==="undefined"
},isValue:function(b){return this.isObject(b)||this.isString(b)||this.isNumber(b)||this.isBoolean(b)
},isEmpty:function(b){if(!this.isString(b)&&this.isValue(b)){return false
}else{if(!this.isValue(b)){return true
}}b=a.trim(b).replace(/\&nbsp\;/ig,"").replace(/\&#160\;/ig,"");
return b===""
}});
a.fn.fmatter=function(f,c,m,l,j){var k=c;
m=a.extend({},a.jgrid.formatter,m);
if(a.fn.fmatter[f]){k=a.fn.fmatter[f](c,m,l,j)
}return k
};
a.fmatter.util={NumberFormat:function(j,c){a.fmatter.isNumber(j)||(j*=1);
if(a.fmatter.isNumber(j)){var p=j<0,o=j+"",l=c.decimalSeparator?c.decimalSeparator:".";
if(a.fmatter.isNumber(c.decimalPlaces)){var m=c.decimalPlaces;
o=Math.pow(10,m);
o=Math.round(j*o)/o+"";
j=o.lastIndexOf(".");
if(m>0){if(j<0){o+=l;
j=o.length-1
}else{if(l!=="."){o=o.replace(".",l)
}}for(;
o.length-1-j<m;
){o+="0"
}}}if(c.thousandsSeparator){m=c.thousandsSeparator;
j=o.lastIndexOf(l);
j=j>-1?j:o.length;
l=o.substring(j);
for(var n=-1,k=j;
k>0;
k--){n++;
if(n%3===0&&k!==j&&(!p||k>1)){l=m+l
}l=o.charAt(k-1)+l
}o=l
}o=c.prefix?c.prefix+o:o;
return o=c.suffix?o+c.suffix:o
}else{return j
}},DateFormat:function(F,E,D,C){var z=function(b,d){b=String(b);
for(d=parseInt(d,10)||2;
b.length<d;
){b="0"+b
}return b
},A={m:1,d:1,y:1970,h:0,i:0,s:0,u:0},B=0,y,x,w=["i18n"];
w.i18n={dayNames:C.dayNames,monthNames:C.monthNames};
if(F in C.masks){F=C.masks[F]
}if(E.constructor===Number){B=new Date(E)
}else{if(E.constructor===Date){B=E
}else{E=E.split(/[\\\/:_;.,\t\T\s-]/);
F=F.split(/[\\\/:_;.,\t\T\s-]/);
y=0;
for(x=F.length;
y<x;
y++){if(F[y]=="M"){B=a.inArray(E[y],w.i18n.monthNames);
if(B!==-1&&B<12){E[y]=B+1
}}if(F[y]=="F"){B=a.inArray(E[y],w.i18n.monthNames);
if(B!==-1&&B>11){E[y]=B+1-12
}}if(E[y]){A[F[y].toLowerCase()]=parseInt(E[y],10)
}}if(A.f){A.m=A.f
}if(A.m===0&&A.y===0&&A.d===0){return"&#160;"
}A.m=parseInt(A.m,10)-1;
B=A.y;
if(B>=70&&B<=99){A.y=1900+A.y
}else{if(B>=0&&B<=69){A.y=2000+A.y
}}B=new Date(A.y,A.m,A.d,A.h,A.i,A.s,A.u)
}}if(D in C.masks){D=C.masks[D]
}else{D||(D="Y-m-d")
}A=B.getHours();
F=B.getMinutes();
E=B.getDate();
y=B.getMonth()+1;
x=B.getTimezoneOffset();
var v=B.getSeconds(),r=B.getMilliseconds(),u=B.getDay(),m=B.getFullYear(),c=(u+6)%7+1,H=(new Date(m,y-1,E)-new Date(m,0,1))/86400000,G={d:z(E),D:w.i18n.dayNames[u],j:E,l:w.i18n.dayNames[u+7],N:c,S:C.S(E),w:u,z:H,W:c<5?Math.floor((H+c-1)/7)+1:Math.floor((H+c-1)/7)||(((new Date(m-1,0,1)).getDay()+6)%7<4?53:52),F:w.i18n.monthNames[y-1+12],m:z(y),M:w.i18n.monthNames[y-1],n:y,t:"?",L:"?",o:"?",Y:m,y:String(m).substring(2),a:A<12?C.AmPm[0]:C.AmPm[1],A:A<12?C.AmPm[2]:C.AmPm[3],B:"?",g:A%12||12,G:A,h:z(A%12||12),H:z(A),i:z(F),s:z(v),u:r,e:"?",I:"?",O:(x>0?"-":"+")+z(Math.floor(Math.abs(x)/60)*100+Math.abs(x)%60,4),P:"?",T:(String(B).match(/\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g)||[""]).pop().replace(/[^-+\dA-Z]/g,""),Z:"?",c:"?",r:"?",U:Math.floor(B/1000)};
return D.replace(/\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g,function(b){return b in G?G[b]:b.substring(1)
})
}};
a.fn.fmatter.defaultFormat=function(d,c){return a.fmatter.isValue(d)&&d!==""?d:c.defaultValue?c.defaultValue:"&#160;"
};
a.fn.fmatter.email=function(d,c){return a.fmatter.isEmpty(d)?a.fn.fmatter.defaultFormat(d,c):'<a href="mailto:'+d+'">'+d+"</a>"
};
a.fn.fmatter.checkbox=function(e,c){var f=a.extend({},c.checkbox);
a.fmatter.isUndefined(c.colModel.formatoptions)||(f=a.extend({},f,c.colModel.formatoptions));
c=f.disabled===true?"disabled":"";
if(a.fmatter.isEmpty(e)||a.fmatter.isUndefined(e)){e=a.fn.fmatter.defaultFormat(e,f)
}e+="";
e=e.toLowerCase();
return'<input type="checkbox" '+(e.search(/(false|0|no|off)/i)<0?" checked='checked' ":"")+' value="'+e+'" offval="no" '+c+"/>"
};
a.fn.fmatter.link=function(f,c){var h={target:c.target},g="";
a.fmatter.isUndefined(c.colModel.formatoptions)||(h=a.extend({},h,c.colModel.formatoptions));
if(h.target){g="target="+h.target
}return a.fmatter.isEmpty(f)?a.fn.fmatter.defaultFormat(f,c):"<a "+g+' href="'+f+'">'+f+"</a>"
};
a.fn.fmatter.showlink=function(f,c){var h={baseLinkUrl:c.baseLinkUrl,showAction:c.showAction,addParam:c.addParam||"",target:c.target,idName:c.idName},g="";
a.fmatter.isUndefined(c.colModel.formatoptions)||(h=a.extend({},h,c.colModel.formatoptions));
if(h.target){g="target="+h.target
}h=h.baseLinkUrl+h.showAction+"?"+h.idName+"="+c.rowId+h.addParam;
return a.fmatter.isString(f)||a.fmatter.isNumber(f)?"<a "+g+' href="'+h+'">'+f+"</a>":a.fn.fmatter.defaultFormat(f,c)
};
a.fn.fmatter.integer=function(e,c){var f=a.extend({},c.integer);
a.fmatter.isUndefined(c.colModel.formatoptions)||(f=a.extend({},f,c.colModel.formatoptions));
if(a.fmatter.isEmpty(e)){return f.defaultValue
}return a.fmatter.util.NumberFormat(e,f)
};
a.fn.fmatter.number=function(e,c){var f=a.extend({},c.number);
a.fmatter.isUndefined(c.colModel.formatoptions)||(f=a.extend({},f,c.colModel.formatoptions));
if(a.fmatter.isEmpty(e)){return f.defaultValue
}return a.fmatter.util.NumberFormat(e,f)
};
a.fn.fmatter.currency=function(e,c){var f=a.extend({},c.currency);
a.fmatter.isUndefined(c.colModel.formatoptions)||(f=a.extend({},f,c.colModel.formatoptions));
if(a.fmatter.isEmpty(e)){return f.defaultValue
}return a.fmatter.util.NumberFormat(e,f)
};
a.fn.fmatter.date=function(f,c,h,g){h=a.extend({},c.date);
a.fmatter.isUndefined(c.colModel.formatoptions)||(h=a.extend({},h,c.colModel.formatoptions));
return !h.reformatAfterEdit&&g=="edit"?a.fn.fmatter.defaultFormat(f,c):a.fmatter.isEmpty(f)?a.fn.fmatter.defaultFormat(f,c):a.fmatter.util.DateFormat(h.srcformat,f,h.newformat,h)
};
a.fn.fmatter.select=function(t,s){t+="";
var r=false,q=[];
if(a.fmatter.isUndefined(s.colModel.formatoptions)){if(!a.fmatter.isUndefined(s.colModel.editoptions)){r=s.colModel.editoptions.value
}}else{r=s.colModel.formatoptions.value
}if(r){var n=s.colModel.editoptions.multiple===true?true:false,o=[],p;
if(n){o=t.split(",");
o=a.map(o,function(b){return a.trim(b)
})
}if(a.fmatter.isString(r)){for(var m=r.split(";"),l=0,c=0;
c<m.length;
c++){p=m[c].split(":");
if(p.length>2){p[1]=jQuery.map(p,function(b,d){if(d>0){return b
}}).join(":")
}if(n){if(jQuery.inArray(p[0],o)>-1){q[l]=p[1];
l++
}}else{if(a.trim(p[0])==a.trim(t)){q[0]=p[1];
break
}}}}else{if(a.fmatter.isObject(r)){if(n){q=jQuery.map(o,function(b){return r[b]
})
}else{q[0]=r[t]||""
}}}}t=q.join(", ");
return t===""?a.fn.fmatter.defaultFormat(t,s):t
};
a.fn.fmatter.rowactions=function(f,c,h,g){switch(h){case"edit":h=function(){a("tr#"+f+" div.ui-inline-edit, tr#"+f+" div.ui-inline-del","#"+c).show();
a("tr#"+f+" div.ui-inline-save, tr#"+f+" div.ui-inline-cancel","#"+c).hide()
};
a("#"+c).jqGrid("editRow",f,g,null,null,null,{oper:"edit"},h,null,h);
a("tr#"+f+" div.ui-inline-edit, tr#"+f+" div.ui-inline-del","#"+c).hide();
a("tr#"+f+" div.ui-inline-save, tr#"+f+" div.ui-inline-cancel","#"+c).show();
break;
case"save":a("#"+c).jqGrid("saveRow",f,null,null);
a("tr#"+f+" div.ui-inline-edit, tr#"+f+" div.ui-inline-del","#"+c).show();
a("tr#"+f+" div.ui-inline-save, tr#"+f+" div.ui-inline-cancel","#"+c).hide();
break;
case"cancel":a("#"+c).jqGrid("restoreRow",f);
a("tr#"+f+" div.ui-inline-edit, tr#"+f+" div.ui-inline-del","#"+c).show();
a("tr#"+f+" div.ui-inline-save, tr#"+f+" div.ui-inline-cancel","#"+c).hide();
break
}};
a.fn.fmatter.actions=function(f,c){f={keys:false,editbutton:true,delbutton:true};
a.fmatter.isUndefined(c.colModel.formatoptions)||(f=a.extend(f,c.colModel.formatoptions));
var k=c.rowId,j="",g;
if(typeof k=="undefined"||a.fmatter.isEmpty(k)){return""
}if(f.editbutton){g="onclick=$.fn.fmatter.rowactions('"+k+"','"+c.gid+"','edit',"+f.keys+");";
j=j+"<div style='margin-left:8px;'><div title='"+a.jgrid.nav.edittitle+"' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "+g+"><span class='ui-icon ui-icon-pencil'></span></div>"
}if(f.delbutton){g="onclick=jQuery('#"+c.gid+"').jqGrid('delGridRow','"+k+"');";
j=j+"<div title='"+a.jgrid.nav.deltitle+"' style='float:left;margin-left:5px;' class='ui-pg-div ui-inline-del' "+g+"><span class='ui-icon ui-icon-trash'></span></div>"
}g="onclick=$.fn.fmatter.rowactions('"+k+"','"+c.gid+"','save',false);";
j=j+"<div title='"+a.jgrid.edit.bSubmit+"' style='float:left;display:none' class='ui-pg-div ui-inline-save'><span class='ui-icon ui-icon-disk' "+g+"></span></div>";
g="onclick=$.fn.fmatter.rowactions('"+k+"','"+c.gid+"','cancel',false);";
return j=j+"<div title='"+a.jgrid.edit.bCancel+"' style='float:left;display:none;margin-left:5px;' class='ui-pg-div ui-inline-cancel'><span class='ui-icon ui-icon-cancel' "+g+"></span></div></div>"
};
a.unformat=function(r,q,p,o){var l,m=q.colModel.formatter,n=q.colModel.formatoptions||{},k=/([\.\*\_\'\(\)\{\}\+\?\\])/g,c=q.colModel.unformat||a.fn.fmatter[m]&&a.fn.fmatter[m].unformat;
if(typeof c!=="undefined"&&a.isFunction(c)){l=c(a(r).text(),q,r)
}else{if(!a.fmatter.isUndefined(m)&&a.fmatter.isString(m)){l=a.jgrid.formatter||{};
switch(m){case"integer":n=a.extend({},l.integer,n);
q=n.thousandsSeparator.replace(k,"\\$1");
q=new RegExp(q,"g");
l=a(r).text().replace(q,"");
break;
case"number":n=a.extend({},l.number,n);
q=n.thousandsSeparator.replace(k,"\\$1");
q=new RegExp(q,"g");
l=a(r).text().replace(q,"").replace(n.decimalSeparator,".");
break;
case"currency":n=a.extend({},l.currency,n);
q=n.thousandsSeparator.replace(k,"\\$1");
q=new RegExp(q,"g");
l=a(r).text().replace(q,"").replace(n.decimalSeparator,".").replace(n.prefix,"").replace(n.suffix,"");
break;
case"checkbox":n=q.colModel.editoptions?q.colModel.editoptions.value.split(":"):["Yes","No"];
l=a("input",r).attr("checked")?n[0]:n[1];
break;
case"select":l=a.unformat.select(r,q,p,o);
break;
case"actions":return"";
default:l=a(r).text()
}}}return l?l:o===true?a(r).text():a.jgrid.htmlDecode(a(r).html())
};
a.unformat.select=function(r,q,p,o){p=[];
r=a(r).text();
if(o===true){return r
}q=a.extend({},q.colModel.editoptions);
if(q.value){var l=q.value;
q=q.multiple===true?true:false;
o=[];
var m;
if(q){o=r.split(",");
o=a.map(o,function(b){return a.trim(b)
})
}if(a.fmatter.isString(l)){for(var n=l.split(";"),k=0,c=0;
c<n.length;
c++){m=n[c].split(":");
if(m.length>2){m[1]=jQuery.map(m,function(d,b){if(b>0){return d
}}).join(":")
}if(q){if(jQuery.inArray(m[1],o)>-1){p[k]=m[0];
k++
}}else{if(a.trim(m[1])==a.trim(r)){p[0]=m[0];
break
}}}}else{if(a.fmatter.isObject(l)||a.isArray(l)){q||(o[0]=r);
p=jQuery.map(o,function(d){var b;
a.each(l,function(e,f){if(f==d){b=e;
return false
}});
if(typeof b!="undefined"){return b
}})
}}return p.join(", ")
}else{return r||""
}};
a.unformat.date=function(e,c){var f=a.jgrid.formatter.date||{};
a.fmatter.isUndefined(c.formatoptions)||(f=a.extend({},f,c.formatoptions));
return a.fmatter.isEmpty(e)?a.fn.fmatter.defaultFormat(e,c):a.fmatter.util.DateFormat(f.newformat,e,f.srcformat,f)
}
})(jQuery);
(function(b){b.jgrid.extend({getColProp:function(c){var e={},a=this[0];
if(a.grid){a=a.p.colModel;
for(var g=0;
g<a.length;
g++){if(a[g].name==c){e=a[g];
break
}}return e
}},setColProp:function(a,c){return this.each(function(){if(this.grid){if(c){for(var d=this.p.colModel,e=0;
e<d.length;
e++){if(d[e].name==a){b.extend(this.p.colModel[e],c);
break
}}}}})
},sortGrid:function(c,e,a){return this.each(function(){var g=this,d=-1;
if(g.grid){if(!c){c=g.p.sortname
}for(var f=0;
f<g.p.colModel.length;
f++){if(g.p.colModel[f].index==c||g.p.colModel[f].name==c){d=f;
break
}}if(d!=-1){f=g.p.colModel[d].sortable;
if(typeof f!=="boolean"){f=true
}if(typeof e!=="boolean"){e=false
}f&&g.sortData("jqgh_"+c,d,e,a)
}}})
},GridDestroy:function(){return this.each(function(){if(this.grid){this.p.pager&&b(this.p.pager).remove();
var a=this.id;
try{b("#gbox_"+a).remove()
}catch(c){}}})
},GridUnload:function(){return this.each(function(){if(this.grid){var a={id:b(this).attr("id"),cl:b(this).attr("class")};
this.p.pager&&b(this.p.pager).empty().removeClass("ui-state-default ui-jqgrid-pager corner-bottom");
var c=document.createElement("table");
b(c).attr({id:a.id});
c.className=a.cl;
a=this.id;
b(c).removeClass("ui-jqgrid-btable");
if(b(this.p.pager).parents("#gbox_"+a).length===1){b(c).insertBefore("#gbox_"+a).show();
b(this.p.pager).insertBefore("#gbox_"+a)
}else{b(c).insertBefore("#gbox_"+a).show()
}b("#gbox_"+a).remove()
}})
},setGridState:function(a){return this.each(function(){if(this.grid){var c=this;
if(a=="hidden"){b(".ui-jqgrid-bdiv, .ui-jqgrid-hdiv","#gview_"+c.p.id).slideUp("fast");
c.p.pager&&b(c.p.pager).slideUp("fast");
c.p.toppager&&b(c.p.toppager).slideUp("fast");
if(c.p.toolbar[0]===true){c.p.toolbar[1]=="both"&&b(c.grid.ubDiv).slideUp("fast");
b(c.grid.uDiv).slideUp("fast")
}c.p.footerrow&&b(".ui-jqgrid-sdiv","#gbox_"+c.p.id).slideUp("fast");
b(".ui-jqgrid-titlebar-close span",c.grid.cDiv).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");
c.p.gridstate="hidden"
}else{if(a=="visible"){b(".ui-jqgrid-hdiv, .ui-jqgrid-bdiv","#gview_"+c.p.id).slideDown("fast");
c.p.pager&&b(c.p.pager).slideDown("fast");
c.p.toppager&&b(c.p.toppager).slideDown("fast");
if(c.p.toolbar[0]===true){c.p.toolbar[1]=="both"&&b(c.grid.ubDiv).slideDown("fast");
b(c.grid.uDiv).slideDown("fast")
}c.p.footerrow&&b(".ui-jqgrid-sdiv","#gbox_"+c.p.id).slideDown("fast");
b(".ui-jqgrid-titlebar-close span",c.grid.cDiv).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");
c.p.gridstate="visible"
}}}})
},updateGridRows:function(g,h,a){var j,c=false,e;
this.each(function(){var f=this,d,m,n,k;
if(!f.grid){return false
}h||(h="id");
g&&g.length>0&&b(g).each(function(){n=this;
if(m=f.rows.namedItem(n[h])){k=n[h];
if(a===true){if(f.p.jsonReader.repeatitems===true){if(f.p.jsonReader.cell){n=n[f.p.jsonReader.cell]
}for(var l=0;
l<n.length;
l++){d=f.formatter(k,n[l],l,n,"edit");
e=f.p.colModel[l].title?{title:b.jgrid.stripHtml(d)}:{};
f.p.treeGrid===true&&j==f.p.ExpandColumn?b("td:eq("+l+") > span:first",m).html(d).attr(e):b("td:eq("+l+")",m).html(d).attr(e)
}return c=true
}}b(f.p.colModel).each(function(o){j=a===true?this.jsonmap||this.name:this.name;
if(n[j]!==undefined){d=f.formatter(k,n[j],o,n,"edit");
e=this.title?{title:b.jgrid.stripHtml(d)}:{};
f.p.treeGrid===true&&j==f.p.ExpandColumn?b("td:eq("+o+") > span:first",m).html(d).attr(e):b("td:eq("+o+")",m).html(d).attr(e);
c=true
}})
}})
});
return c
},filterGrid:function(a,c){c=b.extend({gridModel:false,gridNames:false,gridToolbar:false,filterModel:[],formtype:"horizontal",autosearch:true,formclass:"filterform",tableclass:"filtertable",buttonclass:"filterbutton",searchButton:"Search",clearButton:"Clear",enableSearch:false,enableClear:false,beforeSearch:null,afterSearch:null,beforeClear:null,afterClear:null,url:"",marksearched:true},c||{});
return this.each(function(){var d=this;
this.p=c;
if(this.p.filterModel.length===0&&this.p.gridModel===false){alert("No filter is set")
}else{if(a){this.p.gridid=a.indexOf("#")!=-1?a:"#"+a;
var p=b(this.p.gridid).jqGrid("getGridParam","colModel");
if(p){if(this.p.gridModel===true){var g=b(this.p.gridid)[0],j;
b.each(p,function(h){var l=[];
this.search=this.search===false?false:true;
j=this.editrules&&this.editrules.searchhidden===true?true:this.hidden===true?false:true;
if(this.search===true&&j===true){l.label=d.p.gridNames===true?g.p.colNames[h]:"";
l.name=this.name;
l.index=this.index||this.name;
l.stype=this.edittype||"text";
if(l.stype!="select"){l.stype="text"
}l.defval=this.defval||"";
l.surl=this.surl||"";
l.sopt=this.editoptions||{};
l.width=this.width;
d.p.filterModel.push(l)
}})
}else{b.each(d.p.filterModel,function(){for(var h=0;
h<p.length;
h++){if(this.name==p[h].name){this.index=p[h].index||this.name;
break
}}if(!this.index){this.index=this.name
}})
}var f=function(){var q={},s=0,h,o=b(d.p.gridid)[0],l;
o.p.searchdata={};
b.isFunction(d.p.beforeSearch)&&d.p.beforeSearch();
b.each(d.p.filterModel,function(){l=this.index;
switch(this.stype){case"select":if(h=b("select[name="+l+"]",d).val()){q[l]=h;
d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).addClass("dirty-cell");
s++
}else{d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).removeClass("dirty-cell");
try{delete o.p.postData[this.index]
}catch(v){}}break;
default:if(h=b("input[name="+l+"]",d).val()){q[l]=h;
d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).addClass("dirty-cell");
s++
}else{d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).removeClass("dirty-cell");
try{delete o.p.postData[this.index]
}catch(t){}}}});
var r=s>0?true:false;
b.extend(o.p.postData,q);
var n;
if(d.p.url){n=b(o).jqGrid("getGridParam","url");
b(o).jqGrid("setGridParam",{url:d.p.url})
}b(o).jqGrid("setGridParam",{search:r}).trigger("reloadGrid",[{page:1}]);
n&&b(o).jqGrid("setGridParam",{url:n});
b.isFunction(d.p.afterSearch)&&d.p.afterSearch()
},e=function(){var q={},s,h=0,o=b(d.p.gridid)[0],l;
b.isFunction(d.p.beforeClear)&&d.p.beforeClear();
b.each(d.p.filterModel,function(){l=this.index;
s=this.defval?this.defval:"";
if(!this.stype){this.stype="text"
}switch(this.stype){case"select":var x;
b("select[name="+l+"] option",d).each(function(t){if(t===0){this.selected=true
}if(b(this).text()==s){this.selected=true;
x=b(this).val();
return false
}});
if(x){q[l]=x;
d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).addClass("dirty-cell");
h++
}else{d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).removeClass("dirty-cell");
try{delete o.p.postData[this.index]
}catch(v){}}break;
case"text":b("input[name="+l+"]",d).val(s);
if(s){q[l]=s;
d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).addClass("dirty-cell");
h++
}else{d.p.marksearched&&b("#jqgh_"+this.name,o.grid.hDiv).removeClass("dirty-cell");
try{delete o.p.postData[this.index]
}catch(w){}}break
}});
var r=h>0?true:false;
b.extend(o.p.postData,q);
var n;
if(d.p.url){n=b(o).jqGrid("getGridParam","url");
b(o).jqGrid("setGridParam",{url:d.p.url})
}b(o).jqGrid("setGridParam",{search:r}).trigger("reloadGrid",[{page:1}]);
n&&b(o).jqGrid("setGridParam",{url:n});
b.isFunction(d.p.afterClear)&&d.p.afterClear()
},k,m=b("<form name='SearchForm' style=display:inline;' class='"+this.p.formclass+"'></form>");
k=b("<table class='"+this.p.tableclass+"' cellspacing='0' cellpading='0' border='0'><tbody></tbody></table>");
b(m).append(k);
(function(){var o=document.createElement("tr"),q,h,n,l;
d.p.formtype=="horizontal"&&b(k).append(o);
b.each(d.p.filterModel,function(A){n=document.createElement("td");
b(n).append("<label for='"+this.name+"'>"+this.label+"</label>");
l=document.createElement("td");
var x=this;
if(!this.stype){this.stype="text"
}switch(this.stype){case"select":if(this.surl){b(l).load(this.surl,function(){x.defval&&b("select",this).val(x.defval);
b("select",this).attr({name:x.index||x.name,id:"sg_"+x.name});
x.sopt&&b("select",this).attr(x.sopt);
d.p.gridToolbar===true&&x.width&&b("select",this).width(x.width);
d.p.autosearch===true&&b("select",this).change(function(){f();
return false
})
})
}else{if(x.sopt.value){var z=x.sopt.value,w=document.createElement("select");
b(w).attr({name:x.index||x.name,id:"sg_"+x.name}).attr(x.sopt);
var y;
if(typeof z==="string"){A=z.split(";");
for(var s=0;
s<A.length;
s++){z=A[s].split(":");
y=document.createElement("option");
y.value=z[0];
y.innerHTML=z[1];
if(z[1]==x.defval){y.selected="selected"
}w.appendChild(y)
}}else{if(typeof z==="object"){for(s in z){if(z.hasOwnProperty(s)){A++;
y=document.createElement("option");
y.value=s;
y.innerHTML=z[s];
if(z[s]==x.defval){y.selected="selected"
}w.appendChild(y)
}}}}d.p.gridToolbar===true&&x.width&&b(w).width(x.width);
b(l).append(w);
d.p.autosearch===true&&b(w).change(function(){f();
return false
})
}}break;
case"text":w=this.defval?this.defval:"";
b(l).append("<input type='text' name='"+(this.index||this.name)+"' id='sg_"+this.name+"' value='"+w+"'/>");
x.sopt&&b("input",l).attr(x.sopt);
if(d.p.gridToolbar===true&&x.width){b.browser.msie?b("input",l).width(x.width-4):b("input",l).width(x.width-2)
}d.p.autosearch===true&&b("input",l).keypress(function(r){if((r.charCode?r.charCode:r.keyCode?r.keyCode:0)==13){f();
return false
}return this
});
break
}if(d.p.formtype=="horizontal"){d.p.gridToolbar===true&&d.p.gridNames===false?b(o).append(l):b(o).append(n).append(l);
b(o).append(l)
}else{q=document.createElement("tr");
b(q).append(n).append(l);
b(k).append(q)
}});
l=document.createElement("td");
if(d.p.enableSearch===true){h="<input type='button' id='sButton' class='"+d.p.buttonclass+"' value='"+d.p.searchButton+"'/>";
b(l).append(h);
b("input#sButton",l).click(function(){f();
return false
})
}if(d.p.enableClear===true){h="<input type='button' id='cButton' class='"+d.p.buttonclass+"' value='"+d.p.clearButton+"'/>";
b(l).append(h);
b("input#cButton",l).click(function(){e();
return false
})
}if(d.p.enableClear===true||d.p.enableSearch===true){if(d.p.formtype=="horizontal"){b(o).append(l)
}else{q=document.createElement("tr");
b(q).append("<td>&#160;</td>").append(l);
b(k).append(q)
}}})();
b(this).append(m);
this.triggerSearch=f;
this.clearSearch=e
}else{alert("Could not get grid colModel")
}}else{alert("No target grid is set!")
}}})
},filterToolbar:function(a){a=b.extend({autosearch:true,searchOnEnter:true,beforeSearch:null,afterSearch:null,beforeClear:null,afterClear:null,searchurl:"",stringResult:false,groupOp:"AND",defaultSearch:"bw"},a||{});
return this.each(function(){function g(j,d){var k=b(j);
k[0]&&jQuery.each(d,function(){this.data!==undefined?k.bind(this.type,this.data,this.fn):k.bind(this.type,this.fn)
})
}var c=this,h=function(){var t={},q=0,j,w,u={};
b.each(c.p.colModel,function(){w=this.index||this.name;
var l=this.searchoptions&&this.searchoptions.sopt?this.searchoptions.sopt[0]:a.defaultSearch;
switch(this.stype){case"select":if(j=b("select[name="+w+"]",c.grid.hDiv).val()){t[w]=j;
u[w]=l;
q++
}else{try{delete c.p.postData[w]
}catch(m){}}break;
case"text":if(j=b("input[name="+w+"]",c.grid.hDiv).val()){t[w]=j;
u[w]=l;
q++
}else{try{delete c.p.postData[w]
}catch(k){}}break
}});
var v=q>0?true:false;
if(a.stringResult===true||c.p.datatype=="local"){var n='{"groupOp":"'+a.groupOp+'","rules":[',s=0;
b.each(t,function(k,l){if(s>0){n+=","
}n+='{"field":"'+k+'",';
n+='"op":"'+u[k]+'",';
n+='"data":"'+l+'"}';
s++
});
n+="]}";
b.extend(c.p.postData,{filters:n})
}else{b.extend(c.p.postData,t)
}var r;
if(c.p.searchurl){r=c.p.url;
b(c).jqGrid("setGridParam",{url:c.p.searchurl})
}var d=false;
if(b.isFunction(a.beforeSearch)){d=a.beforeSearch.call(c)
}d||b(c).jqGrid("setGridParam",{search:v}).trigger("reloadGrid",[{page:1}]);
r&&b(c).jqGrid("setGridParam",{url:r});
b.isFunction(a.afterSearch)&&a.afterSearch()
},e=b("<tr class='ui-search-toolbar' role='rowheader'></tr>"),f;
b.each(c.p.colModel,function(){var r=this,n,d,u,s;
d=b("<th role='columnheader' class='ui-state-default ui-th-column ui-th-"+c.p.direction+"'></th>");
n=b("<div style='width:100%;position:relative;height:100%;padding-right:0.3em;'></div>");
this.hidden===true&&b(d).css("display","none");
this.search=this.search===false?false:true;
if(typeof this.stype=="undefined"){this.stype="text"
}u=b.extend({},this.searchoptions||{});
if(this.search){switch(this.stype){case"select":if(s=this.surl||u.dataUrl){b.ajax(b.extend({url:s,dataType:"html",complete:function(k){if(u.buildSelect!==undefined){(k=u.buildSelect(k))&&b(n).append(k)
}else{b(n).append(k.responseText)
}u.defaultValue&&b("select",n).val(u.defaultValue);
b("select",n).attr({name:r.index||r.name,id:"gs_"+r.name});
u.attr&&b("select",n).attr(u.attr);
b("select",n).css({width:"100%"});
u.dataInit!==undefined&&u.dataInit(b("select",n)[0]);
u.dataEvents!==undefined&&g(b("select",n)[0],u.dataEvents);
a.autosearch===true&&b("select",n).change(function(){h();
return false
});
k=null
}},b.jgrid.ajaxOptions,c.p.ajaxSelectOptions||{}))
}else{var t;
if(r.searchoptions&&r.searchoptions.value){t=r.searchoptions.value
}else{if(r.editoptions&&r.editoptions.value){t=r.editoptions.value
}}if(t){s=document.createElement("select");
s.style.width="100%";
b(s).attr({name:r.index||r.name,id:"gs_"+r.name});
var j,q;
if(typeof t==="string"){t=t.split(";");
for(var p=0;
p<t.length;
p++){j=t[p].split(":");
q=document.createElement("option");
q.value=j[0];
q.innerHTML=j[1];
s.appendChild(q)
}}else{if(typeof t==="object"){for(j in t){if(t.hasOwnProperty(j)){q=document.createElement("option");
q.value=j;
q.innerHTML=t[j];
s.appendChild(q)
}}}}u.defaultValue&&b(s).val(u.defaultValue);
u.attr&&b(s).attr(u.attr);
u.dataInit!==undefined&&u.dataInit(s);
u.dataEvents!==undefined&&g(s,u.dataEvents);
b(n).append(s);
a.autosearch===true&&b(s).change(function(){h();
return false
})
}}break;
case"text":s=u.defaultValue?u.defaultValue:"";
b(n).append("<input type='text' style='width:95%;padding:0px;' name='"+(r.index||r.name)+"' id='gs_"+r.name+"' value='"+s+"'/>");
u.attr&&b("input",n).attr(u.attr);
u.dataInit!==undefined&&u.dataInit(b("input",n)[0]);
u.dataEvents!==undefined&&g(b("input",n)[0],u.dataEvents);
if(a.autosearch===true){a.searchOnEnter?b("input",n).keypress(function(k){if((k.charCode?k.charCode:k.keyCode?k.keyCode:0)==13){h();
return false
}return this
}):b("input",n).keydown(function(k){switch(k.which){case 13:return false;
case 9:case 16:case 37:case 38:case 39:case 40:case 27:break;
default:f&&clearTimeout(f);
f=setTimeout(function(){h()
},500)
}})
}break
}}b(d).append(n);
b(e).append(d)
});
b("table thead",c.grid.hDiv).append(e);
this.triggerToolbar=h;
this.clearToolbar=function(t){var q={},j,w=0,u;
t=typeof t!="boolean"?true:t;
b.each(c.p.colModel,function(){j=this.searchoptions&&this.searchoptions.defaultValue?this.searchoptions.defaultValue:"";
u=this.index||this.name;
switch(this.stype){case"select":var l;
b("select[name="+u+"] option",c.grid.hDiv).each(function(o){if(o===0){this.selected=true
}if(b(this).text()==j){this.selected=true;
l=b(this).val();
return false
}});
if(l){q[u]=l;
w++
}else{try{delete c.p.postData[u]
}catch(m){}}break;
case"text":b("input[name="+u+"]",c.grid.hDiv).val(j);
if(j){q[u]=j;
w++
}else{try{delete c.p.postData[u]
}catch(k){}}break
}});
var v=w>0?true:false;
if(a.stringResult===true||c.p.datatype=="local"){var n='{"groupOp":"'+a.groupOp+'","rules":[',s=0;
b.each(q,function(k,l){if(s>0){n+=","
}n+='{"field":"'+k+'",';
n+='"op":"eq",';
n+='"data":"'+l+'"}';
s++
});
n+="]}";
b.extend(c.p.postData,{filters:n})
}else{b.extend(c.p.postData,q)
}var r;
if(c.p.searchurl){r=c.p.url;
b(c).jqGrid("setGridParam",{url:c.p.searchurl})
}var d=false;
if(b.isFunction(a.beforeClear)){d=a.beforeClear.call(c)
}d||t&&b(c).jqGrid("setGridParam",{search:v}).trigger("reloadGrid",[{page:1}]);
r&&b(c).jqGrid("setGridParam",{url:r});
b.isFunction(a.afterClear)&&a.afterClear()
};
this.toggleToolbar=function(){var d=b("tr.ui-search-toolbar",c.grid.hDiv);
d.css("display")=="none"?d.show():d.hide()
}
})
}})
})(jQuery);
var showModal=function(b){b.w.show()
},closeModal=function(b){b.w.hide().attr("aria-hidden","true");
b.o&&b.o.remove()
},hideModal=function(f,d){d=jQuery.extend({jqm:true,gb:""},d||{});
if(d.onClose){var h=d.onClose(f);
if(typeof h=="boolean"&&!h){return
}}if(jQuery.fn.jqm&&d.jqm===true){jQuery(f).attr("aria-hidden","true").jqmHide()
}else{if(d.gb!==""){try{jQuery(".jqgrid-overlay:first",d.gb).hide()
}catch(g){}}jQuery(f).hide().attr("aria-hidden","true")
}};
function findPos(e){var d=0,f=0;
if(e.offsetParent){do{d+=e.offsetLeft;
f+=e.offsetTop
}while(e=e.offsetParent)
}return[d,f]
}var createModal=function(z,y,x,v,u,t){var w=document.createElement("div"),s;
s=jQuery(x.gbox).attr("dir")=="rtl"?true:false;
w.className="ui-widget ui-widget-content ui-corner-all ui-jqdialog";
w.id=z.themodal;
var r=document.createElement("div");
r.className="ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix";
r.id=z.modalhead;
jQuery(r).append("<span class='ui-jqdialog-title'>"+x.caption+"</span>");
var p=jQuery("<a href='javascript:void(0)' class='ui-jqdialog-titlebar-close ui-corner-all'></a>").hover(function(){p.addClass("ui-state-hover")
},function(){p.removeClass("ui-state-hover")
}).append("<span class='ui-icon ui-icon-closethick'></span>");
jQuery(r).append(p);
if(s){w.dir="rtl";
jQuery(".ui-jqdialog-title",r).css("float","right");
jQuery(".ui-jqdialog-titlebar-close",r).css("left","0.3em")
}else{w.dir="ltr";
jQuery(".ui-jqdialog-title",r).css("float","left");
jQuery(".ui-jqdialog-titlebar-close",r).css("right","0.3em")
}var n=document.createElement("div");
jQuery(n).addClass("ui-jqdialog-content ui-widget-content").attr("id",z.modalcontent);
jQuery(n).append(y);
w.appendChild(n);
jQuery(w).prepend(r);
t===true?jQuery("body").append(w):jQuery(w).insertBefore(v);
if(typeof x.jqModal==="undefined"){x.jqModal=true
}y={};
if(jQuery.fn.jqm&&x.jqModal===true){if(x.left===0&&x.top===0){v=[];
v=findPos(u);
x.left=v[0]+4;
x.top=v[1]+4
}y.top=x.top+"px";
y.left=x.left
}else{if(x.left!==0||x.top!==0){y.left=x.left;
y.top=x.top+"px"
}}jQuery("a.ui-jqdialog-titlebar-close",r).click(function(){var b=jQuery("#"+z.themodal).data("onClose")||x.onClose,a=jQuery("#"+z.themodal).data("gbox")||x.gbox;
hideModal("#"+z.themodal,{gb:a,jqm:x.jqModal,onClose:b});
return false
});
if(x.width===0||!x.width){x.width=300
}if(x.height===0||!x.height){x.height=200
}if(!x.zIndex){x.zIndex=950
}u=0;
if(s&&y.left&&!t){u=jQuery(x.gbox).width()-(!isNaN(x.width)?parseInt(x.width,10):0)-8;
y.left=parseInt(y.left,10)+parseInt(u,10)
}if(y.left){y.left+="px"
}jQuery(w).css(jQuery.extend({width:isNaN(x.width)?"auto":x.width+"px",height:isNaN(x.height)?"auto":x.height+"px",zIndex:x.zIndex,overflow:"hidden"},y)).attr({tabIndex:"-1",role:"dialog","aria-labelledby":z.modalhead,"aria-hidden":"true"});
if(typeof x.drag=="undefined"){x.drag=true
}if(typeof x.resize=="undefined"){x.resize=true
}if(x.drag){jQuery(r).css("cursor","move");
if(jQuery.fn.jqDrag){jQuery(w).jqDrag(r)
}else{try{jQuery(w).draggable({handle:jQuery("#"+r.id)})
}catch(k){}}}if(x.resize){if(jQuery.fn.jqResize){jQuery(w).append("<div class='jqResize ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se'></div>");
jQuery("#"+z.themodal).jqResize(".jqResize",z.scrollelm?"#"+z.scrollelm:false)
}else{try{jQuery(w).resizable({handles:"se, sw",alsoResize:z.scrollelm?"#"+z.scrollelm:false})
}catch(m){}}}x.closeOnEscape===true&&jQuery(w).keydown(function(a){if(a.which==27){a=jQuery("#"+z.themodal).data("onClose")||x.onClose;
hideModal(this,{gb:x.gbox,jqm:x.jqModal,onClose:a})
}})
},viewModal=function(e,d){d=jQuery.extend({toTop:true,overlay:10,modal:false,onShow:showModal,onHide:closeModal,gbox:"",jqm:true,jqM:true},d||{});
if(jQuery.fn.jqm&&d.jqm===true){d.jqM?jQuery(e).attr("aria-hidden","false").jqm(d).jqmShow():jQuery(e).attr("aria-hidden","false").jqmShow()
}else{if(d.gbox!==""){jQuery(".jqgrid-overlay:first",d.gbox).show();
jQuery(e).data("gbox",d.gbox)
}jQuery(e).show().attr("aria-hidden","false");
try{jQuery(":input:visible",e)[0].focus()
}catch(f){}}};
function info_dialog(r,q,p,n){var m={width:290,height:"auto",dataheight:"auto",drag:true,resize:false,caption:"<b>"+r+"</b>",left:250,top:170,zIndex:1000,jqModal:true,modal:false,closeOnEscape:true,align:"center",buttonalign:"center",buttons:[]};
jQuery.extend(m,n||{});
var l=m.jqModal;
if(jQuery.fn.jqm&&!l){l=false
}r="";
if(m.buttons.length>0){for(n=0;
n<m.buttons.length;
n++){if(typeof m.buttons[n].id=="undefined"){m.buttons[n].id="info_button_"+n
}r+="<a href='javascript:void(0)' id='"+m.buttons[n].id+"' class='fm-button ui-state-default ui-corner-all'>"+m.buttons[n].text+"</a>"
}}n=isNaN(m.dataheight)?m.dataheight:m.dataheight+"px";
var o="<div id='info_id'>";
o+="<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:"+n+";"+("text-align:"+m.align+";")+"'>"+q+"</div>";
o+=p?"<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+m.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'><a href='javascript:void(0)' id='closedialog' class='fm-button ui-state-default ui-corner-all'>"+p+"</a>"+r+"</div>":r!==""?"<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+m.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'>"+r+"</div>":"";
o+="</div>";
try{jQuery("#info_dialog").attr("aria-hidden")=="false"&&hideModal("#info_dialog",{jqm:l});
jQuery("#info_dialog").remove()
}catch(k){}createModal({themodal:"info_dialog",modalhead:"info_head",modalcontent:"info_content",scrollelm:"infocnt"},o,m,"","",true);
r&&jQuery.each(m.buttons,function(a){jQuery("#"+this.id,"#info_id").bind("click",function(){m.buttons[a].onClick.call(jQuery("#info_dialog"));
return false
})
});
jQuery("#closedialog","#info_id").click(function(a){hideModal("#info_dialog",{jqm:l});
return false
});
jQuery(".fm-button","#info_dialog").hover(function(){jQuery(this).addClass("ui-state-hover")
},function(){jQuery(this).removeClass("ui-state-hover")
});
jQuery.isFunction(m.beforeOpen)&&m.beforeOpen();
viewModal("#info_dialog",{onHide:function(a){a.w.hide().remove();
a.o&&a.o.remove()
},modal:m.modal,jqm:l});
jQuery.isFunction(m.afterOpen)&&m.afterOpen();
try{jQuery("#info_dialog").focus()
}catch(j){}}function createEl(B,A,z,x,w){function v(b,a){if(jQuery.isFunction(a.dataInit)){b.id=a.id;
a.dataInit(b);
delete a.id;
delete a.dataInit
}if(a.dataEvents){jQuery.each(a.dataEvents,function(){this.data!==undefined?jQuery(b).bind(this.type,this.data,this.fn):jQuery(b).bind(this.type,this.fn)
});
delete a.dataEvents
}return a
}var y="";
A.defaultValue&&delete A.defaultValue;
switch(B){case"textarea":y=document.createElement("textarea");
if(x){A.cols||jQuery(y).css({width:"98%"})
}else{if(!A.cols){A.cols=20
}}if(!A.rows){A.rows=2
}if(z=="&nbsp;"||z=="&#160;"||z.length==1&&z.charCodeAt(0)==160){z=""
}y.value=z;
A=v(y,A);
jQuery(y).attr(A).attr({role:"textbox",multiline:"true"});
break;
case"checkbox":y=document.createElement("input");
y.type="checkbox";
if(A.value){var u=A.value.split(":");
if(z===u[0]){y.checked=true;
y.defaultChecked=true
}y.value=u[0];
jQuery(y).attr("offval",u[1]);
try{delete A.value
}catch(t){}}else{u=z.toLowerCase();
if(u.search(/(false|0|no|off|undefined)/i)<0&&u!==""){y.checked=true;
y.defaultChecked=true;
y.value=z
}else{y.value="on"
}jQuery(y).attr("offval","off")
}A=v(y,A);
jQuery(y).attr(A).attr("role","checkbox");
break;
case"select":y=document.createElement("select");
y.setAttribute("role","select");
var s,r=[];
if(A.multiple===true){s=true;
y.multiple="multiple";
jQuery(y).attr("aria-multiselectable","true")
}else{s=false
}if(typeof A.dataUrl!="undefined"){jQuery.ajax(jQuery.extend({url:A.dataUrl,type:"GET",dataType:"html",success:function(b,a){try{delete A.dataUrl;
delete A.value
}catch(c){}if(typeof A.buildSelect!="undefined"){b=A.buildSelect(b);
b=jQuery(b).html();
delete A.buildSelect
}else{b=jQuery(b).html()
}if(b){jQuery(y).append(b);
A=v(y,A);
if(typeof A.size==="undefined"){A.size=s?3:1
}if(s){r=z.split(",");
r=jQuery.map(r,function(d){return jQuery.trim(d)
})
}else{r[0]=jQuery.trim(z)
}jQuery(y).attr(A);
setTimeout(function(){jQuery("option",y).each(function(d){if(d===0){this.selected=""
}jQuery(this).attr("role","option");
if(jQuery.inArray(jQuery.trim(jQuery(this).text()),r)>-1||jQuery.inArray(jQuery.trim(jQuery(this).val()),r)>-1){this.selected="selected";
if(!s){return false
}}})
},0)
}}},w||{}))
}else{if(A.value){if(s){r=z.split(",");
r=jQuery.map(r,function(a){return jQuery.trim(a)
});
if(typeof A.size==="undefined"){A.size=3
}}else{A.size=1
}if(typeof A.value==="function"){A.value=A.value()
}if(typeof A.value==="string"){x=A.value.split(";");
for(u=0;
u<x.length;
u++){w=x[u].split(":");
if(w.length>2){w[1]=jQuery.map(w,function(b,a){if(a>0){return b
}}).join(":")
}B=document.createElement("option");
B.setAttribute("role","option");
B.value=w[0];
B.innerHTML=w[1];
if(!s&&(jQuery.trim(w[0])==jQuery.trim(z)||jQuery.trim(w[1])==jQuery.trim(z))){B.selected="selected"
}if(s&&(jQuery.inArray(jQuery.trim(w[1]),r)>-1||jQuery.inArray(jQuery.trim(w[0]),r)>-1)){B.selected="selected"
}y.appendChild(B)
}}else{if(typeof A.value==="object"){x=A.value;
for(u in x){if(x.hasOwnProperty(u)){B=document.createElement("option");
B.setAttribute("role","option");
B.value=u;
B.innerHTML=x[u];
if(!s&&(jQuery.trim(u)==jQuery.trim(z)||jQuery.trim(x[u])==jQuery.trim(z))){B.selected="selected"
}if(s&&(jQuery.inArray(jQuery.trim(x[u]),r)>-1||jQuery.inArray(jQuery.trim(u),r)>-1)){B.selected="selected"
}y.appendChild(B)
}}}}A=v(y,A);
try{delete A.value
}catch(k){}jQuery(y).attr(A)
}}break;
case"text":case"password":case"button":u=B=="button"?"button":"textbox";
y=document.createElement("input");
y.type=B;
y.value=z;
A=v(y,A);
if(B!="button"){if(x){A.size||jQuery(y).css({width:"98%"})
}else{if(!A.size){A.size=20
}}}jQuery(y).attr(A).attr("role",u);
break;
case"image":case"file":y=document.createElement("input");
y.type=B;
A=v(y,A);
jQuery(y).attr(A);
break;
case"custom":y=document.createElement("span");
try{if(jQuery.isFunction(A.custom_element)){var m=A.custom_element.call(this,z,A);
if(m){m=jQuery(m).addClass("customelement").attr({id:A.id,name:A.name});
jQuery(y).empty().append(m)
}else{throw"e2"
}}else{throw"e1"
}}catch(p){p=="e1"&&info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_element' "+jQuery.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose);
p=="e2"?info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_element' "+jQuery.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):info_dialog(jQuery.jgrid.errors.errcap,typeof p==="string"?p:p.message,jQuery.jgrid.edit.bClose)
}break
}return y
}function daysInFebruary(b){return b%4===0&&(b%100!==0||b%400===0)?29:28
}function DaysArray(d){for(var c=1;
c<=d;
c++){this[c]=31;
if(c==4||c==6||c==9||c==11){this[c]=30
}if(c==2){this[c]=29
}}return this
}function checkDate(r,q){var p={},n;
r=r.toLowerCase();
n=r.indexOf("/")!=-1?"/":r.indexOf("-")!=-1?"-":r.indexOf(".")!=-1?".":"/";
r=r.split(n);
q=q.split(n);
if(q.length!=3){return false
}n=-1;
for(var m,l=-1,o=-1,k=0;
k<r.length;
k++){m=isNaN(q[k])?0:parseInt(q[k],10);
p[r[k]]=m;
m=r[k];
if(m.indexOf("y")!=-1){n=k
}if(m.indexOf("m")!=-1){o=k
}if(m.indexOf("d")!=-1){l=k
}}m=r[n]=="y"||r[n]=="yyyy"?4:r[n]=="yy"?2:-1;
k=DaysArray(12);
var j;
if(n===-1){return false
}else{j=p[r[n]].toString();
if(m==2&&j.length==1){m=1
}if(j.length!=m||p[r[n]]===0&&q[n]!="00"){return false
}}if(o===-1){return false
}else{j=p[r[o]].toString();
if(j.length<1||p[r[o]]<1||p[r[o]]>12){return false
}}if(l===-1){return false
}else{j=p[r[l]].toString();
if(j.length<1||p[r[l]]<1||p[r[l]]>31||p[r[o]]==2&&p[r[l]]>daysInFebruary(p[r[n]])||p[r[l]]>k[p[r[o]]]){return false
}}return true
}function isEmpty(b){return b.match(/^\s+$/)||b===""?true:false
}function checkTime(d){var c=/^(\d{1,2}):(\d{2})([ap]m)?$/;
if(!isEmpty(d)){if(d=d.match(c)){if(d[3]){if(d[1]<1||d[1]>12){return false
}}else{if(d[1]>23){return false
}}if(d[2]>59){return false
}}else{return false
}}return true
}function checkValues(k,j,q){var o,n,m,p;
if(typeof j=="string"){n=0;
for(p=q.p.colModel.length;
n<p;
n++){if(q.p.colModel[n].name==j){o=q.p.colModel[n].editrules;
j=n;
try{m=q.p.colModel[n].formoptions.label
}catch(l){}break
}}}else{if(j>=0){o=q.p.colModel[j].editrules
}}if(o){m||(m=q.p.colNames[j]);
if(o.required===true){if(isEmpty(k)){return[false,m+": "+jQuery.jgrid.edit.msg.required,""]
}}n=o.required===false?false:true;
if(o.number===true){if(!(n===false&&isEmpty(k))){if(isNaN(k)){return[false,m+": "+jQuery.jgrid.edit.msg.number,""]
}}}if(typeof o.minValue!="undefined"&&!isNaN(o.minValue)){if(parseFloat(k)<parseFloat(o.minValue)){return[false,m+": "+jQuery.jgrid.edit.msg.minValue+" "+o.minValue,""]
}}if(typeof o.maxValue!="undefined"&&!isNaN(o.maxValue)){if(parseFloat(k)>parseFloat(o.maxValue)){return[false,m+": "+jQuery.jgrid.edit.msg.maxValue+" "+o.maxValue,""]
}}if(o.email===true){if(!(n===false&&isEmpty(k))){p=/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
if(!p.test(k)){return[false,m+": "+jQuery.jgrid.edit.msg.email,""]
}}}if(o.integer===true){if(!(n===false&&isEmpty(k))){if(isNaN(k)){return[false,m+": "+jQuery.jgrid.edit.msg.integer,""]
}if(k%1!==0||k.indexOf(".")!=-1){return[false,m+": "+jQuery.jgrid.edit.msg.integer,""]
}}}if(o.date===true){if(!(n===false&&isEmpty(k))){j=q.p.colModel[j].formatoptions&&q.p.colModel[j].formatoptions.newformat?q.p.colModel[j].formatoptions.newformat:q.p.colModel[j].datefmt||"Y-m-d";
if(!checkDate(j,k)){return[false,m+": "+jQuery.jgrid.edit.msg.date+" - "+j,""]
}}}if(o.time===true){if(!(n===false&&isEmpty(k))){if(!checkTime(k)){return[false,m+": "+jQuery.jgrid.edit.msg.date+" - hh:mm (am/pm)",""]
}}}if(o.url===true){if(!(n===false&&isEmpty(k))){p=/^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
if(!p.test(k)){return[false,m+": "+jQuery.jgrid.edit.msg.url,""]
}}}if(o.custom===true){if(!(n===false&&isEmpty(k))){if(jQuery.isFunction(o.custom_func)){k=o.custom_func.call(q,k,m);
return jQuery.isArray(k)?k:[false,jQuery.jgrid.edit.msg.customarray,""]
}else{return[false,jQuery.jgrid.edit.msg.customfcheck,""]
}}}}return[true,"",""]
}(function(b){var c=null;
b.jgrid.extend({searchGrid:function(a){a=b.extend({recreateFilter:false,drag:true,sField:"searchField",sValue:"searchString",sOper:"searchOper",sFilter:"filters",loadDefaults:true,beforeShowSearch:null,afterShowSearch:null,onInitializeSearch:null,closeAfterSearch:false,closeAfterReset:false,closeOnEscape:false,multipleSearch:false,cloneSearchRowOnAdd:true,sopt:null,stringResult:undefined,onClose:null,useDataProxy:false,overlay:true},b.jgrid.search,a||{});
return this.each(function(){function z(h,l){l=h.p.postData[l.sFilter];
if(typeof l=="string"){l=b.jgrid.parse(l)
}if(l){l.groupOp&&h.SearchFilter.setGroupOp(l.groupOp);
if(l.rules){var g,k=0,j=l.rules.length;
for(g=false;
k<j;
k++){g=l.rules[k];
if(g.field!==undefined&&g.op!==undefined&&g.data!==undefined){(g=h.SearchFilter.setFilter({sfref:h.SearchFilter.$.find(".sf:last"),filter:b.extend({},g)}))&&h.SearchFilter.add()
}}}}}function f(g){if(a.onClose){var h=a.onClose(g);
if(typeof h=="boolean"&&!h){return
}}g.hide();
a.overlay===true&&b(".jqgrid-overlay:first","#gbox_"+A.p.id).hide()
}function d(){var h=b(".ui-searchFilter").length;
if(h>1){var j=b("#"+t).css("zIndex");
b("#"+t).css({zIndex:parseInt(j,10)+h})
}b("#"+t).show();
a.overlay===true&&b(".jqgrid-overlay:first","#gbox_"+A.p.id).show();
try{b(":input:visible","#"+t)[0].focus()
}catch(g){}}function D(h){var k=h!==undefined,g=b("#"+A.p.id),j={};
if(a.multipleSearch===false){j[a.sField]=h.rules[0].field;
j[a.sValue]=h.rules[0].data;
j[a.sOper]=h.rules[0].op
}else{j[a.sFilter]=h
}g[0].p.search=k;
b.extend(g[0].p.postData,j);
g.trigger("reloadGrid",[{page:1}]);
a.closeAfterSearch&&f(b("#"+t))
}function e(h){h=h&&h.hasOwnProperty("reload")?h.reload:true;
var j=b("#"+A.p.id),g={};
j[0].p.search=false;
if(a.multipleSearch===false){g[a.sField]=g[a.sValue]=g[a.sOper]=""
}else{g[a.sFilter]=""
}b.extend(j[0].p.postData,g);
h&&j.trigger("reloadGrid",[{page:1}]);
a.closeAfterReset&&f(b("#"+t))
}var A=this;
if(A.grid){var t="fbox_"+A.p.id;
if(b.fn.searchFilter){a.recreateFilter===true&&b("#"+t).remove();
if(b("#"+t).html()!=null){b.isFunction(a.beforeShowSearch)&&a.beforeShowSearch(b("#"+t));
d();
b.isFunction(a.afterShowSearch)&&a.afterShowSearch(b("#"+t))
}else{var m=[],G=b("#"+A.p.id).jqGrid("getGridParam","colNames"),y=b("#"+A.p.id).jqGrid("getGridParam","colModel"),q=["eq","ne","lt","le","gt","ge","bw","bn","in","ni","ew","en","cn","nc"],x,r,p,F=[];
if(a.sopt!==null){for(x=p=0;
x<a.sopt.length;
x++){if((r=b.inArray(a.sopt[x],q))!=-1){F[p]={op:a.sopt[x],text:a.odata[r]};
p++
}}}else{for(x=0;
x<q.length;
x++){F[x]={op:q[x],text:a.odata[x]}
}}b.each(y,function(h,l){var g=typeof l.search==="undefined"?true:l.search,k=l.hidden===true;
h=b.extend({},{text:G[h],itemval:l.index||l.name},this.searchoptions);
l=h.searchhidden===true;
if(typeof h.sopt!=="undefined"){p=0;
h.ops=[];
if(h.sopt.length>0){for(x=0;
x<h.sopt.length;
x++){if((r=b.inArray(h.sopt[x],q))!=-1){h.ops[p]={op:h.sopt[x],text:a.odata[r]};
p++
}}}}if(typeof this.stype==="undefined"){this.stype="text"
}if(this.stype=="select"){if(h.dataUrl===undefined){var j;
if(h.value){j=h.value
}else{if(this.editoptions){j=this.editoptions.value
}}if(j){h.dataValues=[];
if(typeof j==="string"){j=j.split(";");
var n;
for(x=0;
x<j.length;
x++){n=j[x].split(":");
h.dataValues[x]={value:n[0],text:n[1]}
}}else{if(typeof j==="object"){x=0;
for(n in j){if(j.hasOwnProperty(n)){h.dataValues[x]={value:n,text:j[n]};
x++
}}}}}}}if(l&&g||g&&!k){m.push(h)
}});
if(m.length>0){b("<div id='"+t+"' role='dialog' tabindex='-1'></div>").insertBefore("#gview_"+A.p.id);
if(a.stringResult===undefined){a.stringResult=a.multipleSearch
}A.SearchFilter=b("#"+t).searchFilter(m,{groupOps:a.groupOps,operators:F,onClose:f,resetText:a.Reset,searchText:a.Find,windowTitle:a.caption,rulesText:a.rulesText,matchText:a.matchText,onSearch:D,onReset:e,stringResult:a.stringResult,ajaxSelectOptions:b.extend({},b.jgrid.ajaxOptions,A.p.ajaxSelectOptions||{}),clone:a.cloneSearchRowOnAdd});
b(".ui-widget-overlay","#"+t).remove();
A.p.direction=="rtl"&&b(".ui-closer","#"+t).css("float","left");
if(a.drag===true){b("#"+t+" table thead tr:first td:first").css("cursor","move");
if(jQuery.fn.jqDrag){b("#"+t).jqDrag(b("#"+t+" table thead tr:first td:first"))
}else{try{b("#"+t).draggable({handle:b("#"+t+" table thead tr:first td:first")})
}catch(u){}}}if(a.multipleSearch===false){b(".ui-del, .ui-add, .ui-del, .ui-add-last, .matchText, .rulesText","#"+t).hide();
b("select[name='groupOp']","#"+t).hide()
}a.multipleSearch===true&&a.loadDefaults===true&&z(A,a);
b.isFunction(a.onInitializeSearch)&&a.onInitializeSearch(b("#"+t));
b.isFunction(a.beforeShowSearch)&&a.beforeShowSearch(b("#"+t));
d();
b.isFunction(a.afterShowSearch)&&a.afterShowSearch(b("#"+t));
a.closeOnEscape===true&&b("#"+t).keydown(function(g){g.which==27&&f(b("#"+t))
})
}}}}})
},editGridRow:function(d,a){c=a=b.extend({top:0,left:0,width:300,height:"auto",dataheight:"auto",modal:false,drag:true,resize:true,url:null,mtype:"POST",clearAfterAdd:true,closeAfterEdit:false,reloadAfterSubmit:true,onInitializeForm:null,beforeInitData:null,beforeShowForm:null,afterShowForm:null,beforeSubmit:null,afterSubmit:null,onclickSubmit:null,afterComplete:null,onclickPgButtons:null,afterclickPgButtons:null,editData:{},recreateForm:false,jqModal:true,closeOnEscape:false,addedrow:"first",topinfo:"",bottominfo:"",saveicon:[],closeicon:[],savekey:[false,13],navkeys:[false,38,40],checkOnSubmit:false,checkOnUpdate:false,_savedData:{},processing:false,onClose:null,ajaxEditOptions:{},serializeEditData:null,viewPagerButtons:true},b.jgrid.edit,a||{});
return this.each(function(){function af(){b(".FormElement","#"+ak).each(function(){var j=b(".customelement",this);
if(j.length){var h=b(j[0]).attr("name");
b.each(an.p.colModel,function(){if(this.name==h&&this.editoptions&&b.isFunction(this.editoptions.custom_value)){try{ao[h]=this.editoptions.custom_value(b("#"+h,"#"+ak),"get");
if(ao[h]===undefined){throw"e1"
}}catch(k){k=="e1"?info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):info_dialog(jQuery.jgrid.errors.errcap,k.message,jQuery.jgrid.edit.bClose)
}return true
}})
}else{switch(b(this).get(0).type){case"checkbox":if(b(this).attr("checked")){ao[this.name]=b(this).val()
}else{j=b(this).attr("offval");
ao[this.name]=j
}break;
case"select-one":ao[this.name]=b("option:selected",this).val();
S[this.name]=b("option:selected",this).text();
break;
case"select-multiple":ao[this.name]=b(this).val();
ao[this.name]=ao[this.name]?ao[this.name].join(","):"";
var g=[];
b("option:selected",this).each(function(l,k){g[l]=b(k).text()
});
S[this.name]=g.join(",");
break;
case"password":case"text":case"textarea":case"button":ao[this.name]=b(this).val();
break
}if(an.p.autoencode){ao[this.name]=b.jgrid.htmlEncode(ao[this.name])
}}});
return true
}function ab(m,g,B,h){for(var v,o,u,s=0,q,n,A,l=[],w=false,j="",k=1;
k<=h;
k++){j+="<td class='CaptionTD ui-widget-content'>&#160;</td><td class='DataTD ui-widget-content' style='white-space:pre'>&#160;</td>"
}if(m!="_empty"){w=b(g).jqGrid("getInd",m)
}b(g.p.colModel).each(function(E){v=this.name;
n=(o=this.editrules&&this.editrules.edithidden===true?false:this.hidden===true?true:false)?"style='display:none'":"";
if(v!=="cb"&&v!=="subgrid"&&this.editable===true&&v!=="rn"){if(w===false){q=""
}else{if(v==g.p.ExpandColumn&&g.p.treeGrid===true){q=b("td:eq("+E+")",g.rows[w]).text()
}else{try{q=b.unformat(b("td:eq("+E+")",g.rows[w]),{rowId:m,colModel:this},E)
}catch(D){q=b("td:eq("+E+")",g.rows[w]).html()
}}}var C=b.extend({},this.editoptions||{},{id:v,name:v}),M=b.extend({},{elmprefix:"",elmsuffix:"",rowabove:false,rowcontent:""},this.formoptions||{}),K=parseInt(M.rowpos,10)||s+1,F=parseInt((parseInt(M.colpos,10)||1)*2,10);
if(m=="_empty"&&C.defaultValue){q=b.isFunction(C.defaultValue)?C.defaultValue():C.defaultValue
}if(!this.edittype){this.edittype="text"
}if(an.p.autoencode){q=b.jgrid.htmlDecode(q)
}A=createEl(this.edittype,C,q,false,b.extend({},b.jgrid.ajaxOptions,g.p.ajaxSelectOptions||{}));
if(q===""&&this.edittype=="checkbox"){q=b(A).attr("offval")
}if(q===""&&this.edittype=="select"){q=b("option:eq(0)",A).text()
}if(c.checkOnSubmit||c.checkOnUpdate){c._savedData[v]=q
}b(A).addClass("FormElement");
u=b(B).find("tr[rowpos="+K+"]");
if(M.rowabove){C=b("<tr><td class='contentinfo' colspan='"+h*2+"'>"+M.rowcontent+"</td></tr>");
b(B).append(C);
C[0].rp=K
}if(u.length===0){u=b("<tr "+n+" rowpos='"+K+"'></tr>").addClass("FormData").attr("id","tr_"+v);
b(u).append(j);
b(B).append(u);
u[0].rp=K
}b("td:eq("+(F-2)+")",u[0]).html(typeof M.label==="undefined"?g.p.colNames[E]:M.label);
b("td:eq("+(F-1)+")",u[0]).append(M.elmprefix).append(A).append(M.elmsuffix);
l[s]=E;
s++
}});
if(s>0){k=b("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+(h*2-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='"+g.p.id+"_id' value='"+m+"'/></td></tr>");
k[0].rp=s+999;
b(B).append(k);
if(c.checkOnSubmit||c.checkOnUpdate){c._savedData[g.p.id+"_id"]=m
}}return l
}function R(k,g,v){var h,s=0,m,q,o,n,l;
if(c.checkOnSubmit||c.checkOnUpdate){c._savedData={};
c._savedData[g.p.id+"_id"]=k
}var u=g.p.colModel;
if(k=="_empty"){b(u).each(function(){h=this.name;
o=b.extend({},this.editoptions||{});
q=b("#"+b.jgrid.jqID(h),"#"+v);
if(q[0]!=null){n="";
if(o.defaultValue){n=b.isFunction(o.defaultValue)?o.defaultValue():o.defaultValue;
if(q[0].type=="checkbox"){l=n.toLowerCase();
if(l.search(/(false|0|no|off|undefined)/i)<0&&l!==""){q[0].checked=true;
q[0].defaultChecked=true;
q[0].value=n
}else{q.attr({checked:"",defaultChecked:""})
}}else{q.val(n)
}}else{if(q[0].type=="checkbox"){q[0].checked=false;
q[0].defaultChecked=false;
n=b(q).attr("offval")
}else{if(q[0].type&&q[0].type.substr(0,6)=="select"){q[0].selectedIndex=0
}else{q.val(n)
}}}if(c.checkOnSubmit===true||c.checkOnUpdate){c._savedData[h]=n
}}});
b("#id_g","#"+v).val(k)
}else{var j=b(g).jqGrid("getInd",k,true);
if(j){b("td",j).each(function(C){h=u[C].name;
if(h!=="cb"&&h!=="subgrid"&&h!=="rn"&&u[C].editable===true){if(h==g.p.ExpandColumn&&g.p.treeGrid===true){m=b(this).text()
}else{try{m=b.unformat(b(this),{rowId:k,colModel:u[C]},C)
}catch(w){m=b(this).html()
}}if(an.p.autoencode){m=b.jgrid.htmlDecode(m)
}if(c.checkOnSubmit===true||c.checkOnUpdate){c._savedData[h]=m
}h=b.jgrid.jqID(h);
switch(u[C].edittype){case"password":case"text":case"button":case"image":b("#"+h,"#"+v).val(m);
break;
case"textarea":if(m=="&nbsp;"||m=="&#160;"||m.length==1&&m.charCodeAt(0)==160){m=""
}b("#"+h,"#"+v).val(m);
break;
case"select":var B=m.split(",");
B=b.map(B,function(D){return b.trim(D)
});
b("#"+h+" option","#"+v).each(function(){this.selected=!u[C].editoptions.multiple&&(B[0]==b.trim(b(this).text())||B[0]==b.trim(b(this).val()))?true:u[C].editoptions.multiple?b.inArray(b.trim(b(this).text()),B)>-1||b.inArray(b.trim(b(this).val()),B)>-1?true:false:false
});
break;
case"checkbox":m+="";
if(u[C].editoptions&&u[C].editoptions.value){if(u[C].editoptions.value.split(":")[0]==m){b("#"+h,"#"+v).attr("checked",true);
b("#"+h,"#"+v).attr("defaultChecked",true)
}else{b("#"+h,"#"+v).attr("checked",false);
b("#"+h,"#"+v).attr("defaultChecked","")
}}else{m=m.toLowerCase();
if(m.search(/(false|0|no|off|undefined)/i)<0&&m!==""){b("#"+h,"#"+v).attr("checked",true);
b("#"+h,"#"+v).attr("defaultChecked",true)
}else{b("#"+h,"#"+v).attr("checked",false);
b("#"+h,"#"+v).attr("defaultChecked","")
}}break;
case"custom":try{if(u[C].editoptions&&b.isFunction(u[C].editoptions.custom_value)){u[C].editoptions.custom_value(b("#"+h,"#"+v),"set",m)
}else{throw"e1"
}}catch(A){A=="e1"?info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose):info_dialog(jQuery.jgrid.errors.errcap,A.message,jQuery.jgrid.edit.bClose)
}break
}s++
}});
s>0&&b("#id_g","#"+ak).val(k)
}}}function ac(){var m=[true,"",""],k={},j=an.p.prmNames,l,h;
if(b.isFunction(c.beforeCheckValues)){var n=c.beforeCheckValues(ao,b("#"+am),ao[an.p.id+"_id"]=="_empty"?j.addoper:j.editoper);
if(n&&typeof n==="object"){ao=n
}}for(var g in ao){if(ao.hasOwnProperty(g)){m=checkValues(ao[g],g,an);
if(m[0]===false){break
}}}if(m[0]){if(b.isFunction(c.onclickSubmit)){k=c.onclickSubmit(c,ao)||{}
}if(b.isFunction(c.beforeSubmit)){m=c.beforeSubmit(ao,b("#"+am))
}}if(m[0]&&!c.processing){c.processing=true;
b("#sData","#"+ak+"_2").addClass("ui-state-active");
h=j.oper;
l=j.id;
ao[h]=b.trim(ao[an.p.id+"_id"])=="_empty"?j.addoper:j.editoper;
if(ao[h]!=j.addoper){ao[l]=ao[an.p.id+"_id"]
}else{if(ao[l]===undefined){ao[l]=ao[an.p.id+"_id"]
}}delete ao[an.p.id+"_id"];
ao=b.extend(ao,c.editData,k);
k=b.extend({url:c.url?c.url:b(an).jqGrid("getGridParam","editurl"),type:c.mtype,data:b.isFunction(c.serializeEditData)?c.serializeEditData(ao):ao,complete:function(q,u){if(u!="success"){m[0]=false;
m[1]=b.isFunction(c.errorTextFormat)?c.errorTextFormat(q):u+" Status: '"+q.statusText+"'. Error code: "+q.status
}else{if(b.isFunction(c.afterSubmit)){m=c.afterSubmit(q,ao)
}}if(m[0]===false){b("#FormError>td","#"+ak).html(m[1]);
b("#FormError","#"+ak).show()
}else{b.each(an.p.colModel,function(){if(S[this.name]&&this.formatter&&this.formatter=="select"){try{delete S[this.name]
}catch(v){}}});
ao=b.extend(ao,S);
an.p.autoencode&&b.each(ao,function(w,v){ao[w]=b.jgrid.htmlDecode(v)
});
c.reloadAfterSubmit=c.reloadAfterSubmit&&an.p.datatype!="local";
if(ao[h]==j.addoper){m[2]||(m[2]=parseInt(an.p.records,10)+1+"");
ao[l]=m[2];
if(c.closeAfterAdd){if(c.reloadAfterSubmit){b(an).trigger("reloadGrid")
}else{b(an).jqGrid("addRowData",m[2],ao,a.addedrow);
b(an).jqGrid("setSelection",m[2])
}hideModal("#"+ai.themodal,{gb:"#gbox_"+aj,jqm:a.jqModal,onClose:c.onClose})
}else{if(c.clearAfterAdd){c.reloadAfterSubmit?b(an).trigger("reloadGrid"):b(an).jqGrid("addRowData",m[2],ao,a.addedrow);
R("_empty",an,am)
}else{c.reloadAfterSubmit?b(an).trigger("reloadGrid"):b(an).jqGrid("addRowData",m[2],ao,a.addedrow)
}}}else{if(c.reloadAfterSubmit){b(an).trigger("reloadGrid");
c.closeAfterEdit||setTimeout(function(){b(an).jqGrid("setSelection",ao[l])
},1000)
}else{an.p.treeGrid===true?b(an).jqGrid("setTreeRow",ao[l],ao):b(an).jqGrid("setRowData",ao[l],ao)
}c.closeAfterEdit&&hideModal("#"+ai.themodal,{gb:"#gbox_"+aj,jqm:a.jqModal,onClose:c.onClose})
}if(b.isFunction(c.afterComplete)){ae=q;
setTimeout(function(){c.afterComplete(ae,ao,b("#"+am));
ae=null
},500)
}}c.processing=false;
if(c.checkOnSubmit||c.checkOnUpdate){b("#"+am).data("disabled",false);
if(c._savedData[an.p.id+"_id"]!="_empty"){for(var o in c._savedData){if(ao[o]){c._savedData[o]=ao[o]
}}}}b("#sData","#"+ak+"_2").removeClass("ui-state-active");
try{b(":input:visible","#"+am)[0].focus()
}catch(s){}},error:function(q,s,o){b("#FormError>td","#"+ak).html(s+" : "+o);
b("#FormError","#"+ak).show();
c.processing=false;
b("#"+am).data("disabled",false);
b("#sData","#"+ak+"_2").removeClass("ui-state-active")
}},b.jgrid.ajaxOptions,c.ajaxEditOptions);
if(!k.url&&!c.useDataProxy){if(b.isFunction(an.p.dataProxy)){c.useDataProxy=true
}else{m[0]=false;
m[1]+=" "+b.jgrid.errors.nourl
}}if(m[0]){c.useDataProxy?an.p.dataProxy.call(an,k,"set_"+an.p.id):b.ajax(k)
}}if(m[0]===false){b("#FormError>td","#"+ak).html(m[1]);
b("#FormError","#"+ak).show()
}}function L(k,h){var g=false,j;
for(j in k){if(k[j]!=h[j]){g=true;
break
}}return g
}function al(){var g=true;
b("#FormError","#"+ak).hide();
if(c.checkOnUpdate){ao={};
S={};
af();
I=b.extend({},ao,S);
if(r=L(I,c._savedData)){b("#"+am).data("disabled",true);
b(".confirm","#"+ai.themodal).show();
g=false
}}return g
}function ag(h,g){h===0?b("#pData","#"+ak+"_2").addClass("ui-state-disabled"):b("#pData","#"+ak+"_2").removeClass("ui-state-disabled");
h==g?b("#nData","#"+ak+"_2").addClass("ui-state-disabled"):b("#nData","#"+ak+"_2").removeClass("ui-state-disabled")
}function W(){var h=b(an).jqGrid("getDataIDs"),g=b("#id_g","#"+ak).val();
return[b.inArray(g,h),h]
}var an=this;
if(an.grid&&d){var aj=an.p.id,am="FrmGrid_"+aj,ak="TblGrid_"+aj,ai={themodal:"editmod"+aj,modalhead:"edithd"+aj,modalcontent:"editcnt"+aj,scrollelm:am},aa=b.isFunction(c.beforeShowForm)?c.beforeShowForm:false,x=b.isFunction(c.afterShowForm)?c.afterShowForm:false,ah=b.isFunction(c.beforeInitData)?c.beforeInitData:false,ad=b.isFunction(c.onInitializeForm)?c.onInitializeForm:false,ae=null,T=1,J=0,ao,S,I,r;
if(d=="new"){d="_empty";
a.caption=a.addCaption
}else{a.caption=a.editCaption
}a.recreateForm===true&&b("#"+ai.themodal).html()!=null&&b("#"+ai.themodal).remove();
var G=true;
if(a.checkOnUpdate&&a.jqModal&&!a.modal){G=false
}if(b("#"+ai.themodal).html()!=null){b(".ui-jqdialog-title","#"+ai.modalhead).html(a.caption);
b("#FormError","#"+ak).hide();
if(c.topinfo){b(".topinfo","#"+ak+"_2").html(c.topinfo);
b(".tinfo","#"+ak+"_2").show()
}else{b(".tinfo","#"+ak+"_2").hide()
}if(c.bottominfo){b(".bottominfo","#"+ak+"_2").html(c.bottominfo);
b(".binfo","#"+ak+"_2").show()
}else{b(".binfo","#"+ak+"_2").hide()
}ah&&ah(b("#"+am));
R(d,an,am);
d=="_empty"||!c.viewPagerButtons?b("#pData, #nData","#"+ak+"_2").hide():b("#pData, #nData","#"+ak+"_2").show();
if(c.processing===true){c.processing=false;
b("#sData","#"+ak+"_2").removeClass("ui-state-active")
}if(b("#"+am).data("disabled")===true){b(".confirm","#"+ai.themodal).hide();
b("#"+am).data("disabled",false)
}aa&&aa(b("#"+am));
b("#"+ai.themodal).data("onClose",c.onClose);
viewModal("#"+ai.themodal,{gbox:"#gbox_"+aj,jqm:a.jqModal,jqM:false,closeoverlay:G,modal:a.modal});
G||b(".jqmOverlay").click(function(){if(!al()){return false
}hideModal("#"+ai.themodal,{gb:"#gbox_"+aj,jqm:a.jqModal,onClose:c.onClose});
return false
});
x&&x(b("#"+am))
}else{b(an.p.colModel).each(function(){var g=this.formoptions;
T=Math.max(T,g?g.colpos||0:0);
J=Math.max(J,g?g.rowpos||0:0)
});
var z=isNaN(a.dataheight)?a.dataheight:a.dataheight+"px";
z=b("<form name='FormPost' id='"+am+"' class='FormGrid' onSubmit='return false;' style='width:100%;overflow:auto;position:relative;height:"+z+";'></form>").data("disabled",false);
var H=b("<table id='"+ak+"' class='EditTable' cellspacing='0' cellpading='0' border='0'><tbody></tbody></table>");
b(z).append(H);
var X=b("<tr id='FormError' style='display:none'><td class='ui-state-error' colspan='"+T*2+"'></td></tr>");
X[0].rp=0;
b(H).append(X);
X=b("<tr style='display:none' class='tinfo'><td class='topinfo' colspan='"+T*2+"'>"+c.topinfo+"</td></tr>");
X[0].rp=0;
b(H).append(X);
ah&&ah(b("#"+am));
X=(ah=an.p.direction=="rtl"?true:false)?"nData":"pData";
var p=ah?"pData":"nData";
ab(d,an,H,T);
X="<a href='javascript:void(0)' id='"+X+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></div>";
p="<a href='javascript:void(0)' id='"+p+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></div>";
var e="<a href='javascript:void(0)' id='sData' class='fm-button ui-state-default ui-corner-all'>"+a.bSubmit+"</a>",t="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+a.bCancel+"</a>";
X="<table border='0' class='EditTable' id='"+ak+"_2'><tbody><tr id='Act_Buttons'><td class='navButton ui-widget-content'>"+(ah?p+X:X+p)+"</td><td class='EditButton ui-widget-content'>"+e+t+"</td></tr>";
X+="<tr style='display:none' class='binfo'><td class='bottominfo' colspan='2'>"+c.bottominfo+"</td></tr>";
X+="</tbody></table>";
if(J>0){var f=[];
b.each(b(H)[0].rows,function(h,g){f[h]=g
});
f.sort(function(h,g){if(h.rp>g.rp){return 1
}if(h.rp<g.rp){return -1
}return 0
});
b.each(f,function(h,g){b("tbody",H).append(g)
})
}a.gbox="#gbox_"+aj;
var y=false;
if(a.closeOnEscape===true){a.closeOnEscape=false;
y=true
}z=b("<span></span>").append(z).append(X);
createModal(ai,z,a,"#gview_"+an.p.id,b("#gbox_"+an.p.id)[0]);
if(ah){b("#pData, #nData","#"+ak+"_2").css("float","right");
b(".EditButton","#"+ak+"_2").css("text-align","left")
}c.topinfo&&b(".tinfo","#"+ak+"_2").show();
c.bottominfo&&b(".binfo","#"+ak+"_2").show();
X=z=null;
b("#"+ai.themodal).keydown(function(h){var g=h.target;
if(b("#"+am).data("disabled")===true){return false
}if(c.savekey[0]===true&&h.which==c.savekey[1]){if(g.tagName!="TEXTAREA"){b("#sData","#"+ak+"_2").trigger("click");
return false
}}if(h.which===27){if(!al()){return false
}y&&hideModal(this,{gb:a.gbox,jqm:a.jqModal,onClose:c.onClose});
return false
}if(c.navkeys[0]===true){if(b("#id_g","#"+ak).val()=="_empty"){return true
}if(h.which==c.navkeys[1]){b("#pData","#"+ak+"_2").trigger("click");
return false
}if(h.which==c.navkeys[2]){b("#nData","#"+ak+"_2").trigger("click");
return false
}}});
if(a.checkOnUpdate){b("a.ui-jqdialog-titlebar-close span","#"+ai.themodal).removeClass("jqmClose");
b("a.ui-jqdialog-titlebar-close","#"+ai.themodal).unbind("click").click(function(){if(!al()){return false
}hideModal("#"+ai.themodal,{gb:"#gbox_"+aj,jqm:a.jqModal,onClose:c.onClose});
return false
})
}a.saveicon=b.extend([true,"left","ui-icon-disk"],a.saveicon);
a.closeicon=b.extend([true,"left","ui-icon-close"],a.closeicon);
if(a.saveicon[0]===true){b("#sData","#"+ak+"_2").addClass(a.saveicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+a.saveicon[2]+"'></span>")
}if(a.closeicon[0]===true){b("#cData","#"+ak+"_2").addClass(a.closeicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+a.closeicon[2]+"'></span>")
}if(c.checkOnSubmit||c.checkOnUpdate){e="<a href='javascript:void(0)' id='sNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+a.bYes+"</a>";
p="<a href='javascript:void(0)' id='nNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+a.bNo+"</a>";
t="<a href='javascript:void(0)' id='cNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+a.bExit+"</a>";
z=a.zIndex||999;
z++;
b("<div class='ui-widget-overlay jqgrid-overlay confirm' style='z-index:"+z+";display:none;'>&#160;"+(b.browser.msie&&b.browser.version==6?'<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>':"")+"</div><div class='confirm ui-widget-content ui-jqconfirm' style='z-index:"+(z+1)+"'>"+a.saveData+"<br/><br/>"+e+p+t+"</div>").insertAfter("#"+am);
b("#sNew","#"+ai.themodal).click(function(){ac();
b("#"+am).data("disabled",false);
b(".confirm","#"+ai.themodal).hide();
return false
});
b("#nNew","#"+ai.themodal).click(function(){b(".confirm","#"+ai.themodal).hide();
b("#"+am).data("disabled",false);
setTimeout(function(){b(":input","#"+am)[0].focus()
},0);
return false
});
b("#cNew","#"+ai.themodal).click(function(){b(".confirm","#"+ai.themodal).hide();
b("#"+am).data("disabled",false);
hideModal("#"+ai.themodal,{gb:"#gbox_"+aj,jqm:a.jqModal,onClose:c.onClose});
return false
})
}ad&&ad(b("#"+am));
d=="_empty"||!c.viewPagerButtons?b("#pData,#nData","#"+ak+"_2").hide():b("#pData,#nData","#"+ak+"_2").show();
aa&&aa(b("#"+am));
b("#"+ai.themodal).data("onClose",c.onClose);
viewModal("#"+ai.themodal,{gbox:"#gbox_"+aj,jqm:a.jqModal,closeoverlay:G,modal:a.modal});
G||b(".jqmOverlay").click(function(){if(!al()){return false
}hideModal("#"+ai.themodal,{gb:"#gbox_"+aj,jqm:a.jqModal,onClose:c.onClose});
return false
});
x&&x(b("#"+am));
b(".fm-button","#"+ai.themodal).hover(function(){b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
b("#sData","#"+ak+"_2").click(function(){ao={};
S={};
b("#FormError","#"+ak).hide();
af();
if(ao[an.p.id+"_id"]=="_empty"){ac()
}else{if(a.checkOnSubmit===true){I=b.extend({},ao,S);
if(r=L(I,c._savedData)){b("#"+am).data("disabled",true);
b(".confirm","#"+ai.themodal).show()
}else{ac()
}}else{ac()
}}return false
});
b("#cData","#"+ak+"_2").click(function(){if(!al()){return false
}hideModal("#"+ai.themodal,{gb:"#gbox_"+aj,jqm:a.jqModal,onClose:c.onClose});
return false
});
b("#nData","#"+ak+"_2").click(function(){if(!al()){return false
}b("#FormError","#"+ak).hide();
var g=W();
g[0]=parseInt(g[0],10);
if(g[0]!=-1&&g[1][g[0]+1]){b.isFunction(a.onclickPgButtons)&&a.onclickPgButtons("next",b("#"+am),g[1][g[0]]);
R(g[1][g[0]+1],an,am);
b(an).jqGrid("setSelection",g[1][g[0]+1]);
b.isFunction(a.afterclickPgButtons)&&a.afterclickPgButtons("next",b("#"+am),g[1][g[0]+1]);
ag(g[0]+1,g[1].length-1)
}return false
});
b("#pData","#"+ak+"_2").click(function(){if(!al()){return false
}b("#FormError","#"+ak).hide();
var g=W();
if(g[0]!=-1&&g[1][g[0]-1]){b.isFunction(a.onclickPgButtons)&&a.onclickPgButtons("prev",b("#"+am),g[1][g[0]]);
R(g[1][g[0]-1],an,am);
b(an).jqGrid("setSelection",g[1][g[0]-1]);
b.isFunction(a.afterclickPgButtons)&&a.afterclickPgButtons("prev",b("#"+am),g[1][g[0]-1]);
ag(g[0]-1,g[1].length-1)
}return false
})
}aa=W();
ag(aa[0],aa[1].length-1)
}})
},viewGridRow:function(d,a){a=b.extend({top:0,left:0,width:0,height:"auto",dataheight:"auto",modal:false,drag:true,resize:true,jqModal:true,closeOnEscape:false,labelswidth:"30%",closeicon:[],navkeys:[false,38,40],onClose:null,beforeShowForm:null,viewPagerButtons:true},b.jgrid.view,a||{});
return this.each(function(){function H(){if(a.closeOnEscape===true||a.navkeys[0]===true){setTimeout(function(){b(".ui-jqdialog-titlebar-close","#"+M.modalhead).focus()
},0)
}}function y(T,C,s,k){for(var o,n,q,F=0,j,g,l=[],h=false,m="<td class='CaptionTD form-view-label ui-widget-content' width='"+a.labelswidth+"'>&#160;</td><td class='DataTD form-view-data ui-helper-reset ui-widget-content'>&#160;</td>",S="",K=["integer","number","currency"],E=0,Q=0,B,v,A,w=1;
w<=k;
w++){S+=w==1?m:"<td class='CaptionTD form-view-label ui-widget-content'>&#160;</td><td class='DataTD form-view-data ui-widget-content'>&#160;</td>"
}b(C.p.colModel).each(function(){n=this.editrules&&this.editrules.edithidden===true?false:this.hidden===true?true:false;
if(!n&&this.align==="right"){if(this.formatter&&b.inArray(this.formatter,K)!==-1){E=Math.max(E,parseInt(this.width,10))
}else{Q=Math.max(Q,parseInt(this.width,10))
}}});
B=E!==0?E:Q!==0?Q:0;
h=b(C).jqGrid("getInd",T);
b(C.p.colModel).each(function(Y){o=this.name;
v=false;
g=(n=this.editrules&&this.editrules.edithidden===true?false:this.hidden===true?true:false)?"style='display:none'":"";
A=typeof this.viewable!="boolean"?true:this.viewable;
if(o!=="cb"&&o!=="subgrid"&&o!=="rn"&&A){j=h===false?"":o==C.p.ExpandColumn&&C.p.treeGrid===true?b("td:eq("+Y+")",C.rows[h]).text():b("td:eq("+Y+")",C.rows[h]).html();
v=this.align==="right"&&B!==0?true:false;
b.extend({},this.editoptions||{},{id:o,name:o});
var U=b.extend({},{rowabove:false,rowcontent:""},this.formoptions||{}),X=parseInt(U.rowpos,10)||F+1,W=parseInt((parseInt(U.colpos,10)||1)*2,10);
if(U.rowabove){var V=b("<tr><td class='contentinfo' colspan='"+k*2+"'>"+U.rowcontent+"</td></tr>");
b(s).append(V);
V[0].rp=X
}q=b(s).find("tr[rowpos="+X+"]");
if(q.length===0){q=b("<tr "+g+" rowpos='"+X+"'></tr>").addClass("FormData").attr("id","trv_"+o);
b(q).append(S);
b(s).append(q);
q[0].rp=X
}b("td:eq("+(W-2)+")",q[0]).html("<b>"+(typeof U.label==="undefined"?C.p.colNames[Y]:U.label)+"</b>");
b("td:eq("+(W-1)+")",q[0]).append("<span>"+j+"</span>").attr("id","v_"+o);
v&&b("td:eq("+(W-1)+") span",q[0]).css({"text-align":"right",width:B+"px"});
l[F]=Y;
F++
}});
if(F>0){T=b("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+(k*2-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='id' value='"+T+"'/></td></tr>");
T[0].rp=F+99;
b(s).append(T)
}return l
}function r(n,h){var m,g,k=0,j,l;
if(l=b(h).jqGrid("getInd",n,true)){b("td",l).each(function(o){m=h.p.colModel[o].name;
g=h.p.colModel[o].editrules&&h.p.colModel[o].editrules.edithidden===true?false:h.p.colModel[o].hidden===true?true:false;
if(m!=="cb"&&m!=="subgrid"&&m!=="rn"){j=m==h.p.ExpandColumn&&h.p.treeGrid===true?b(this).text():b(this).html();
b.extend({},h.p.colModel[o].editoptions||{});
m=b.jgrid.jqID("v_"+m);
b("#"+m+" span","#"+R).html(j);
g&&b("#"+m,"#"+R).parents("tr:first").hide();
k++
}});
k>0&&b("#id_g","#"+R).val(n)
}}function z(h,g){h===0?b("#pData","#"+R+"_2").addClass("ui-state-disabled"):b("#pData","#"+R+"_2").removeClass("ui-state-disabled");
h==g?b("#nData","#"+R+"_2").addClass("ui-state-disabled"):b("#nData","#"+R+"_2").removeClass("ui-state-disabled")
}function p(){var h=b(O).jqGrid("getDataIDs"),g=b("#id_g","#"+R).val();
return[b.inArray(g,h),h]
}var O=this;
if(O.grid&&d){if(!a.imgpath){a.imgpath=O.p.imgpath
}var I=O.p.id,u="ViewGrid_"+I,R="ViewTbl_"+I,M={themodal:"viewmod"+I,modalhead:"viewhd"+I,modalcontent:"viewcnt"+I,scrollelm:u},P=1,N=0;
if(b("#"+M.themodal).html()!=null){b(".ui-jqdialog-title","#"+M.modalhead).html(a.caption);
b("#FormError","#"+R).hide();
r(d,O);
b.isFunction(a.beforeShowForm)&&a.beforeShowForm(b("#"+u));
viewModal("#"+M.themodal,{gbox:"#gbox_"+I,jqm:a.jqModal,jqM:false,modal:a.modal});
H()
}else{b(O.p.colModel).each(function(){var g=this.formoptions;
P=Math.max(P,g?g.colpos||0:0);
N=Math.max(N,g?g.rowpos||0:0)
});
var L=isNaN(a.dataheight)?a.dataheight:a.dataheight+"px",x=b("<form name='FormPost' id='"+u+"' class='FormGrid' style='width:100%;overflow:auto;position:relative;height:"+L+";'></form>"),e=b("<table id='"+R+"' class='EditTable' cellspacing='1' cellpading='2' border='0' style='table-layout:fixed'><tbody></tbody></table>");
b(x).append(e);
y(d,O,e,P);
L=O.p.direction=="rtl"?true:false;
var J="<a href='javascript:void(0)' id='"+(L?"nData":"pData")+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></div>",D="<a href='javascript:void(0)' id='"+(L?"pData":"nData")+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></div>",G="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+a.bClose+"</a>";
if(N>0){var t=[];
b.each(b(e)[0].rows,function(h,g){t[h]=g
});
t.sort(function(h,g){if(h.rp>g.rp){return 1
}if(h.rp<g.rp){return -1
}return 0
});
b.each(t,function(h,g){b("tbody",e).append(g)
})
}a.gbox="#gbox_"+I;
var f=false;
if(a.closeOnEscape===true){a.closeOnEscape=false;
f=true
}x=b("<span></span>").append(x).append("<table border='0' class='EditTable' id='"+R+"_2'><tbody><tr id='Act_Buttons'><td class='navButton ui-widget-content' width='"+a.labelswidth+"'>"+(L?D+J:J+D)+"</td><td class='EditButton ui-widget-content'>"+G+"</td></tr></tbody></table>");
createModal(M,x,a,"#gview_"+O.p.id,b("#gview_"+O.p.id)[0]);
if(L){b("#pData, #nData","#"+R+"_2").css("float","right");
b(".EditButton","#"+R+"_2").css("text-align","left")
}a.viewPagerButtons||b("#pData, #nData","#"+R+"_2").hide();
x=null;
b("#"+M.themodal).keydown(function(g){if(g.which===27){f&&hideModal(this,{gb:a.gbox,jqm:a.jqModal,onClose:a.onClose});
return false
}if(a.navkeys[0]===true){if(g.which===a.navkeys[1]){b("#pData","#"+R+"_2").trigger("click");
return false
}if(g.which===a.navkeys[2]){b("#nData","#"+R+"_2").trigger("click");
return false
}}});
a.closeicon=b.extend([true,"left","ui-icon-close"],a.closeicon);
if(a.closeicon[0]===true){b("#cData","#"+R+"_2").addClass(a.closeicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+a.closeicon[2]+"'></span>")
}b.isFunction(a.beforeShowForm)&&a.beforeShowForm(b("#"+u));
viewModal("#"+M.themodal,{gbox:"#gbox_"+I,jqm:a.jqModal,modal:a.modal});
b(".fm-button:not(.ui-state-disabled)","#"+R+"_2").hover(function(){b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
H();
b("#cData","#"+R+"_2").click(function(){hideModal("#"+M.themodal,{gb:"#gbox_"+I,jqm:a.jqModal,onClose:a.onClose});
return false
});
b("#nData","#"+R+"_2").click(function(){b("#FormError","#"+R).hide();
var g=p();
g[0]=parseInt(g[0],10);
if(g[0]!=-1&&g[1][g[0]+1]){b.isFunction(a.onclickPgButtons)&&a.onclickPgButtons("next",b("#"+u),g[1][g[0]]);
r(g[1][g[0]+1],O);
b(O).jqGrid("setSelection",g[1][g[0]+1]);
b.isFunction(a.afterclickPgButtons)&&a.afterclickPgButtons("next",b("#"+u),g[1][g[0]+1]);
z(g[0]+1,g[1].length-1)
}H();
return false
});
b("#pData","#"+R+"_2").click(function(){b("#FormError","#"+R).hide();
var g=p();
if(g[0]!=-1&&g[1][g[0]-1]){b.isFunction(a.onclickPgButtons)&&a.onclickPgButtons("prev",b("#"+u),g[1][g[0]]);
r(g[1][g[0]-1],O);
b(O).jqGrid("setSelection",g[1][g[0]-1]);
b.isFunction(a.afterclickPgButtons)&&a.afterclickPgButtons("prev",b("#"+u),g[1][g[0]-1]);
z(g[0]-1,g[1].length-1)
}H();
return false
})
}L=p();
z(L[0],L[1].length-1)
}})
},delGridRow:function(d,a){c=a=b.extend({top:0,left:0,width:240,height:"auto",dataheight:"auto",modal:false,drag:true,resize:true,url:"",mtype:"POST",reloadAfterSubmit:true,beforeShowForm:null,afterShowForm:null,beforeSubmit:null,onclickSubmit:null,afterSubmit:null,jqModal:true,closeOnEscape:false,delData:{},delicon:[],cancelicon:[],onClose:null,ajaxDelOptions:{},processing:false,serializeDelData:null,useDataProxy:false},b.jgrid.del,a||{});
return this.each(function(){var l=this;
if(l.grid){if(d){var e=typeof a.beforeShowForm==="function"?true:false,x=typeof a.afterShowForm==="function"?true:false,f=l.p.id,u={},r="DelTbl_"+f,m,y,t,p,s={themodal:"delmod"+f,modalhead:"delhd"+f,modalcontent:"delcnt"+f,scrollelm:r};
if(jQuery.isArray(d)){d=d.join()
}if(b("#"+s.themodal).html()!=null){b("#DelData>td","#"+r).text(d);
b("#DelError","#"+r).hide();
if(c.processing===true){c.processing=false;
b("#dData","#"+r).removeClass("ui-state-active")
}e&&a.beforeShowForm(b("#"+r));
viewModal("#"+s.themodal,{gbox:"#gbox_"+f,jqm:a.jqModal,jqM:false,modal:a.modal})
}else{var q=isNaN(a.dataheight)?a.dataheight:a.dataheight+"px";
q="<div id='"+r+"' class='formdata' style='width:100%;overflow:auto;position:relative;height:"+q+";'>";
q+="<table class='DelTable'><tbody>";
q+="<tr id='DelError' style='display:none'><td class='ui-state-error'></td></tr>";
q+="<tr id='DelData' style='display:none'><td >"+d+"</td></tr>";
q+='<tr><td class="delmsg" style="white-space:pre;">'+a.msg+"</td></tr><tr><td >&#160;</td></tr>";
q+="</tbody></table></div>";
q+="<table cellspacing='0' cellpadding='0' border='0' class='EditTable' id='"+r+"_2'><tbody><tr><td class='DataTD ui-widget-content'></td></tr><tr style='display:block;height:3px;'><td></td></tr><tr><td class='DelButton EditButton'>"+("<a href='javascript:void(0)' id='dData' class='fm-button ui-state-default ui-corner-all'>"+a.bSubmit+"</a>")+"&#160;"+("<a href='javascript:void(0)' id='eData' class='fm-button ui-state-default ui-corner-all'>"+a.bCancel+"</a>")+"</td></tr></tbody></table>";
a.gbox="#gbox_"+f;
createModal(s,q,a,"#gview_"+l.p.id,b("#gview_"+l.p.id)[0]);
b(".fm-button","#"+r+"_2").hover(function(){b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
a.delicon=b.extend([true,"left","ui-icon-scissors"],a.delicon);
a.cancelicon=b.extend([true,"left","ui-icon-cancel"],a.cancelicon);
if(a.delicon[0]===true){b("#dData","#"+r+"_2").addClass(a.delicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+a.delicon[2]+"'></span>")
}if(a.cancelicon[0]===true){b("#eData","#"+r+"_2").addClass(a.cancelicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+a.cancelicon[2]+"'></span>")
}b("#dData","#"+r+"_2").click(function(){var g=[true,""];
u={};
var h=b("#DelData>td","#"+r).text();
if(typeof a.onclickSubmit==="function"){u=a.onclickSubmit(c,h)||{}
}if(typeof a.beforeSubmit==="function"){g=a.beforeSubmit(h)
}if(g[0]&&!c.processing){c.processing=true;
b(this).addClass("ui-state-active");
t=l.p.prmNames;
m=b.extend({},c.delData,u);
p=t.oper;
m[p]=t.deloper;
y=t.id;
m[y]=h;
var j=b.extend({url:c.url?c.url:b(l).jqGrid("getGridParam","editurl"),type:a.mtype,data:b.isFunction(a.serializeDelData)?a.serializeDelData(m):m,complete:function(n,v){if(v!="success"){g[0]=false;
g[1]=b.isFunction(c.errorTextFormat)?c.errorTextFormat(n):v+" Status: '"+n.statusText+"'. Error code: "+n.status
}else{if(typeof c.afterSubmit==="function"){g=c.afterSubmit(n,m)
}}if(g[0]===false){b("#DelError>td","#"+r).html(g[1]);
b("#DelError","#"+r).show()
}else{if(c.reloadAfterSubmit&&l.p.datatype!="local"){b(l).trigger("reloadGrid")
}else{v=[];
v=h.split(",");
if(l.p.treeGrid===true){try{b(l).jqGrid("delTreeNode",v[0])
}catch(k){}}else{for(var o=0;
o<v.length;
o++){b(l).jqGrid("delRowData",v[o])
}}l.p.selrow=null;
l.p.selarrrow=[]
}b.isFunction(c.afterComplete)&&setTimeout(function(){c.afterComplete(n,h)
},500)
}c.processing=false;
b("#dData","#"+r+"_2").removeClass("ui-state-active");
g[0]&&hideModal("#"+s.themodal,{gb:"#gbox_"+f,jqm:a.jqModal,onClose:c.onClose})
},error:function(n,o,k){b("#DelError>td","#"+r).html(o+" : "+k);
b("#DelError","#"+r).show();
c.processing=false;
b("#dData","#"+r+"_2").removeClass("ui-state-active")
}},b.jgrid.ajaxOptions,a.ajaxDelOptions);
if(!j.url&&!c.useDataProxy){if(b.isFunction(l.p.dataProxy)){c.useDataProxy=true
}else{g[0]=false;
g[1]+=" "+b.jgrid.errors.nourl
}}if(g[0]){c.useDataProxy?l.p.dataProxy.call(l,j,"del_"+l.p.id):b.ajax(j)
}}if(g[0]===false){b("#DelError>td","#"+r).html(g[1]);
b("#DelError","#"+r).show()
}return false
});
b("#eData","#"+r+"_2").click(function(){hideModal("#"+s.themodal,{gb:"#gbox_"+f,jqm:a.jqModal,onClose:c.onClose});
return false
});
e&&a.beforeShowForm(b("#"+r));
viewModal("#"+s.themodal,{gbox:"#gbox_"+f,jqm:a.jqModal,modal:a.modal})
}x&&a.afterShowForm(b("#"+r));
a.closeOnEscape===true&&setTimeout(function(){b(".ui-jqdialog-titlebar-close","#"+s.modalhead).focus()
},0)
}}})
},navGrid:function(g,a,j,h,e,k,d){a=b.extend({edit:true,editicon:"ui-icon-pencil",add:true,addicon:"ui-icon-plus",del:true,delicon:"ui-icon-trash",search:true,searchicon:"ui-icon-search",refresh:true,refreshicon:"ui-icon-refresh",refreshstate:"firstpage",view:false,viewicon:"ui-icon-document",position:"left",closeOnEscape:true,beforeRefresh:null,afterRefresh:null,cloneToTop:false},b.jgrid.nav,a||{});
return this.each(function(){var p={themodal:"alertmod",modalhead:"alerthd",modalcontent:"alertcnt"},t=this,s,r,m;
if(!(!t.grid||typeof g!="string")){if(b("#"+p.themodal).html()===null){if(typeof window.innerWidth!="undefined"){s=window.innerWidth;
r=window.innerHeight
}else{if(typeof document.documentElement!="undefined"&&typeof document.documentElement.clientWidth!="undefined"&&document.documentElement.clientWidth!==0){s=document.documentElement.clientWidth;
r=document.documentElement.clientHeight
}else{s=1024;
r=768
}}createModal(p,"<div>"+a.alerttext+"</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>",{gbox:"#gbox_"+t.p.id,jqModal:true,drag:true,resize:true,caption:a.alertcap,top:r/2-25,left:s/2-100,width:200,height:"auto",closeOnEscape:a.closeOnEscape},"","",true)
}s=1;
if(a.cloneToTop&&t.p.toppager){s=2
}for(r=0;
r<s;
r++){var q=b("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>"),o,f;
if(r===0){o=g;
f=t.p.id;
if(o==t.p.toppager){f+="_top";
s=1
}}else{o=t.p.toppager;
f=t.p.id+"_top"
}t.p.direction=="rtl"&&b(q).attr("dir","rtl").css("float","right");
if(a.add){h=h||{};
m=b("<td class='ui-pg-button ui-corner-all'></td>");
b(m).append("<div class='ui-pg-div'><span class='ui-icon "+a.addicon+"'></span>"+a.addtext+"</div>");
b("tr",q).append(m);
b(m,q).attr({title:a.addtitle||"",id:h.id||"add_"+f}).click(function(){b(this).hasClass("ui-state-disabled")||(typeof a.addfunc=="function"?a.addfunc():b(t).jqGrid("editGridRow","new",h));
return false
}).hover(function(){b(this).hasClass("ui-state-disabled")||b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
m=null
}if(a.edit){m=b("<td class='ui-pg-button ui-corner-all'></td>");
j=j||{};
b(m).append("<div class='ui-pg-div'><span class='ui-icon "+a.editicon+"'></span>"+a.edittext+"</div>");
b("tr",q).append(m);
b(m,q).attr({title:a.edittitle||"",id:j.id||"edit_"+f}).click(function(){if(!b(this).hasClass("ui-state-disabled")){var l=t.p.selrow;
if(l){typeof a.editfunc=="function"?a.editfunc(l):b(t).jqGrid("editGridRow",l,j)
}else{viewModal("#"+p.themodal,{gbox:"#gbox_"+t.p.id,jqm:true});
b("#jqg_alrt").focus()
}}return false
}).hover(function(){b(this).hasClass("ui-state-disabled")||b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
m=null
}if(a.view){m=b("<td class='ui-pg-button ui-corner-all'></td>");
d=d||{};
b(m).append("<div class='ui-pg-div'><span class='ui-icon "+a.viewicon+"'></span>"+a.viewtext+"</div>");
b("tr",q).append(m);
b(m,q).attr({title:a.viewtitle||"",id:d.id||"view_"+f}).click(function(){if(!b(this).hasClass("ui-state-disabled")){var l=t.p.selrow;
if(l){b(t).jqGrid("viewGridRow",l,d)
}else{viewModal("#"+p.themodal,{gbox:"#gbox_"+t.p.id,jqm:true});
b("#jqg_alrt").focus()
}}return false
}).hover(function(){b(this).hasClass("ui-state-disabled")||b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
m=null
}if(a.del){m=b("<td class='ui-pg-button ui-corner-all'></td>");
e=e||{};
b(m).append("<div class='ui-pg-div'><span class='ui-icon "+a.delicon+"'></span>"+a.deltext+"</div>");
b("tr",q).append(m);
b(m,q).attr({title:a.deltitle||"",id:e.id||"del_"+f}).click(function(){if(!b(this).hasClass("ui-state-disabled")){var l;
if(t.p.multiselect){l=t.p.selarrrow;
if(l.length===0){l=null
}}else{l=t.p.selrow
}if(l){"function"==typeof a.delfunc?a.delfunc(l):b(t).jqGrid("delGridRow",l,e)
}else{viewModal("#"+p.themodal,{gbox:"#gbox_"+t.p.id,jqm:true});
b("#jqg_alrt").focus()
}}return false
}).hover(function(){b(this).hasClass("ui-state-disabled")||b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
m=null
}if(a.add||a.edit||a.del||a.view){b("tr",q).append("<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>")
}if(a.search){m=b("<td class='ui-pg-button ui-corner-all'></td>");
k=k||{};
b(m).append("<div class='ui-pg-div'><span class='ui-icon "+a.searchicon+"'></span>"+a.searchtext+"</div>");
b("tr",q).append(m);
b(m,q).attr({title:a.searchtitle||"",id:k.id||"search_"+f}).click(function(){b(this).hasClass("ui-state-disabled")||b(t).jqGrid("searchGrid",k);
return false
}).hover(function(){b(this).hasClass("ui-state-disabled")||b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
m=null
}if(a.refresh){m=b("<td class='ui-pg-button ui-corner-all'></td>");
b(m).append("<div class='ui-pg-div'><span class='ui-icon "+a.refreshicon+"'></span>"+a.refreshtext+"</div>");
b("tr",q).append(m);
b(m,q).attr({title:a.refreshtitle||"",id:"refresh_"+f}).click(function(){if(!b(this).hasClass("ui-state-disabled")){b.isFunction(a.beforeRefresh)&&a.beforeRefresh();
t.p.search=false;
try{b("#fbox_"+t.p.id).searchFilter().reset({reload:false});
b.isFunction(t.clearToolbar)&&t.clearToolbar(false)
}catch(l){}switch(a.refreshstate){case"firstpage":b(t).trigger("reloadGrid",[{page:1}]);
break;
case"current":b(t).trigger("reloadGrid",[{current:true}]);
break
}b.isFunction(a.afterRefresh)&&a.afterRefresh()
}return false
}).hover(function(){b(this).hasClass("ui-state-disabled")||b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
});
m=null
}m=b(".ui-jqgrid").css("font-size")||"11px";
b("body").append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+m+";visibility:hidden;' ></div>");
m=b(q).clone().appendTo("#testpg2").width();
b("#testpg2").remove();
b(o+"_"+a.position,o).append(q);
if(t.p._nvtd){if(m>t.p._nvtd[0]){b(o+"_"+a.position,o).width(m);
t.p._nvtd[0]=m
}t.p._nvtd[1]=m
}q=m=m=null
}}})
},navButtonAdd:function(d,a){a=b.extend({caption:"newButton",title:"",buttonicon:"ui-icon-newwin",onClickButton:null,position:"last",cursor:"pointer"},a||{});
return this.each(function(){if(this.grid){if(d.indexOf("#")!==0){d="#"+d
}var g=b(".navtable",d)[0],f=this;
if(g){var e=b("<td></td>");
a.buttonicon.toString().toUpperCase()=="NONE"?b(e).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'>"+a.caption+"</div>"):b(e).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'><span class='ui-icon "+a.buttonicon+"'></span>"+a.caption+"</div>");
a.id&&b(e).attr("id",a.id);
if(a.position=="first"){g.rows[0].cells.length===0?b("tr",g).append(e):b("tr td:eq(0)",g).before(e)
}else{b("tr",g).append(e)
}b(e,g).attr("title",a.title||"").click(function(h){b(this).hasClass("ui-state-disabled")||b.isFunction(a.onClickButton)&&a.onClickButton.call(f,h);
return false
}).hover(function(){b(this).hasClass("ui-state-disabled")||b(this).addClass("ui-state-hover")
},function(){b(this).removeClass("ui-state-hover")
})
}}})
},navSeparatorAdd:function(d,a){a=b.extend({sepclass:"ui-separator",sepcontent:""},a||{});
return this.each(function(){if(this.grid){if(d.indexOf("#")!==0){d="#"+d
}var f=b(".navtable",d)[0];
if(f){var e="<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='"+a.sepclass+"'></span>"+a.sepcontent+"</td>";
b("tr",f).append(e)
}}})
},GridToForm:function(d,a){return this.each(function(){var g=this;
if(g.grid){var f=b(g).jqGrid("getRowData",d);
if(f){for(var e in f){b("[name="+e+"]",a).is("input:radio")||b("[name="+e+"]",a).is("input:checkbox")?b("[name="+e+"]",a).each(function(){b(this).val()==f[e]?b(this).attr("checked","checked"):b(this).attr("checked","")
}):b("[name="+e+"]",a).val(f[e])
}}}})
},FormToGrid:function(d,a,g,e){return this.each(function(){var h=this;
if(h.grid){g||(g="set");
e||(e="first");
var j=b(a).serializeArray(),f={};
b.each(j,function(k,l){f[l.name]=l.value
});
if(g=="add"){b(h).jqGrid("addRowData",d,f,e)
}else{g=="set"&&b(h).jqGrid("setRowData",d,f)
}}})
}})
})(jQuery);
jQuery.fn.searchFilter=function(a,c){function b(R,P,q){this.$=R;
this.add=function(e){e==null?R.find(".ui-add-last").click():R.find(".sf:eq("+e+") .ui-add").click();
return this
};
this.del=function(e){e==null?R.find(".sf:last .ui-del").click():R.find(".sf:eq("+e+") .ui-del").click();
return this
};
this.search=function(){R.find(".ui-search").click();
return this
};
this.reset=function(e){if(e===undefined){e=false
}R.find(".ui-reset").trigger("click",[e]);
return this
};
this.close=function(){R.find(".ui-closer").click();
return this
};
if(P!=null){function J(){jQuery(this).toggleClass("ui-state-hover");
return false
}function I(e){jQuery(this).toggleClass("ui-state-active",e.type=="mousedown");
return false
}function O(f,e){return"<option value='"+f+"'>"+e+"</option>"
}function j(f,e,l){return"<select class='"+f+"'"+(l?" style='display:none;'":"")+">"+e+"</select>"
}function B(f,e){f=R.find("tr.sf td.data "+f);
f[0]!=null&&e(f)
}function u(f,e){var l=R.find("tr.sf td.data "+f);
l[0]!=null&&jQuery.each(e,function(){this.data!=null?l.bind(this.type,this.data,this.fn):l.bind(this.type,this.fn)
})
}var Q=jQuery.extend({},jQuery.fn.searchFilter.defaults,q),N=-1,K="";
jQuery.each(Q.groupOps,function(){K+=O(this.op,this.text)
});
K="<select name='groupOp'>"+K+"</select>";
R.html("").addClass("ui-searchFilter").append("<div class='ui-widget-overlay' style='z-index: -1'>&#160;</div><table class='ui-widget-content ui-corner-all'><thead><tr><td colspan='5' class='ui-widget-header ui-corner-all' style='line-height: 18px;'><div class='ui-closer ui-state-default ui-corner-all ui-helper-clearfix' style='float: right;'><span class='ui-icon ui-icon-close'></span></div>"+Q.windowTitle+"</td></tr></thead><tbody><tr class='sf'><td class='fields'></td><td class='ops'></td><td class='data'></td><td><div class='ui-del ui-state-default ui-corner-all'><span class='ui-icon ui-icon-minus'></span></div></td><td><div class='ui-add ui-state-default ui-corner-all'><span class='ui-icon ui-icon-plus'></span></div></td></tr><tr><td colspan='5' class='divider'><div>&#160;</div></td></tr></tbody><tfoot><tr><td colspan='3'><span class='ui-reset ui-state-default ui-corner-all' style='display: inline-block; float: left;'><span class='ui-icon ui-icon-arrowreturnthick-1-w' style='float: left;'></span><span style='line-height: 18px; padding: 0 7px 0 3px;'>"+Q.resetText+"</span></span><span class='ui-search ui-state-default ui-corner-all' style='display: inline-block; float: right;'><span class='ui-icon ui-icon-search' style='float: left;'></span><span style='line-height: 18px; padding: 0 7px 0 3px;'>"+Q.searchText+"</span></span><span class='matchText'>"+Q.matchText+"</span> "+K+" <span class='rulesText'>"+Q.rulesText+"</span></td><td>&#160;</td><td><div class='ui-add-last ui-state-default ui-corner-all'><span class='ui-icon ui-icon-plusthick'></span></div></td></tr></tfoot></table>");
var h=R.find("tr.sf"),k=h.find("td.fields"),g=h.find("td.ops"),M=h.find("td.data"),H="";
jQuery.each(Q.operators,function(){H+=O(this.op,this.text)
});
H=j("default",H,true);
g.append(H);
M.append("<input type='text' class='default' style='display:none;' />");
var A="",d=false,L=false;
jQuery.each(P,function(f){A+=O(this.itemval,this.text);
if(this.ops!=null){d=true;
var e="";
jQuery.each(this.ops,function(){e+=O(this.op,this.text)
});
e=j("field"+f,e,true);
g.append(e)
}if(this.dataUrl!=null){if(f>N){N=f
}L=true;
var n=this.dataEvents,o=this.dataInit,m=this.buildSelect;
jQuery.ajax(jQuery.extend({url:this.dataUrl,complete:function(p){p=m!=null?jQuery("<div />").append(m(p)):jQuery("<div />").append(p.responseText);
p.find("select").addClass("field"+f).hide();
M.append(p.html());
o&&B(".field"+f,o);
n&&u(".field"+f,n);
f==N&&R.find("tr.sf td.fields select[name='field']").change()
}},Q.ajaxSelectOptions))
}else{if(this.dataValues!=null){L=true;
var l="";
jQuery.each(this.dataValues,function(){l+=O(this.value,this.text)
});
l=j("field"+f,l,true);
M.append(l)
}else{if(this.dataEvents!=null||this.dataInit!=null){L=true;
l="<input type='text' class='field"+f+"' />";
M.append(l)
}}}this.dataInit!=null&&f!=N&&B(".field"+f,this.dataInit);
this.dataEvents!=null&&f!=N&&u(".field"+f,this.dataEvents)
});
A="<select name='field'>"+A+"</select>";
k.append(A);
P=k.find("select[name='field']");
d?P.change(function(f){var e=f.target.selectedIndex;
f=jQuery(f.target).parents("tr.sf").find("td.ops");
f.find("select").removeAttr("name").hide();
e=f.find(".field"+e);
if(e[0]==null){e=f.find(".default")
}e.attr("name","op").show();
return false
}):g.find(".default").attr("name","op").show();
L?P.change(function(f){var e=f.target.selectedIndex;
f=jQuery(f.target).parents("tr.sf").find("td.data");
f.find("select,input").removeClass("vdata").hide();
e=f.find(".field"+e);
if(e[0]==null){e=f.find(".default")
}e.show().addClass("vdata");
return false
}):M.find(".default").show().addClass("vdata");
if(d||L){P.change()
}R.find(".ui-state-default").hover(J,J).mousedown(I).mouseup(I);
R.find(".ui-closer").click(function(){Q.onClose(jQuery(R.selector));
return false
});
R.find(".ui-del").click(function(e){e=jQuery(e.target).parents(".sf");
if(e.siblings(".sf").length>0){Q.datepickerFix===true&&jQuery.fn.datepicker!==undefined&&e.find(".hasDatepicker").datepicker("destroy");
e.remove()
}else{e.find("select[name='field']")[0].selectedIndex=0;
e.find("select[name='op']")[0].selectedIndex=0;
e.find(".data input").val("");
e.find(".data select").each(function(){this.selectedIndex=0
});
e.find("select[name='field']").change(function(f){f.stopPropagation()
})
}return false
});
R.find(".ui-add").click(function(f){f=jQuery(f.target).parents(".sf");
var e=f.clone(true).insertAfter(f);
e.find(".ui-state-default").removeClass("ui-state-hover ui-state-active");
if(Q.clone){e.find("select[name='field']")[0].selectedIndex=f.find("select[name='field']")[0].selectedIndex;
if(e.find("select[name='op']")[0]!=null){e.find("select[name='op']").focus()[0].selectedIndex=f.find("select[name='op']")[0].selectedIndex
}var l=e.find("select.vdata");
if(l[0]!=null){l[0].selectedIndex=f.find("select.vdata")[0].selectedIndex
}}else{e.find(".data input").val("");
e.find("select[name='field']").focus()
}Q.datepickerFix===true&&jQuery.fn.datepicker!==undefined&&f.find(".hasDatepicker").each(function(){var m=jQuery.data(this,"datepicker").settings;
e.find("#"+this.id).unbind().removeAttr("id").removeClass("hasDatepicker").datepicker(m)
});
e.find("select[name='field']").change(function(m){m.stopPropagation()
});
return false
});
R.find(".ui-search").click(function(){var f=jQuery(R.selector),e,l=f.find("select[name='groupOp'] :selected").val();
e=Q.stringResult?'{"groupOp":"'+l+'","rules":[':{groupOp:l,rules:[]};
f.find(".sf").each(function(p){var o=jQuery(this).find("select[name='field'] :selected").val(),m=jQuery(this).find("select[name='op'] :selected").val(),n=jQuery(this).find("input.vdata,select.vdata :selected").val();
n+="";
n=n.replace(/\\/g,"\\\\").replace(/\"/g,'\\"');
if(Q.stringResult){if(p>0){e+=","
}e+='{"field":"'+o+'",';
e+='"op":"'+m+'",';
e+='"data":"'+n+'"}'
}else{e.rules.push({field:o,op:m,data:n})
}});
if(Q.stringResult){e+="]}"
}Q.onSearch(e);
return false
});
R.find(".ui-reset").click(function(f,e){f=jQuery(R.selector);
f.find(".ui-del").click();
f.find("select[name='groupOp']")[0].selectedIndex=0;
Q.onReset(e);
return false
});
R.find(".ui-add-last").click(function(){var f=jQuery(R.selector+" .sf:last"),e=f.clone(true).insertAfter(f);
e.find(".ui-state-default").removeClass("ui-state-hover ui-state-active");
e.find(".data input").val("");
e.find("select[name='field']").focus();
Q.datepickerFix===true&&jQuery.fn.datepicker!==undefined&&f.find(".hasDatepicker").each(function(){var l=jQuery.data(this,"datepicker").settings;
e.find("#"+this.id).unbind().removeAttr("id").removeClass("hasDatepicker").datepicker(l)
});
e.find("select[name='field']").change(function(l){l.stopPropagation()
});
return false
});
this.setGroupOp=function(f){selDOMobj=R.find("select[name='groupOp']")[0];
var e={},l=selDOMobj.options.length,m;
for(m=0;
m<l;
m++){e[selDOMobj.options[m].value]=m
}selDOMobj.selectedIndex=e[f];
jQuery(selDOMobj).change(function(n){n.stopPropagation()
})
};
this.setFilter=function(v){var t=v.sfref;
v=v.filter;
var r=[],s,p,n,o,m={};
selDOMobj=t.find("select[name='field']")[0];
s=0;
for(n=selDOMobj.options.length;
s<n;
s++){m[selDOMobj.options[s].value]={index:s,ops:{}};
r.push(selDOMobj.options[s].value)
}s=0;
for(n=r.length;
s<n;
s++){if(selDOMobj=t.find(".ops > select[class='field"+s+"']")[0]){p=0;
for(o=selDOMobj.options.length;
p<o;
p++){m[r[s]].ops[selDOMobj.options[p].value]=p
}}if(selDOMobj=t.find(".data > select[class='field"+s+"']")[0]){m[r[s]].data={};
p=0;
for(o=selDOMobj.options.length;
p<o;
p++){m[r[s]].data[selDOMobj.options[p].value]=p
}}}var w,e,l,f;
r=v.field;
if(m[r]){w=m[r].index
}if(w!=null){e=m[r].ops[v.op];
if(e===undefined){s=0;
for(n=q.operators.length;
s<n;
s++){if(q.operators[s].op==v.op){e=s;
break
}}}l=v.data;
f=m[r].data==null?-1:m[r].data[l]
}if(w!=null&&e!=null&&f!=null){t.find("select[name='field']")[0].selectedIndex=w;
t.find("select[name='field']").change();
t.find("select[name='op']")[0].selectedIndex=e;
t.find("input.vdata").val(l);
if(t=t.find("select.vdata")[0]){t.selectedIndex=f
}return true
}else{return false
}}
}}return new b(this,a,c)
};
jQuery.fn.searchFilter.version="1.2.9";
jQuery.fn.searchFilter.defaults={clone:true,datepickerFix:true,onReset:function(a){alert("Reset Clicked. Data Returned: "+a)
},onSearch:function(a){alert("Search Clicked. Data Returned: "+a)
},onClose:function(a){a.hide()
},groupOps:[{op:"AND",text:"all"},{op:"OR",text:"any"}],operators:[{op:"eq",text:"is equal to"},{op:"ne",text:"is not equal to"},{op:"lt",text:"is less than"},{op:"le",text:"is less or equal to"},{op:"gt",text:"is greater than"},{op:"ge",text:"is greater or equal to"},{op:"in",text:"is in"},{op:"ni",text:"is not in"},{op:"bw",text:"begins with"},{op:"bn",text:"does not begin with"},{op:"ew",text:"ends with"},{op:"en",text:"does not end with"},{op:"cn",text:"contains"},{op:"nc",text:"does not contain"}],matchText:"match",rulesText:"rules",resetText:"Reset",searchText:"Search",stringResult:true,windowTitle:"Search Rules",ajaxSelectOptions:{}};
(function(b){b.jgrid.extend({editRow:function(k,m,h,g,e,a,p,l,j){return this.each(function(){var c=this,f,d,n=0,u=null,s={},o,r;
if(c.grid){o=b(c).jqGrid("getInd",k,true);
if(o!==false){if((b(o).attr("editable")||"0")=="0"&&!b(o).hasClass("not-editable-row")){r=c.p.colModel;
b("td",o).each(function(w){f=r[w].name;
var t=c.p.treeGrid===true&&f==c.p.ExpandColumn;
if(t){d=b("span:first",this).html()
}else{try{d=b.unformat(this,{rowId:k,colModel:r[w]},w)
}catch(q){d=b(this).html()
}}if(f!="cb"&&f!="subgrid"&&f!="rn"){if(c.p.autoencode){d=b.jgrid.htmlDecode(d)
}s[f]=d;
if(r[w].editable===true){if(u===null){u=w
}t?b("span:first",this).html(""):b(this).html("");
var x=b.extend({},r[w].editoptions||{},{id:k+"_"+f,name:f});
if(!r[w].edittype){r[w].edittype="text"
}x=createEl(r[w].edittype,x,d,true,b.extend({},b.jgrid.ajaxOptions,c.p.ajaxSelectOptions||{}));
b(x).addClass("editable");
t?b("span:first",this).append(x):b(this).append(x);
r[w].edittype=="select"&&r[w].editoptions.multiple===true&&b.browser.msie&&b(x).width(b(x).width());
n++
}}});
if(n>0){s.id=k;
c.p.savedRow.push(s);
b(o).attr("editable","1");
b("td:eq("+u+") input",o).focus();
m===true&&b(o).bind("keydown",function(q){q.keyCode===27&&b(c).jqGrid("restoreRow",k,j);
if(q.keyCode===13){if(q.target.tagName=="TEXTAREA"){return true
}b(c).jqGrid("saveRow",k,g,e,a,p,l,j);
return false
}q.stopPropagation()
});
b.isFunction(h)&&h.call(c,k)
}}}}})
},saveRow:function(h,a,c,j,g,f,e){return this.each(function(){var x=this,w,y={},o={},n,A,m,d;
if(x.grid){d=b(x).jqGrid("getInd",h,true);
if(d!==false){n=b(d).attr("editable");
c=c?c:x.p.editurl;
if(n==="1"){var s;
b("td",d).each(function(l){s=x.p.colModel[l];
w=s.name;
if(w!="cb"&&w!="subgrid"&&s.editable===true&&w!="rn"){switch(s.edittype){case"checkbox":var q=["Yes","No"];
if(s.editoptions){q=s.editoptions.value.split(":")
}y[w]=b("input",this).attr("checked")?q[0]:q[1];
break;
case"text":case"password":case"textarea":case"button":y[w]=b("input, textarea",this).val();
break;
case"select":if(s.editoptions.multiple){q=b("select",this);
var k=[];
y[w]=b(q).val();
y[w]=y[w]?y[w].join(","):"";
b("select > option:selected",this).each(function(v,t){k[v]=b(t).text()
});
o[w]=k.join(",")
}else{y[w]=b("select>option:selected",this).val();
o[w]=b("select>option:selected",this).text()
}if(s.formatter&&s.formatter=="select"){o={}
}break;
case"custom":try{if(s.editoptions&&b.isFunction(s.editoptions.custom_value)){y[w]=s.editoptions.custom_value.call(x,b(".customelement",this),"get");
if(y[w]===undefined){throw"e2"
}}else{throw"e1"
}}catch(p){p=="e1"&&info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose);
p=="e2"?info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):info_dialog(jQuery.jgrid.errors.errcap,p.message,jQuery.jgrid.edit.bClose)
}break
}m=checkValues(y[w],l,x);
if(m[0]===false){m[1]=y[w]+" "+m[1];
return false
}if(x.p.autoencode){y[w]=b.jgrid.htmlEncode(y[w])
}}});
if(m[0]===false){try{var u=findPos(b("#"+b.jgrid.jqID(h),x.grid.bDiv)[0]);
info_dialog(b.jgrid.errors.errcap,m[1],b.jgrid.edit.bClose,{left:u[0],top:u[1]})
}catch(r){alert(m[1])
}}else{if(y){var z;
u=x.p.prmNames;
z=u.oper;
n=u.id;
y[z]=u.editoper;
y[n]=h;
if(typeof x.p.inlineData=="undefined"){x.p.inlineData={}
}if(typeof j=="undefined"){j={}
}y=b.extend({},y,x.p.inlineData,j)
}if(c=="clientArray"){y=b.extend({},y,o);
x.p.autoencode&&b.each(y,function(k,l){y[k]=b.jgrid.htmlDecode(l)
});
n=b(x).jqGrid("setRowData",h,y);
b(d).attr("editable","0");
for(u=0;
u<x.p.savedRow.length;
u++){if(x.p.savedRow[u].id==h){A=u;
break
}}A>=0&&x.p.savedRow.splice(A,1);
b.isFunction(g)&&g.call(x,h,n)
}else{b("#lui_"+x.p.id).show();
b.ajax(b.extend({url:c,data:b.isFunction(x.p.serializeRowData)?x.p.serializeRowData.call(x,y):y,type:"POST",complete:function(k,l){b("#lui_"+x.p.id).hide();
if(l==="success"){if((b.isFunction(a)?a.call(x,k):true)===true){x.p.autoencode&&b.each(y,function(p,q){y[p]=b.jgrid.htmlDecode(q)
});
y=b.extend({},y,o);
b(x).jqGrid("setRowData",h,y);
b(d).attr("editable","0");
for(l=0;
l<x.p.savedRow.length;
l++){if(x.p.savedRow[l].id==h){A=l;
break
}}A>=0&&x.p.savedRow.splice(A,1);
b.isFunction(g)&&g.call(x,h,k)
}else{b.isFunction(f)&&f.call(x,h,k,l);
b(x).jqGrid("restoreRow",h,e)
}}},error:function(k,l){b("#lui_"+x.p.id).hide();
b.isFunction(f)?f.call(x,h,k,l):alert("Error Row: "+h+" Result: "+k.status+":"+k.statusText+" Status: "+l);
b(x).jqGrid("restoreRow",h,e)
}},b.jgrid.ajaxOptions,x.p.ajaxRowOptions||{}))
}b(d).unbind("keydown")
}}}}})
},restoreRow:function(c,a){return this.each(function(){var d=this,j,g,f={};
if(d.grid){g=b(d).jqGrid("getInd",c,true);
if(g!==false){for(var e=0;
e<d.p.savedRow.length;
e++){if(d.p.savedRow[e].id==c){j=e;
break
}}if(j>=0){if(b.isFunction(b.fn.datepicker)){try{b("input.hasDatepicker","#"+b.jgrid.jqID(g.id)).datepicker("hide")
}catch(h){}}b.each(d.p.colModel,function(){if(this.editable===true&&this.name in d.p.savedRow[j]){f[this.name]=d.p.savedRow[j][this.name]
}});
b(d).jqGrid("setRowData",c,f);
b(g).attr("editable","0").unbind("keydown");
d.p.savedRow.splice(j,1)
}b.isFunction(a)&&a.call(d,c)
}}})
}})
})(jQuery);
(function(a){a.jgrid.extend({editCell:function(f,c,b){return this.each(function(){var p=this,m,o,n;
if(!(!p.grid||p.p.cellEdit!==true)){c=parseInt(c,10);
p.p.selrow=p.rows[f].id;
p.p.knv||a(p).jqGrid("GridNav");
if(p.p.savedRow.length>0){if(b===true){if(f==p.p.iRow&&c==p.p.iCol){return
}}a(p).jqGrid("saveCell",p.p.savedRow[0].id,p.p.savedRow[0].ic)
}else{window.setTimeout(function(){a("#"+p.p.knv).attr("tabindex","-1").focus()
},0)
}m=p.p.colModel[c].name;
if(!(m=="subgrid"||m=="cb"||m=="rn")){n=a("td:eq("+c+")",p.rows[f]);
if(p.p.colModel[c].editable===true&&b===true&&!n.hasClass("not-editable-cell")){if(parseInt(p.p.iCol,10)>=0&&parseInt(p.p.iRow,10)>=0){a("td:eq("+p.p.iCol+")",p.rows[p.p.iRow]).removeClass("edit-cell ui-state-highlight");
a(p.rows[p.p.iRow]).removeClass("selected-row ui-state-hover")
}a(n).addClass("edit-cell ui-state-highlight");
a(p.rows[f]).addClass("selected-row ui-state-hover");
try{o=a.unformat(n,{rowId:p.rows[f].id,colModel:p.p.colModel[c]},c)
}catch(d){o=a(n).html()
}if(p.p.autoencode){o=a.jgrid.htmlDecode(o)
}if(!p.p.colModel[c].edittype){p.p.colModel[c].edittype="text"
}p.p.savedRow.push({id:f,ic:c,name:m,v:o});
if(a.isFunction(p.p.formatCell)){var e=p.p.formatCell.call(p,p.rows[f].id,m,o,f,c);
if(e!==undefined){o=e
}}e=a.extend({},p.p.colModel[c].editoptions||{},{id:f+"_"+m,name:m});
var l=createEl(p.p.colModel[c].edittype,e,o,true,a.extend({},a.jgrid.ajaxOptions,p.p.ajaxSelectOptions||{}));
a.isFunction(p.p.beforeEditCell)&&p.p.beforeEditCell.call(p,p.rows[f].id,m,o,f,c);
a(n).html("").append(l).attr("tabindex","0");
window.setTimeout(function(){a(l).focus()
},0);
a("input, select, textarea",n).bind("keydown",function(g){if(g.keyCode===27){if(a("input.hasDatepicker",n).length>0){a(".ui-datepicker").is(":hidden")?a(p).jqGrid("restoreCell",f,c):a("input.hasDatepicker",n).datepicker("hide")
}else{a(p).jqGrid("restoreCell",f,c)
}}g.keyCode===13&&a(p).jqGrid("saveCell",f,c);
if(g.keyCode==9){if(p.grid.hDiv.loading){return false
}else{g.shiftKey?a(p).jqGrid("prevCell",f,c):a(p).jqGrid("nextCell",f,c)
}}g.stopPropagation()
});
a.isFunction(p.p.afterEditCell)&&p.p.afterEditCell.call(p,p.rows[f].id,m,o,f,c)
}else{if(parseInt(p.p.iCol,10)>=0&&parseInt(p.p.iRow,10)>=0){a("td:eq("+p.p.iCol+")",p.rows[p.p.iRow]).removeClass("edit-cell ui-state-highlight");
a(p.rows[p.p.iRow]).removeClass("selected-row ui-state-hover")
}n.addClass("edit-cell ui-state-highlight");
a(p.rows[f]).addClass("selected-row ui-state-hover");
if(a.isFunction(p.p.onSelectCell)){o=n.html().replace(/\&#160\;/ig,"");
p.p.onSelectCell.call(p,p.rows[f].id,m,o,f,c)
}}p.p.iCol=c;
p.p.iRow=f
}}})
},saveCell:function(c,b){return this.each(function(){var C=this,B;
if(!(!C.grid||C.p.cellEdit!==true)){B=C.p.savedRow.length>=1?0:null;
if(B!==null){var y=a("td:eq("+b+")",C.rows[c]),A,z,v=C.p.colModel[b],w=v.name,x=a.jgrid.jqID(w);
switch(v.edittype){case"select":if(v.editoptions.multiple){x=a("#"+c+"_"+x,C.rows[c]);
var u=[];
if(A=a(x).val()){A.join(",")
}else{A=""
}a("option:selected",x).each(function(f,g){u[f]=a(g).text()
});
z=u.join(",")
}else{A=a("#"+c+"_"+x+">option:selected",C.rows[c]).val();
z=a("#"+c+"_"+x+">option:selected",C.rows[c]).text()
}if(v.formatter){z=A
}break;
case"checkbox":var p=["Yes","No"];
if(v.editoptions){p=v.editoptions.value.split(":")
}z=A=a("#"+c+"_"+x,C.rows[c]).attr("checked")?p[0]:p[1];
break;
case"password":case"text":case"textarea":case"button":z=A=a("#"+c+"_"+x,C.rows[c]).val();
break;
case"custom":try{if(v.editoptions&&a.isFunction(v.editoptions.custom_value)){A=v.editoptions.custom_value.call(C,a(".customelement",y),"get");
if(A===undefined){throw"e2"
}else{z=A
}}else{throw"e1"
}}catch(e){e=="e1"&&info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose);
e=="e2"?info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):info_dialog(jQuery.jgrid.errors.errcap,e.message,jQuery.jgrid.edit.bClose)
}break
}if(z!=C.p.savedRow[B].v){if(a.isFunction(C.p.beforeSaveCell)){if(B=C.p.beforeSaveCell.call(C,C.rows[c].id,w,A,c,b)){A=B
}}var d=checkValues(A,b,C);
if(d[0]===true){B={};
if(a.isFunction(C.p.beforeSubmitCell)){(B=C.p.beforeSubmitCell.call(C,C.rows[c].id,w,A,c,b))||(B={})
}a("input.hasDatepicker",y).length>0&&a("input.hasDatepicker",y).datepicker("hide");
if(C.p.cellsubmit=="remote"){if(C.p.cellurl){var m={};
if(C.p.autoencode){A=a.jgrid.htmlEncode(A)
}m[w]=A;
p=C.p.prmNames;
v=p.id;
x=p.oper;
m[v]=C.rows[c].id;
m[x]=p.editoper;
m=a.extend(B,m);
a("#lui_"+C.p.id).show();
C.grid.hDiv.loading=true;
a.ajax(a.extend({url:C.p.cellurl,data:a.isFunction(C.p.serializeCellData)?C.p.serializeCellData.call(C,m):m,type:"POST",complete:function(f,g){a("#lui_"+C.p.id).hide();
C.grid.hDiv.loading=false;
if(g=="success"){if(a.isFunction(C.p.afterSubmitCell)){f=C.p.afterSubmitCell.call(C,f,m.id,w,A,c,b);
if(f[0]===true){a(y).empty();
a(C).jqGrid("setCell",C.rows[c].id,b,z,false,false,true);
a(y).addClass("dirty-cell");
a(C.rows[c]).addClass("edited");
a.isFunction(C.p.afterSaveCell)&&C.p.afterSaveCell.call(C,C.rows[c].id,w,A,c,b);
C.p.savedRow.splice(0,1)
}else{info_dialog(a.jgrid.errors.errcap,f[1],a.jgrid.edit.bClose);
a(C).jqGrid("restoreCell",c,b)
}}else{a(y).empty();
a(C).jqGrid("setCell",C.rows[c].id,b,z,false,false,true);
a(y).addClass("dirty-cell");
a(C.rows[c]).addClass("edited");
a.isFunction(C.p.afterSaveCell)&&C.p.afterSaveCell.call(C,C.rows[c].id,w,A,c,b);
C.p.savedRow.splice(0,1)
}}},error:function(f,g){a("#lui_"+C.p.id).hide();
C.grid.hDiv.loading=false;
a.isFunction(C.p.errorCell)?C.p.errorCell.call(C,f,g):info_dialog(a.jgrid.errors.errcap,f.status+" : "+f.statusText+"<br/>"+g,a.jgrid.edit.bClose);
a(C).jqGrid("restoreCell",c,b)
}},a.jgrid.ajaxOptions,C.p.ajaxCellOptions||{}))
}else{try{info_dialog(a.jgrid.errors.errcap,a.jgrid.errors.nourl,a.jgrid.edit.bClose);
a(C).jqGrid("restoreCell",c,b)
}catch(E){}}}if(C.p.cellsubmit=="clientArray"){a(y).empty();
a(C).jqGrid("setCell",C.rows[c].id,b,z,false,false,true);
a(y).addClass("dirty-cell");
a(C.rows[c]).addClass("edited");
a.isFunction(C.p.afterSaveCell)&&C.p.afterSaveCell.call(C,C.rows[c].id,w,A,c,b);
C.p.savedRow.splice(0,1)
}}else{try{window.setTimeout(function(){info_dialog(a.jgrid.errors.errcap,A+" "+d[1],a.jgrid.edit.bClose)
},100);
a(C).jqGrid("restoreCell",c,b)
}catch(D){}}}else{a(C).jqGrid("restoreCell",c,b)
}}a.browser.opera?a("#"+C.p.knv).attr("tabindex","-1").focus():window.setTimeout(function(){a("#"+C.p.knv).attr("tabindex","-1").focus()
},0)
}})
},restoreCell:function(c,b){return this.each(function(){var d=this,j;
if(!(!d.grid||d.p.cellEdit!==true)){j=d.p.savedRow.length>=1?0:null;
if(j!==null){var e=a("td:eq("+b+")",d.rows[c]);
if(a.isFunction(a.fn.datepicker)){try{a("input.hasDatepicker",e).datepicker("hide")
}catch(g){}}a(e).empty().attr("tabindex","-1");
a(d).jqGrid("setCell",d.rows[c].id,b,d.p.savedRow[j].v,false,false,true);
a.isFunction(d.p.afterRestoreCell)&&d.p.afterRestoreCell.call(d,d.rows[c].id,d.p.savedRow[j].v,c,b);
d.p.savedRow.splice(0,1)
}window.setTimeout(function(){a("#"+d.p.knv).attr("tabindex","-1").focus()
},0)
}})
},nextCell:function(c,b){return this.each(function(){var d=this,f=false;
if(!(!d.grid||d.p.cellEdit!==true)){for(var e=b+1;
e<d.p.colModel.length;
e++){if(d.p.colModel[e].editable===true){f=e;
break
}}if(f!==false){a(d).jqGrid("editCell",c,f,true)
}else{d.p.savedRow.length>0&&a(d).jqGrid("saveCell",c,b)
}}})
},prevCell:function(c,b){return this.each(function(){var d=this,f=false;
if(!(!d.grid||d.p.cellEdit!==true)){for(var e=b-1;
e>=0;
e--){if(d.p.colModel[e].editable===true){f=e;
break
}}if(f!==false){a(d).jqGrid("editCell",c,f,true)
}else{d.p.savedRow.length>0&&a(d).jqGrid("saveCell",c,b)
}}})
},GridNav:function(){return this.each(function(){function l(h,d,e){if(e.substr(0,1)=="v"){var f=a(b.grid.bDiv)[0].clientHeight,c=a(b.grid.bDiv)[0].scrollTop,p=b.rows[h].offsetTop+b.rows[h].clientHeight,o=b.rows[h].offsetTop;
if(e=="vd"){if(p>=f){a(b.grid.bDiv)[0].scrollTop=a(b.grid.bDiv)[0].scrollTop+b.rows[h].clientHeight
}}if(e=="vu"){if(o<c){a(b.grid.bDiv)[0].scrollTop=a(b.grid.bDiv)[0].scrollTop-b.rows[h].clientHeight
}}}if(e=="h"){e=a(b.grid.bDiv)[0].clientWidth;
f=a(b.grid.bDiv)[0].scrollLeft;
c=b.rows[h].cells[d].offsetLeft;
if(b.rows[h].cells[d].offsetLeft+b.rows[h].cells[d].clientWidth>=e+parseInt(f,10)){a(b.grid.bDiv)[0].scrollLeft=a(b.grid.bDiv)[0].scrollLeft+b.rows[h].cells[d].clientWidth
}else{if(c<f){a(b.grid.bDiv)[0].scrollLeft=a(b.grid.bDiv)[0].scrollLeft-b.rows[h].cells[d].clientWidth
}}}}function k(f,c){var d,e;
if(c=="lft"){d=f+1;
for(e=f;
e>=0;
e--){if(b.p.colModel[e].hidden!==true){d=e;
break
}}}if(c=="rgt"){d=f-1;
for(e=f;
e<b.p.colModel.length;
e++){if(b.p.colModel[e].hidden!==true){d=e;
break
}}}return d
}var b=this;
if(!(!b.grid||b.p.cellEdit!==true)){b.p.knv=b.p.id+"_kn";
var m=a("<span style='width:0px;height:0px;background-color:black;' tabindex='0'><span tabindex='-1' style='width:0px;height:0px;background-color:grey' id='"+b.p.knv+"'></span></span>"),g,j;
a(m).insertBefore(b.grid.cDiv);
a("#"+b.p.knv).focus().keydown(function(c){j=c.keyCode;
if(b.p.direction=="rtl"){if(j==37){j=39
}else{if(j==39){j=37
}}}switch(j){case 38:if(b.p.iRow-1>0){l(b.p.iRow-1,b.p.iCol,"vu");
a(b).jqGrid("editCell",b.p.iRow-1,b.p.iCol,false)
}break;
case 40:if(b.p.iRow+1<=b.rows.length-1){l(b.p.iRow+1,b.p.iCol,"vd");
a(b).jqGrid("editCell",b.p.iRow+1,b.p.iCol,false)
}break;
case 37:if(b.p.iCol-1>=0){g=k(b.p.iCol-1,"lft");
l(b.p.iRow,g,"h");
a(b).jqGrid("editCell",b.p.iRow,g,false)
}break;
case 39:if(b.p.iCol+1<=b.p.colModel.length-1){g=k(b.p.iCol+1,"rgt");
l(b.p.iRow,g,"h");
a(b).jqGrid("editCell",b.p.iRow,g,false)
}break;
case 13:parseInt(b.p.iCol,10)>=0&&parseInt(b.p.iRow,10)>=0&&a(b).jqGrid("editCell",b.p.iRow,b.p.iCol,true);
break
}return false
})
}})
},getChangedCells:function(c){var b=[];
c||(c="all");
this.each(function(){var d=this,e;
!d.grid||d.p.cellEdit!==true||a(d.rows).each(function(g){var j={};
if(a(this).hasClass("edited")){a("td",this).each(function(l){e=d.p.colModel[l].name;
if(e!=="cb"&&e!=="subgrid"){if(c=="dirty"){if(a(this).hasClass("dirty-cell")){try{j[e]=a.unformat(this,{rowId:d.rows[g].id,colModel:d.p.colModel[l]},l)
}catch(f){j[e]=a.jgrid.htmlDecode(a(this).html())
}}}else{try{j[e]=a.unformat(this,{rowId:d.rows[g].id,colModel:d.p.colModel[l]},l)
}catch(h){j[e]=a.jgrid.htmlDecode(a(this).html())
}}}});
j.id=this.id;
b.push(j)
}})
});
return b
}})
})(jQuery);
(function(u){u.fn.jqm=function(b){var e={overlay:50,closeoverlay:true,overlayClass:"jqmOverlay",closeClass:"jqmClose",trigger:".jqModal",ajax:t,ajaxText:"",target:t,modal:t,toTop:t,onShow:t,onHide:t,onLoad:t};
return this.each(function(){if(this._jqm){return m[this._jqm].c=u.extend({},m[this._jqm].c,b)
}h++;
this._jqm=h;
m[h]={c:u.extend(e,u.jqm.params,b),a:t,w:u(this).addClass("jqmID"+h),s:h};
e.trigger&&u(this).jqmAddTrigger(e.trigger)
})
};
u.fn.jqmAddClose=function(b){return f(this,b,"jqmHide")
};
u.fn.jqmAddTrigger=function(b){return f(this,b,"jqmShow")
};
u.fn.jqmShow=function(b){return this.each(function(){u.jqm.open(this._jqm,b)
})
};
u.fn.jqmHide=function(b){return this.each(function(){u.jqm.close(this._jqm,b)
})
};
u.jqm={hash:{},open:function(e,o){var q=m[e],p=q.c,l="."+p.closeClass,n=parseInt(q.w.css("z-index"));
n=n>0?n:3000;
var k=u("<div></div>").css({height:"100%",width:"100%",position:"fixed",left:0,top:0,"z-index":n-1,opacity:p.overlay/100});
if(q.a){return t
}q.t=o;
q.a=true;
q.w.css("z-index",n);
if(p.modal){j[0]||setTimeout(function(){d("bind")
},1);
j.push(e)
}else{if(p.overlay>0){p.closeoverlay&&q.w.jqmAddClose(k)
}else{k=t
}}q.o=k?k.addClass(p.overlayClass).prependTo("body"):t;
if(c){u("html,body").css({height:"100%",width:"100%"});
if(k){k=k.css({position:"absolute"})[0];
for(var b in {Top:1,Left:1}){k.style.setExpression(b.toLowerCase(),"(_=(document.documentElement.scroll"+b+" || document.body.scroll"+b+"))+'px'")
}}}if(p.ajax){e=p.target||q.w;
n=p.ajax;
e=typeof e=="string"?u(e,q.w):u(e);
n=n.substr(0,1)=="@"?u(o).attr(n.substring(1)):n;
e.html(p.ajaxText).load(n,function(){p.onLoad&&p.onLoad.call(this,q);
l&&q.w.jqmAddClose(u(l,q.w));
a(q)
})
}else{l&&q.w.jqmAddClose(u(l,q.w))
}p.toTop&&q.o&&q.w.before('<span id="jqmP'+q.w[0]._jqm+'"></span>').insertAfter(q.o);
p.onShow?p.onShow(q):q.w.show();
a(q);
return t
},close:function(b){b=m[b];
if(!b.a){return t
}b.a=t;
if(j[0]){j.pop();
j[0]||d("unbind")
}b.c.toTop&&b.o&&u("#jqmP"+b.w[0]._jqm).after(b.w).remove();
if(b.c.onHide){b.c.onHide(b)
}else{b.w.hide();
b.o&&b.o.remove()
}return t
},params:{}};
var h=0,m=u.jqm.hash,j=[],c=u.browser.msie&&u.browser.version=="6.0",t=false,a=function(b){var e=u('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({opacity:0});
if(c){if(b.o){b.o.html('<p style="width:100%;height:100%"/>').prepend(e)
}else{u("iframe.jqm",b.w)[0]||b.w.prepend(e)
}}v(b)
},v=function(b){try{u(":input:visible",b.w)[0].focus()
}catch(e){}},d=function(b){u(document)[b]("keypress",g)[b]("keydown",g)[b]("mousedown",g)
},g=function(b){var e=m[j[j.length-1]];
(b=!u(b.target).parents(".jqmID"+e.s)[0])&&v(e);
return !b
},f=function(b,e,k){return b.each(function(){var l=this._jqm;
u(e).each(function(){if(!this[k]){this[k]=[];
u(this).click(function(){for(var n in {jqmShow:1,jqmHide:1}){for(var o in this[n]){m[this[n][o]]&&m[this[n][o]].w[n](this)
}}return t
})
}this[k].push(l)
})
})
}
})(jQuery);
(function(a){a.fn.jqDrag=function(b){return e(this,b,"d")
};
a.fn.jqResize=function(b,c){return e(this,b,"r",c)
};
a.jqDnR={dnr:{},e:0,drag:function(b){if(n.k=="d"){m.css({left:n.X+b.pageX-n.pX,top:n.Y+b.pageY-n.pY})
}else{m.css({width:Math.max(b.pageX-n.pX+n.W,0),height:Math.max(b.pageY-n.pY+n.H,0)});
M1&&j.css({width:Math.max(b.pageX-M1.pX+M1.W,0),height:Math.max(b.pageY-M1.pY+M1.H,0)})
}return false
},stop:function(){a(document).unbind("mousemove",h.drag).unbind("mouseup",h.stop)
}};
var h=a.jqDnR,n=h.dnr,m=h.e,j,e=function(c,d,f,b){return c.each(function(){d=d?a(d,c):c;
d.bind("mousedown",{e:c,k:f},function(q){var k=q.data,l={};
m=k.e;
j=b?a(b):false;
if(m.css("position")!="relative"){try{m.position(l)
}catch(s){}}n={X:l.left||g("left")||0,Y:l.top||g("top")||0,W:g("width")||m[0].scrollWidth||0,H:g("height")||m[0].scrollHeight||0,pX:q.pageX,pY:q.pageY,k:k.k};
M1=j&&k.k!="d"?{X:l.left||f1("left")||0,Y:l.top||f1("top")||0,W:j[0].offsetWidth||f1("width")||0,H:j[0].offsetHeight||f1("height")||0,pX:q.pageX,pY:q.pageY,k:k.k}:false;
try{a("input.hasDatepicker",m[0]).datepicker("hide")
}catch(r){}a(document).mousemove(a.jqDnR.drag).mouseup(a.jqDnR.stop);
return false
})
})
},g=function(b){return parseInt(m.css(b))||false
};
f1=function(b){return parseInt(j.css(b))||false
}
})(jQuery);
(function(a){a.jgrid.extend({setSubGrid:function(){return this.each(function(){var b=this;
b.p.colNames.unshift("");
b.p.colModel.unshift({name:"subgrid",width:a.browser.safari?b.p.subGridWidth+b.p.cellLayout:b.p.subGridWidth,sortable:false,resizable:false,hidedlg:true,search:false,fixed:true});
b=b.p.subGridModel;
if(b[0]){b[0].align=a.extend([],b[0].align||[]);
for(var d=0;
d<b[0].name.length;
d++){b[0].align[d]=b[0].align[d]||"left"
}}})
},addSubGridCell:function(d,h){var b="",g,f;
this.each(function(){b=this.formatCol(d,h);
g=this.p.gridview;
f=this.p.id
});
return g===false?'<td role="grid" aria-describedby="'+f+'_subgrid" class="ui-sgcollapsed sgcollapsed" '+b+"><a href='javascript:void(0);'><span class='ui-icon ui-icon-plus'></span></a></td>":'<td role="grid" aria-describedby="'+f+'_subgrid" '+b+"></td>"
},addSubGrid:function(b,d){return this.each(function(){var j=this;
if(j.grid){var g=function(n,m,o){m=a("<td align='"+j.p.subGridModel[0].align[o]+"'></td>").html(m);
a(n).append(m)
},f=function(r,n){var s,t,p,o=a("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),m=a("<tr></tr>");
for(t=0;
t<j.p.subGridModel[0].name.length;
t++){s=a("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-"+j.p.direction+"'></th>");
a(s).html(j.p.subGridModel[0].name[t]);
a(s).width(j.p.subGridModel[0].width[t]);
a(m).append(s)
}a(o).append(m);
if(r){p=j.p.xmlReader.subgrid;
a(p.root+" "+p.row,r).each(function(){m=a("<tr class='ui-widget-content ui-subtblcell'></tr>");
if(p.repeatitems===true){a(p.cell,this).each(function(v){g(m,a(this).text()||"&#160;",v)
})
}else{var u=j.p.subGridModel[0].mapping||j.p.subGridModel[0].name;
if(u){for(t=0;
t<u.length;
t++){g(m,a(u[t],this).text()||"&#160;",t)
}}}a(o).append(m)
})
}r=a("table:first",j.grid.bDiv).attr("id")+"_";
a("#"+r+n).append(o);
j.grid.hDiv.loading=false;
a("#load_"+j.p.id).hide();
return false
},y=function(t,p){var u,v,s,r,o=a("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),n=a("<tr></tr>");
for(v=0;
v<j.p.subGridModel[0].name.length;
v++){u=a("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-"+j.p.direction+"'></th>");
a(u).html(j.p.subGridModel[0].name[v]);
a(u).width(j.p.subGridModel[0].width[v]);
a(n).append(u)
}a(o).append(n);
if(t){u=j.p.jsonReader.subgrid;
t=t[u.root];
if(typeof t!=="undefined"){for(v=0;
v<t.length;
v++){s=t[v];
n=a("<tr class='ui-widget-content ui-subtblcell'></tr>");
if(u.repeatitems===true){if(u.cell){s=s[u.cell]
}for(r=0;
r<s.length;
r++){g(n,s[r]||"&#160;",r)
}}else{var m=j.p.subGridModel[0].mapping||j.p.subGridModel[0].name;
if(m.length){for(r=0;
r<m.length;
r++){g(n,s[m[r]]||"&#160;",r)
}}}a(o).append(n)
}}}v=a("table:first",j.grid.bDiv).attr("id")+"_";
a("#"+v+p).append(o);
j.grid.hDiv.loading=false;
a("#load_"+j.p.id).hide();
return false
},k=function(o){var m,p,r,n;
m=a(o).attr("id");
p={nd_:(new Date).getTime()};
p[j.p.prmNames.subgridid]=m;
if(!j.p.subGridModel[0]){return false
}if(j.p.subGridModel[0].params){for(n=0;
n<j.p.subGridModel[0].params.length;
n++){for(r=0;
r<j.p.colModel.length;
r++){if(j.p.colModel[r].name==j.p.subGridModel[0].params[n]){p[j.p.colModel[r].name]=a("td:eq("+r+")",o).text().replace(/\&#160\;/ig,"")
}}}}if(!j.grid.hDiv.loading){j.grid.hDiv.loading=true;
a("#load_"+j.p.id).show();
if(!j.p.subgridtype){j.p.subgridtype=j.p.datatype
}if(a.isFunction(j.p.subgridtype)){j.p.subgridtype.call(j,p)
}else{j.p.subgridtype=j.p.subgridtype.toLowerCase()
}switch(j.p.subgridtype){case"xml":case"json":a.ajax(a.extend({type:j.p.mtype,url:j.p.subGridUrl,dataType:j.p.subgridtype,data:a.isFunction(j.p.serializeSubGridData)?j.p.serializeSubGridData(j,p):p,complete:function(s){j.p.subgridtype=="xml"?f(s.responseXML,m):y(a.jgrid.parse(s.responseText),m)
}},a.jgrid.ajaxOptions,j.p.ajaxSubgridOptions||{}));
break
}}return false
},c,h,A,q,z,l,e;
a("td:eq("+d+")",b).click(function(){if(a(this).hasClass("sgcollapsed")){A=j.p.id;
c=a(this).parent();
q=d>=1?"<td colspan='"+d+"'>&#160;</td>":"";
h=a(c).attr("id");
e=true;
if(a.isFunction(j.p.subGridBeforeExpand)){e=j.p.subGridBeforeExpand.call(j,A+"_"+h,h)
}if(e===false){return false
}z=0;
a.each(j.p.colModel,function(){if(this.hidden===true||this.name=="rn"||this.name=="cb"){z++
}});
l="<tr role='row' class='ui-subgrid'>"+q+"<td class='ui-widget-content subgrid-cell'><span class='ui-icon ui-icon-carat-1-sw'/></td><td colspan='"+parseInt(j.p.colNames.length-1-z,10)+"' class='ui-widget-content subgrid-data'><div id="+A+"_"+h+" class='tablediv'>";
a(this).parent().after(l+"</div></td></tr>");
a.isFunction(j.p.subGridRowExpanded)?j.p.subGridRowExpanded.call(j,A+"_"+h,h):k(c);
a(this).html("<a href='javascript:void(0);'><span class='ui-icon ui-icon-minus'></span></a>").removeClass("sgcollapsed").addClass("sgexpanded")
}else{if(a(this).hasClass("sgexpanded")){e=true;
if(a.isFunction(j.p.subGridRowColapsed)){c=a(this).parent();
h=a(c).attr("id");
e=j.p.subGridRowColapsed.call(j,A+"_"+h,h)
}if(e===false){return false
}a(this).parent().next().remove(".ui-subgrid");
a(this).html("<a href='javascript:void(0);'><span class='ui-icon ui-icon-plus'></span></a>").removeClass("sgexpanded").addClass("sgcollapsed")
}}return false
});
j.subGridXml=function(n,m){f(n,m)
};
j.subGridJson=function(n,m){y(n,m)
}
}})
},expandSubGridRow:function(b){return this.each(function(){var d=this;
if(d.grid||b){if(d.p.subGrid===true){if(d=a(this).jqGrid("getInd",b,true)){(d=a("td.sgcollapsed",d)[0])&&a(d).trigger("click")
}}}})
},collapseSubGridRow:function(b){return this.each(function(){var d=this;
if(d.grid||b){if(d.p.subGrid===true){if(d=a(this).jqGrid("getInd",b,true)){(d=a("td.sgexpanded",d)[0])&&a(d).trigger("click")
}}}})
},toggleSubGridRow:function(b){return this.each(function(){var e=this;
if(e.grid||b){if(e.p.subGrid===true){if(e=a(this).jqGrid("getInd",b,true)){var d=a("td.sgcollapsed",e)[0];
if(d){a(d).trigger("click")
}else{(d=a("td.sgexpanded",e)[0])&&a(d).trigger("click")
}}}}})
}})
})(jQuery);
(function(a){a.jgrid.extend({groupingSetup:function(){return this.each(function(){var f=this,m=f.p.groupingView;
if(m!==null&&(typeof m==="object"||a.isFunction(m))){if(m.groupField.length){for(var h=0;
h<m.groupField.length;
h++){m.groupOrder[h]||(m.groupOrder[h]="asc");
m.groupText[h]||(m.groupText[h]="{0}");
if(typeof m.groupColumnShow[h]!="boolean"){m.groupColumnShow[h]=true
}if(typeof m.groupSummary[h]!="boolean"){m.groupSummary[h]=false
}m.groupColumnShow[h]===true?a(f).jqGrid("showCol",m.groupField[h]):a(f).jqGrid("hideCol",m.groupField[h]);
m.sortitems[h]=[];
m.sortnames[h]=[];
m.summaryval[h]=[];
if(m.groupSummary[h]){m.summary[h]=[];
for(var l=f.p.colModel,k=0,j=l.length;
k<j;
k++){l[k].summaryType&&m.summary[h].push({nm:l[k].name,st:l[k].summaryType,v:""})
}}}f.p.scroll=false;
f.p.rownumbers=false;
f.p.subGrid=false;
f.p.treeGrid=false;
f.p.gridview=true
}else{f.p.grouping=false
}}else{f.p.grouping=false
}})
},groupingPrepare:function(e,h,f,g){this.each(function(){var d=h[0]?h[0].split(" ").join(""):"",c=this.p.groupingView,b=this;
if(f.hasOwnProperty(d)){f[d].push(e)
}else{f[d]=[];
f[d].push(e);
c.sortitems[0].push(d);
c.sortnames[0].push(a.trim(h[0]));
c.summaryval[0][d]=a.extend(true,{},c.summary[0])
}c.groupSummary[0]&&a.each(c.summaryval[0][d],function(){this.v=a.isFunction(this.st)?this.st.call(b,this.v,this.nm,g):a(b).jqGrid("groupingCalculations."+this.st,this.v,this.nm,g)
})
});
return f
},groupingToggle:function(c){this.each(function(){var k=this.p.groupingView,b=c.lastIndexOf("_"),j=c.substring(0,b+1);
b=parseInt(c.substring(b+1),10)+1;
var h=k.minusicon,f=k.plusicon;
if(a("#"+c+" span").hasClass(h)){k.showSummaryOnHide&&k.groupSummary[0]?a("#"+c).nextUntil(".jqfoot").hide():a("#"+c).nextUntil("#"+j+String(b)).hide();
a("#"+c+" span").removeClass(h).addClass(f)
}else{a("#"+c).nextUntil("#"+j+String(b)).show();
a("#"+c+" span").removeClass(f).addClass(h)
}});
return false
},groupingRender:function(d,e){return this.each(function(){var c=this,m=c.p.groupingView,k="",h="",f,b="";
if(!m.groupDataSorted){m.sortitems[0].sort();
m.sortnames[0].sort();
m.groupOrder[0].toLowerCase()=="desc"&&m.sortitems[0].reverse()
}b=m.groupCollapse?m.plusicon:m.minusicon;
b+=" tree-wrap-"+c.p.direction;
a.each(m.sortitems[0],function(r,j){f=c.p.id+"ghead_"+r;
h="<span style='cursor:pointer;' class='ui-icon "+b+"' onclick=\"jQuery('#"+c.p.id+"').jqGrid('groupingToggle','"+f+"');return false;\"></span>";
k+='<tr id="'+f+'" role="row" class= "ui-widget-content jqgroup ui-row-'+c.p.direction+'"><td colspan="'+e+'">'+h+a.jgrid.format(m.groupText[0],m.sortnames[0][r],d[j].length)+"</td></tr>";
for(r=0;
r<d[j].length;
r++){k+=d[j][r].join("")
}if(m.groupSummary[0]){r="";
if(m.groupCollapse&&!m.showSummaryOnHide){r=' style="display:none;"'
}k+="<tr"+r+' role="row" class="ui-widget-content jqfoot ui-row-'+c.p.direction+'">';
r=m.summaryval[0][j];
for(var g=c.p.colModel,v,u=d[j].length,l=0;
l<e;
l++){var t="<td "+c.formatCol(l,1,"")+">&#160;</td>",s="{0}";
a.each(r,function(){if(this.nm==g[l].name){if(g[l].summaryTpl){s=g[l].summaryTpl
}if(this.st=="avg"){if(this.v&&u>0){this.v/=u
}}try{v=c.formatter("",this.v,l,this)
}catch(n){v=this.v
}t="<td "+c.formatCol(l,1,"")+">"+a.jgrid.format(s,v)+"</td>";
return false
}});
k+=t
}k+="</tr>"
}});
a("#"+c.p.id+" tbody:first").append(k);
k=null
})
},groupingGroupBy:function(d,e){return this.each(function(){var b=this;
if(typeof d=="string"){d=[d]
}var f=b.p.groupingView;
b.p.grouping=true;
for(var c=0;
c<f.groupField.length;
c++){f.groupColumnShow[c]||a(b).jqGrid("showCol",f.groupField[c])
}b.p.groupingView=a.extend(b.p.groupingView,e||{});
f.groupField=d;
a(b).trigger("reloadGrid")
})
},groupingRemove:function(c){return this.each(function(){var b=this;
if(typeof c=="undefined"){c=true
}b.p.grouping=false;
c===true?a("tr.jqgroup, tr.jqfoot","#"+b.p.id+" tbody:first").remove():a(b).trigger("reloadGrid")
})
},groupingCalculations:{sum:function(d,f,e){return parseFloat(d||0)+parseFloat(e[f]||0)
},min:function(d,f,e){if(d===""){return parseFloat(e[f]||0)
}return Math.min(parseFloat(d),parseFloat(e[f]||0))
},max:function(d,f,e){if(d===""){return parseFloat(e[f]||0)
}return Math.max(parseFloat(d),parseFloat(e[f]||0))
},count:function(d,f,e){if(d===""){d=0
}return e.hasOwnProperty(f)?d+1:0
},avg:function(d,f,e){return parseFloat(d||0)+parseFloat(e[f]||0)
}}})
})(jQuery);
(function(a){a.jgrid.extend({setTreeNode:function(d,e){return this.each(function(){var b=this;
if(b.grid&&b.p.treeGrid){var n=b.p.expColInd,o=b.p.treeReader.expanded_field,c=b.p.treeReader.leaf_field,g=b.p.treeReader.level_field;
e.level=d[g];
if(b.p.treeGridModel=="nested"){var l=d[b.p.treeReader.left_field],m=d[b.p.treeReader.right_field];
d[c]||(d[c]=parseInt(m,10)===parseInt(l,10)+1?"true":"false")
}m=parseInt(d[g],10);
if(b.p.tree_root_level===0){l=m+1;
m=m
}else{l=m;
m=m-1
}l="<div class='tree-wrap tree-wrap-"+b.p.direction+"' style='width:"+l*18+"px;'>";
l+="<div style='"+(b.p.direction=="rtl"?"right:":"left:")+m*18+"px;' class='ui-icon ";
if(d[c]=="true"||d[c]===true){l+=b.p.treeIcons.leaf+" tree-leaf'";
d[c]=true;
d[o]=false
}else{if(d[o]=="true"||d[o]===true){l+=b.p.treeIcons.minus+" tree-minus treeclick'";
d[o]=true
}else{l+=b.p.treeIcons.plus+" tree-plus treeclick'";
d[o]=false
}d[c]=false
}l+="</div></div>";
if(!b.p.loadonce){d[b.p.localReader.id]=e.id;
b.p.data.push(d);
b.p._index[e.id]=b.p.data.length-1
}if(parseInt(d[g],10)!==parseInt(b.p.tree_root_level,10)){a(b).jqGrid("isVisibleNode",d)||a(e).css("display","none")
}a("td:eq("+n+")",e).wrapInner("<span></span>").prepend(l);
a(".treeclick",e).bind("click",function(h){h=a(h.target||h.srcElement,b.rows).closest("tr.jqgrow")[0].id;
h=b.p._index[h];
var f=b.p.treeReader.expanded_field;
if(!b.p.data[h][b.p.treeReader.leaf_field]){if(b.p.data[h][f]){a(b).jqGrid("collapseRow",b.p.data[h]);
a(b).jqGrid("collapseNode",b.p.data[h])
}else{a(b).jqGrid("expandRow",b.p.data[h]);
a(b).jqGrid("expandNode",b.p.data[h])
}}return false
});
b.p.ExpandColClick===true&&a("span",e).css("cursor","pointer").bind("click",function(j){j=a(j.target||j.srcElement,b.rows).closest("tr.jqgrow")[0].id;
var h=b.p._index[j],f=b.p.treeReader.expanded_field;
if(!b.p.data[h][b.p.treeReader.leaf_field]){if(b.p.data[h][f]){a(b).jqGrid("collapseRow",b.p.data[h]);
a(b).jqGrid("collapseNode",b.p.data[h])
}else{a(b).jqGrid("expandRow",b.p.data[h]);
a(b).jqGrid("expandNode",b.p.data[h])
}}a(b).jqGrid("setSelection",j);
return false
})
}})
},setTreeGrid:function(){return this.each(function(){var d=this,f=0;
if(d.p.treeGrid){d.p.treedatatype||a.extend(d.p,{treedatatype:d.p.datatype});
d.p.subGrid=false;
d.p.altRows=false;
d.p.pgbuttons=false;
d.p.pginput=false;
d.p.multiselect=false;
d.p.rowList=[];
d.p.treeIcons=a.extend({plus:"ui-icon-triangle-1-"+(d.p.direction=="rtl"?"w":"e"),minus:"ui-icon-triangle-1-s",leaf:"ui-icon-radio-off"},d.p.treeIcons||{});
if(d.p.treeGridModel=="nested"){d.p.treeReader=a.extend({level_field:"level",left_field:"lft",right_field:"rgt",leaf_field:"isLeaf",expanded_field:"expanded"},d.p.treeReader)
}else{if(d.p.treeGridModel=="adjacency"){d.p.treeReader=a.extend({level_field:"level",parent_id_field:"parent",leaf_field:"isLeaf",expanded_field:"expanded"},d.p.treeReader)
}}for(var e in d.p.colModel){if(d.p.colModel.hasOwnProperty(e)){if(d.p.colModel[e].name==d.p.ExpandColumn){d.p.expColInd=f;
break
}f++
}}if(!d.p.expColInd){d.p.expColInd=0
}a.each(d.p.treeReader,function(b,c){if(c){d.p.colNames.push(c);
d.p.colModel.push({name:c,width:1,hidden:true,sortable:false,resizable:false,hidedlg:true,editable:true,search:false})
}})
}})
},expandRow:function(c){this.each(function(){var e=this;
if(e.grid&&e.p.treeGrid){var b=a(e).jqGrid("getNodeChildren",c),d=e.p.treeReader.expanded_field;
a(b).each(function(){var f=a.jgrid.getAccessor(this,e.p.localReader.id);
a("#"+f,e.grid.bDiv).css("display","");
this[d]&&a(e).jqGrid("expandRow",this)
})
}})
},collapseRow:function(c){this.each(function(){var e=this;
if(e.grid&&e.p.treeGrid){var b=a(e).jqGrid("getNodeChildren",c),d=e.p.treeReader.expanded_field;
a(b).each(function(){var f=a.jgrid.getAccessor(this,e.p.localReader.id);
a("#"+f,e.grid.bDiv).css("display","none");
this[d]&&a(e).jqGrid("collapseRow",this)
})
}})
},getRootNodes:function(){var c=[];
this.each(function(){var e=this;
if(e.grid&&e.p.treeGrid){switch(e.p.treeGridModel){case"nested":var b=e.p.treeReader.level_field;
a(e.p.data).each(function(){parseInt(this[b],10)===parseInt(e.p.tree_root_level,10)&&c.push(this)
});
break;
case"adjacency":var d=e.p.treeReader.parent_id_field;
a(e.p.data).each(function(){if(this[d]===null||String(this[d]).toLowerCase()=="null"){c.push(this)
}});
break
}}});
return c
},getNodeDepth:function(d){var e=null;
this.each(function(){if(this.grid&&this.p.treeGrid){var b=this;
switch(b.p.treeGridModel){case"nested":e=parseInt(d[b.p.treeReader.level_field],10)-parseInt(b.p.tree_root_level,10);
break;
case"adjacency":e=a(b).jqGrid("getNodeAncestors",d).length;
break
}}});
return e
},getNodeParent:function(d){var e=null;
this.each(function(){var s=this;
if(s.grid&&s.p.treeGrid){switch(s.p.treeGridModel){case"nested":var q=s.p.treeReader.left_field,r=s.p.treeReader.right_field,c=s.p.treeReader.level_field,m=parseInt(d[q],10),n=parseInt(d[r],10),o=parseInt(d[c],10);
a(this.p.data).each(function(){if(parseInt(this[c],10)===o-1&&parseInt(this[q],10)<m&&parseInt(this[r],10)>n){e=this;
return false
}});
break;
case"adjacency":var p=s.p.treeReader.parent_id_field,b=s.p.localReader.id;
a(this.p.data).each(function(){if(this[b]==d[p]){e=this;
return false
}});
break
}}});
return e
},getNodeChildren:function(d){var e=[];
this.each(function(){var s=this;
if(s.grid&&s.p.treeGrid){switch(s.p.treeGridModel){case"nested":var q=s.p.treeReader.left_field,r=s.p.treeReader.right_field,c=s.p.treeReader.level_field,m=parseInt(d[q],10),n=parseInt(d[r],10),o=parseInt(d[c],10);
a(this.p.data).each(function(){parseInt(this[c],10)===o+1&&parseInt(this[q],10)>m&&parseInt(this[r],10)<n&&e.push(this)
});
break;
case"adjacency":var p=s.p.treeReader.parent_id_field,b=s.p.localReader.id;
a(this.p.data).each(function(){this[p]==d[b]&&e.push(this)
});
break
}}});
return e
},getFullTreeNode:function(d){var e=[];
this.each(function(){var u=this,s;
if(u.grid&&u.p.treeGrid){switch(u.p.treeGridModel){case"nested":var t=u.p.treeReader.left_field,n=u.p.treeReader.right_field,o=u.p.treeReader.level_field,p=parseInt(d[t],10),q=parseInt(d[n],10),r=parseInt(d[o],10);
a(this.p.data).each(function(){parseInt(this[o],10)>=r&&parseInt(this[t],10)>=p&&parseInt(this[t],10)<=q&&e.push(this)
});
break;
case"adjacency":e.push(d);
var c=u.p.treeReader.parent_id_field,b=u.p.localReader.id;
a(this.p.data).each(function(f){s=e.length;
for(f=0;
f<s;
f++){if(e[f][b]==this[c]){e.push(this);
break
}}});
break
}}});
return e
},getNodeAncestors:function(d){var e=[];
this.each(function(){if(this.grid&&this.p.treeGrid){for(var b=a(this).jqGrid("getNodeParent",d);
b;
){e.push(b);
b=a(this).jqGrid("getNodeParent",b)
}}});
return e
},isVisibleNode:function(d){var e=true;
this.each(function(){var b=this;
if(b.grid&&b.p.treeGrid){var c=a(b).jqGrid("getNodeAncestors",d),g=b.p.treeReader.expanded_field;
a(c).each(function(){e=e&&this[g];
if(!e){return false
}})
}});
return e
},isNodeLoaded:function(d){var e;
this.each(function(){var b=this;
if(b.grid&&b.p.treeGrid){var c=b.p.treeReader.leaf_field;
e=d.loaded!==undefined?d.loaded:d[c]||a(b).jqGrid("getNodeChildren",d).length>0?true:false
}});
return e
},expandNode:function(c){return this.each(function(){if(this.grid&&this.p.treeGrid){var h=this.p.treeReader.expanded_field;
if(!c[h]){var b=a.jgrid.getAccessor(c,this.p.localReader.id),d=a("#"+b,this.grid.bDiv)[0],g=this.p._index[b];
if(a(this).jqGrid("isNodeLoaded",this.p.data[g])){c[h]=true;
a("div.treeclick",d).removeClass(this.p.treeIcons.plus+" tree-plus").addClass(this.p.treeIcons.minus+" tree-minus")
}else{c[h]=true;
a("div.treeclick",d).removeClass(this.p.treeIcons.plus+" tree-plus").addClass(this.p.treeIcons.minus+" tree-minus");
this.p.treeANode=d.rowIndex;
this.p.datatype=this.p.treedatatype;
this.p.treeGridModel=="nested"?a(this).jqGrid("setGridParam",{postData:{nodeid:b,n_left:c.lft,n_right:c.rgt,n_level:c.level}}):a(this).jqGrid("setGridParam",{postData:{nodeid:b,parentid:c.parent_id,n_level:c.level}});
a(this).trigger("reloadGrid");
this.p.treeGridModel=="nested"?a(this).jqGrid("setGridParam",{postData:{nodeid:"",n_left:"",n_right:"",n_level:""}}):a(this).jqGrid("setGridParam",{postData:{nodeid:"",parentid:"",n_level:""}})
}}}})
},collapseNode:function(c){return this.each(function(){if(this.grid&&this.p.treeGrid){if(c.expanded){c.expanded=false;
var b=a.jgrid.getAccessor(c,this.p.localReader.id);
b=a("#"+b,this.grid.bDiv)[0];
a("div.treeclick",b).removeClass(this.p.treeIcons.minus+" tree-minus").addClass(this.p.treeIcons.plus+" tree-plus")
}}})
},SortTree:function(d,h,e,g){return this.each(function(){if(this.grid&&this.p.treeGrid){var n,b,c,f=[],l=this,m;
n=a(this).jqGrid("getRootNodes");
n=a.jgrid.from(n);
n.orderBy(d,h,e,g);
m=n.select();
n=0;
for(b=m.length;
n<b;
n++){c=m[n];
f.push(c);
a(this).jqGrid("collectChildrenSortTree",f,c,d,h,e,g)
}a.each(f,function(k){var j=a.jgrid.getAccessor(this,l.p.localReader.id);
if(k===0){k=a("#"+j,l.grid.bDiv);
a("td",k).each(function(o){a(this).css("width",l.grid.headers[o].width+"px")
});
l.grid.cols=k[0].cells
}a("tbody",l.grid.bDiv).append(a("#"+j,l.grid.bDiv))
});
f=m=n=null
}})
},collectChildrenSortTree:function(d,m,g,j,l,h){return this.each(function(){if(this.grid&&this.p.treeGrid){var b,c,e,f;
b=a(this).jqGrid("getNodeChildren",m);
b=a.jgrid.from(b);
b.orderBy(g,j,l,h);
f=b.select();
b=0;
for(c=f.length;
b<c;
b++){e=f[b];
d.push(e);
a(this).jqGrid("collectChildrenSortTree",d,e,g,j,l,h)
}}})
},setTreeRow:function(d,f){var e=false;
this.each(function(){var b=this;
if(b.grid&&b.p.treeGrid){e=a(b).jqGrid("setRowData",d,f)
}});
return e
},delTreeNode:function(c){return this.each(function(){var h=this;
if(h.grid&&h.p.treeGrid){var b=a(h).jqGrid("getInd",c,true);
if(b){var d=a(h).jqGrid("getNodeChildren",b);
if(d.length>0){for(var g=0;
g<d.length;
g++){a(h).jqGrid("delRowData",d[g].id)
}}a(h).jqGrid("delRowData",b.id)
}}})
}})
})(jQuery);
(function(a){a.jgrid.extend({jqGridImport:function(b){b=a.extend({imptype:"xml",impstring:"",impurl:"",mtype:"GET",impData:{},xmlGrid:{config:"roots>grid",data:"roots>rows"},jsonGrid:{config:"grid",data:"data"},ajaxOptions:{}},b||{});
return this.each(function(){var g=this,h=function(m,j){var l=a(j.xmlGrid.config,m)[0];
j=a(j.xmlGrid.data,m)[0];
var c;
if(xmlJsonClass.xml2json&&a.jgrid.parse){l=xmlJsonClass.xml2json(l," ");
l=a.jgrid.parse(l);
for(var d in l){if(l.hasOwnProperty(d)){c=l[d]
}}if(j){d=l.grid.datatype;
l.grid.datatype="xmlstring";
l.grid.datastr=m;
a(g).jqGrid(c).jqGrid("setGridParam",{datatype:d})
}else{a(g).jqGrid(c)
}}else{alert("xml2json or parse are not present")
}},f=function(j,c){if(j&&typeof j=="string"){var d=a.jgrid.parse(j);
j=d[c.jsonGrid.config];
if(c=d[c.jsonGrid.data]){d=j.datatype;
j.datatype="jsonstring";
j.datastr=c;
a(g).jqGrid(j).jqGrid("setGridParam",{datatype:d})
}else{a(g).jqGrid(j)
}}};
switch(b.imptype){case"xml":a.ajax(a.extend({url:b.impurl,type:b.mtype,data:b.impData,dataType:"xml",complete:function(d,c){if(c=="success"){h(d.responseXML,b);
a.isFunction(b.importComplete)&&b.importComplete(d)
}}},b.ajaxOptions));
break;
case"xmlstring":if(b.impstring&&typeof b.impstring=="string"){var e=a.jgrid.stringToDoc(b.impstring);
if(e){h(e,b);
a.isFunction(b.importComplete)&&b.importComplete(e);
b.impstring=null
}e=null
}break;
case"json":a.ajax(a.extend({url:b.impurl,type:b.mtype,data:b.impData,dataType:"json",complete:function(d,c){if(c=="success"){f(d.responseText,b);
a.isFunction(b.importComplete)&&b.importComplete(d)
}}},b.ajaxOptions));
break;
case"jsonstring":if(b.impstring&&typeof b.impstring=="string"){f(b.impstring,b);
a.isFunction(b.importComplete)&&b.importComplete(b.impstring);
b.impstring=null
}break
}})
},jqGridExport:function(b){b=a.extend({exptype:"xmlstring",root:"grid",ident:"\t"},b||{});
var c=null;
this.each(function(){if(this.grid){var e=a.extend({},a(this).jqGrid("getGridParam"));
if(e.rownumbers){e.colNames.splice(0,1);
e.colModel.splice(0,1)
}if(e.multiselect){e.colNames.splice(0,1);
e.colModel.splice(0,1)
}if(e.subGrid){e.colNames.splice(0,1);
e.colModel.splice(0,1)
}e.knv=null;
if(e.treeGrid){for(var d in e.treeReader){if(e.treeReader.hasOwnProperty(d)){e.colNames.splice(e.colNames.length-1);
e.colModel.splice(e.colModel.length-1)
}}}switch(b.exptype){case"xmlstring":c="<"+b.root+">"+xmlJsonClass.json2xml(e,b.ident)+"</"+b.root+">";
break;
case"jsonstring":c="{"+xmlJsonClass.toJson(e,b.root,b.ident)+"}";
if(e.postData.filters!==undefined){c=c.replace(/filters":"/,'filters":');
c=c.replace(/}]}"/,"}]}")
}break
}}});
return c
},excelExport:function(b){b=a.extend({exptype:"remote",url:null,oper:"oper",tag:"excel",exportOptions:{}},b||{});
return this.each(function(){if(this.grid){var c;
if(b.exptype=="remote"){c=a.extend({},this.p.postData);
c[b.oper]=b.tag;
c=jQuery.param(c);
c=b.url.indexOf("?")!=-1?b.url+"&"+c:b.url+"?"+c;
window.location=c
}}})
}})
})(jQuery);
var xmlJsonClass={xml2json:function(d,c){if(d.nodeType===9){d=d.documentElement
}d=this.toJson(this.toObj(this.removeWhite(d)),d.nodeName,"\t");
return"{\n"+c+(c?d.replace(/\t/g,c):d.replace(/\t|\n/g,""))+"\n}"
},json2xml:function(d,c){var h=function(m,n,e){var f="",b,g;
if(m instanceof Array){if(m.length===0){f+=e+"<"+n+">__EMPTY_ARRAY_</"+n+">\n"
}else{b=0;
for(g=m.length;
b<g;
b+=1){var a=e+h(m[b],n,e+"\t")+"\n";
f+=a
}}}else{if(typeof m==="object"){b=false;
f+=e+"<"+n;
for(g in m){if(m.hasOwnProperty(g)){if(g.charAt(0)==="@"){f+=" "+g.substr(1)+'="'+m[g].toString()+'"'
}else{b=true
}}}f+=b?">":"/>";
if(b){for(g in m){if(m.hasOwnProperty(g)){if(g==="#text"){f+=m[g]
}else{if(g==="#cdata"){f+="<![CDATA["+m[g]+"]]>"
}else{if(g.charAt(0)!=="@"){f+=h(m[g],g,e+"\t")
}}}}}f+=(f.charAt(f.length-1)==="\n"?e:"")+"</"+n+">"
}}else{f+=typeof m==="function"?e+"<"+n+"><![CDATA["+m+"]]></"+n+">":m.toString()==='""'||m.toString().length===0?e+"<"+n+">__EMPTY_STRING_</"+n+">":e+"<"+n+">"+m.toString()+"</"+n+">"
}}return f
},k="",j;
for(j in d){if(d.hasOwnProperty(j)){k+=h(d[j],j,"")
}}return c?k.replace(/\t/g,c):k.replace(/\t|\n/g,"")
},toObj:function(j){var h={},k=/function/i;
if(j.nodeType===1){if(j.attributes.length){var m;
for(m=0;
m<j.attributes.length;
m+=1){h["@"+j.attributes[m].nodeName]=(j.attributes[m].nodeValue||"").toString()
}}if(j.firstChild){var l=m=0,n=false,o;
for(o=j.firstChild;
o;
o=o.nextSibling){if(o.nodeType===1){n=true
}else{if(o.nodeType===3&&o.nodeValue.match(/[^ \f\n\r\t\v]/)){m+=1
}else{if(o.nodeType===4){l+=1
}}}}if(n){if(m<2&&l<2){this.removeWhite(j);
for(o=j.firstChild;
o;
o=o.nextSibling){if(o.nodeType===3){h["#text"]=this.escape(o.nodeValue)
}else{if(o.nodeType===4){if(k.test(o.nodeValue)){h[o.nodeName]=[h[o.nodeName],o.nodeValue]
}else{h["#cdata"]=this.escape(o.nodeValue)
}}else{if(h[o.nodeName]){if(h[o.nodeName] instanceof Array){h[o.nodeName][h[o.nodeName].length]=this.toObj(o)
}else{h[o.nodeName]=[h[o.nodeName],this.toObj(o)]
}}else{h[o.nodeName]=this.toObj(o)
}}}}}else{if(j.attributes.length){h["#text"]=this.escape(this.innerXml(j))
}else{h=this.escape(this.innerXml(j))
}}}else{if(m){if(j.attributes.length){h["#text"]=this.escape(this.innerXml(j))
}else{h=this.escape(this.innerXml(j));
if(h==="__EMPTY_ARRAY_"){h="[]"
}else{if(h==="__EMPTY_STRING_"){h=""
}}}}else{if(l){if(l>1){h=this.escape(this.innerXml(j))
}else{for(o=j.firstChild;
o;
o=o.nextSibling){if(k.test(j.firstChild.nodeValue)){h=j.firstChild.nodeValue;
break
}else{h["#cdata"]=this.escape(o.nodeValue)
}}}}}}}if(!j.attributes.length&&!j.firstChild){h=null
}}else{if(j.nodeType===9){h=this.toObj(j.documentElement)
}else{alert("unhandled node type: "+j.nodeType)
}}return h
},toJson:function(j,h,k){var m=h?'"'+h+'"':"";
if(j==="[]"){m+=h?":[]":"[]"
}else{if(j instanceof Array){var l,n,o=[];
n=0;
for(l=j.length;
n<l;
n+=1){o[n]=this.toJson(j[n],"",k+"\t")
}m+=(h?":[":"[")+(o.length>1?"\n"+k+"\t"+o.join(",\n"+k+"\t")+"\n"+k:o.join(""))+"]"
}else{if(j===null){m+=(h&&":")+"null"
}else{if(typeof j==="object"){l=[];
for(n in j){if(j.hasOwnProperty(n)){l[l.length]=this.toJson(j[n],n,k+"\t")
}}m+=(h?":{":"{")+(l.length>1?"\n"+k+"\t"+l.join(",\n"+k+"\t")+"\n"+k:l.join(""))+"}"
}else{if(typeof j==="string"){k=/function/i;
l=j.toString();
m+=/(^-?\d+\.?\d*$)/.test(l)||k.test(l)||l==="false"||l==="true"?(h&&":")+l:(h&&":")+'"'+j+'"'
}else{m+=(h&&":")+j.toString()
}}}}}return m
},innerXml:function(d){var c="";
if("innerHTML" in d){c=d.innerHTML
}else{var e=function(b){var a="",g;
if(b.nodeType===1){a+="<"+b.nodeName;
for(g=0;
g<b.attributes.length;
g+=1){a+=" "+b.attributes[g].nodeName+'="'+(b.attributes[g].nodeValue||"").toString()+'"'
}if(b.firstChild){a+=">";
for(g=b.firstChild;
g;
g=g.nextSibling){a+=e(g)
}a+="</"+b.nodeName+">"
}else{a+="/>"
}}else{if(b.nodeType===3){a+=b.nodeValue
}else{if(b.nodeType===4){a+="<![CDATA["+b.nodeValue+"]]>"
}}}return a
};
for(d=d.firstChild;
d;
d=d.nextSibling){c+=e(d)
}}return c
},escape:function(b){return b.replace(/[\\]/g,"\\\\").replace(/[\"]/g,'\\"').replace(/[\n]/g,"\\n").replace(/[\r]/g,"\\r")
},removeWhite:function(d){d.normalize();
var c;
for(c=d.firstChild;
c;
){if(c.nodeType===3){if(c.nodeValue.match(/[^ \f\n\r\t\v]/)){c=c.nextSibling
}else{var e=c.nextSibling;
d.removeChild(c);
c=e
}}else{c.nodeType===1&&this.removeWhite(c);
c=c.nextSibling
}}return d
}};
(function(a){a.jgrid.extend({setColumns:function(b){b=a.extend({top:0,left:0,width:200,height:"auto",dataheight:"auto",modal:false,drag:true,beforeShowForm:null,afterShowForm:null,afterSubmitForm:null,closeOnEscape:true,ShrinkToFit:false,jqModal:false,saveicon:[true,"left","ui-icon-disk"],closeicon:[true,"left","ui-icon-close"],onClose:null,colnameview:true,closeAfterSubmit:true,updateAfterCheck:false,recreateForm:false},a.jgrid.col,b||{});
return this.each(function(){var s=this;
if(s.grid){var n=typeof b.beforeShowForm==="function"?true:false,m=typeof b.afterShowForm==="function"?true:false,h=typeof b.afterSubmitForm==="function"?true:false,q=s.p.id,r="ColTbl_"+q,p={themodal:"colmod"+q,modalhead:"colhd"+q,modalcontent:"colcnt"+q,scrollelm:r};
b.recreateForm===true&&a("#"+p.themodal).html()!=null&&a("#"+p.themodal).remove();
if(a("#"+p.themodal).html()!=null){n&&b.beforeShowForm(a("#"+r));
viewModal("#"+p.themodal,{gbox:"#gbox_"+q,jqm:b.jqModal,jqM:false,modal:b.modal})
}else{var o=isNaN(b.dataheight)?b.dataheight:b.dataheight+"px";
o="<div id='"+r+"' class='formdata' style='width:100%;overflow:auto;position:relative;height:"+o+";'>";
o+="<table class='ColTable' cellspacing='1' cellpading='2' border='0'><tbody>";
for(i=0;
i<this.p.colNames.length;
i++){s.p.colModel[i].hidedlg||(o+="<tr><td style='white-space: pre;'><input type='checkbox' style='margin-right:5px;' id='col_"+this.p.colModel[i].name+"' class='cbox' value='T' "+(this.p.colModel[i].hidden===false?"checked":"")+"/><label for='col_"+this.p.colModel[i].name+"'>"+this.p.colNames[i]+(b.colnameview?" ("+this.p.colModel[i].name+")":"")+"</label></td></tr>")
}o+="</tbody></table></div>";
o+="<table border='0' class='EditTable' id='"+r+"_2'><tbody><tr style='display:block;height:3px;'><td></td></tr><tr><td class='DataTD ui-widget-content'></td></tr><tr><td class='ColButton EditButton'>"+(!b.updateAfterCheck?"<a href='javascript:void(0)' id='dData' class='fm-button ui-state-default ui-corner-all'>"+b.bSubmit+"</a>":"")+"&#160;"+("<a href='javascript:void(0)' id='eData' class='fm-button ui-state-default ui-corner-all'>"+b.bCancel+"</a>")+"</td></tr></tbody></table>";
b.gbox="#gbox_"+q;
createModal(p,o,b,"#gview_"+s.p.id,a("#gview_"+s.p.id)[0]);
if(b.saveicon[0]==true){a("#dData","#"+r+"_2").addClass(b.saveicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.saveicon[2]+"'></span>")
}if(b.closeicon[0]==true){a("#eData","#"+r+"_2").addClass(b.closeicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.closeicon[2]+"'></span>")
}b.updateAfterCheck?a(":input","#"+r).click(function(){var c=this.id.substr(4);
if(c){this.checked?a(s).jqGrid("showCol",c):a(s).jqGrid("hideCol",c);
b.ShrinkToFit===true&&a(s).jqGrid("setGridWidth",s.grid.width-0.001,true)
}return this
}):a("#dData","#"+r+"_2").click(function(){for(i=0;
i<s.p.colModel.length;
i++){if(!s.p.colModel[i].hidedlg){var c=s.p.colModel[i].name.replace(/\./g,"\\.");
if(a("#col_"+c,"#"+r).attr("checked")){a(s).jqGrid("showCol",s.p.colModel[i].name);
a("#col_"+c,"#"+r).attr("defaultChecked",true)
}else{a(s).jqGrid("hideCol",s.p.colModel[i].name);
a("#col_"+c,"#"+r).attr("defaultChecked","")
}}}b.ShrinkToFit===true&&a(s).jqGrid("setGridWidth",s.grid.width-0.001,true);
b.closeAfterSubmit&&hideModal("#"+p.themodal,{gb:"#gbox_"+q,jqm:b.jqModal,onClose:b.onClose});
h&&b.afterSubmitForm(a("#"+r));
return false
});
a("#eData","#"+r+"_2").click(function(){hideModal("#"+p.themodal,{gb:"#gbox_"+q,jqm:b.jqModal,onClose:b.onClose});
return false
});
a("#dData, #eData","#"+r+"_2").hover(function(){a(this).addClass("ui-state-hover")
},function(){a(this).removeClass("ui-state-hover")
});
n&&b.beforeShowForm(a("#"+r));
viewModal("#"+p.themodal,{gbox:"#gbox_"+q,jqm:b.jqModal,jqM:true,modal:b.modal})
}m&&b.afterShowForm(a("#"+r))
}})
}})
})(jQuery);
(function(a){a.jgrid.extend({getPostData:function(){var b=this[0];
if(b.grid){return b.p.postData
}},setPostData:function(d){var c=this[0];
if(c.grid){if(typeof d==="object"){c.p.postData=d
}else{alert("Error: cannot add a non-object postData value. postData unchanged.")
}}},appendPostData:function(d){var c=this[0];
if(c.grid){typeof d==="object"?a.extend(c.p.postData,d):alert("Error: cannot append a non-object postData value. postData unchanged.")
}},setPostDataItem:function(e,c){var f=this[0];
if(f.grid){f.p.postData[e]=c
}},getPostDataItem:function(d){var c=this[0];
if(c.grid){return c.p.postData[d]
}},removePostDataItem:function(d){var c=this[0];
c.grid&&delete c.p.postData[d]
},getUserData:function(){var b=this[0];
if(b.grid){return b.p.userData
}},getUserDataItem:function(d){var c=this[0];
if(c.grid){return c.p.userData[d]
}}})
})(jQuery);
function tableToGrid(b,a){jQuery(b).each(function(){if(!this.grid){jQuery(this).width("99%");
var r=jQuery(this).width(),o=jQuery("input[type=checkbox]:first",jQuery(this)),q=jQuery("input[type=radio]:first",jQuery(this));
o=o.length>0;
q=!o&&q.length>0;
var e=o||q,p=[],m=[];
jQuery("th",jQuery(this)).each(function(){if(p.length===0&&e){p.push({name:"__selection__",index:"__selection__",width:0,hidden:true});
m.push("__selection__")
}else{p.push({name:jQuery(this).attr("id")||jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),index:jQuery(this).attr("id")||jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),width:jQuery(this).width()||150});
m.push(jQuery(this).html())
}});
var n=[],k=[],j=[];
jQuery("tbody > tr",jQuery(this)).each(function(){var c={},d=0;
jQuery("td",jQuery(this)).each(function(){if(d===0&&e){var g=jQuery("input",jQuery(this)),f=g.attr("value");
k.push(f||n.length);
g.attr("checked")&&j.push(f);
c[p[d].name]=g.attr("value")
}else{c[p[d].name]=jQuery(this).html()
}d++
});
d>0&&n.push(c)
});
jQuery(this).empty();
jQuery(this).addClass("scroll");
jQuery(this).jqGrid(jQuery.extend({datatype:"local",width:r,colNames:m,colModel:p,multiselect:o},a||{}));
for(r=0;
r<n.length;
r++){q=null;
if(k.length>0){if((q=k[r])&&q.replace){q=encodeURIComponent(q).replace(/[.\-%]/g,"_")
}}if(q===null){q=r+1
}jQuery(this).jqGrid("addRowData",q,n[r])
}for(r=0;
r<j.length;
r++){jQuery(this).jqGrid("setSelection",j[r])
}}})
}(function(a){if(a.browser.msie&&a.browser.version==8){a.expr[":"].hidden=function(b){return b.offsetWidth===0||b.offsetHeight===0||b.style.display=="none"
}
}a.jgrid._multiselect=false;
if(a.ui){if(a.ui.multiselect){if(a.ui.multiselect.prototype._setSelected){var q=a.ui.multiselect.prototype._setSelected;
a.ui.multiselect.prototype._setSelected=function(b,j){b=q.call(this,b,j);
if(j&&this.selectedList){var c=this.element;
this.selectedList.find("li").each(function(){a(this).data("optionLink")&&a(this).data("optionLink").remove().appendTo(c)
})
}return b
}
}if(a.ui.multiselect.prototype.destroy){a.ui.multiselect.prototype.destroy=function(){this.element.show();
this.container.remove();
a.Widget===undefined?a.widget.prototype.destroy.apply(this,arguments):a.Widget.prototype.destroy.apply(this,arguments)
}
}a.jgrid._multiselect=true
}}a.jgrid.extend({sortableColumns:function(b){return this.each(function(){function j(){c.p.disableClick=true
}var c=this,g={tolerance:"pointer",axis:"x",scrollSensitivity:"1",items:">th:not(:has(#jqgh_cb,#jqgh_rn,#jqgh_subgrid),:hidden)",placeholder:{element:function(e){return a(document.createElement(e[0].nodeName)).addClass(e[0].className+" ui-sortable-placeholder ui-state-highlight").removeClass("ui-sortable-helper")[0]
},update:function(e,h){h.height(e.currentItem.innerHeight()-parseInt(e.currentItem.css("paddingTop")||0,10)-parseInt(e.currentItem.css("paddingBottom")||0,10));
h.width(e.currentItem.innerWidth()-parseInt(e.currentItem.css("paddingLeft")||0,10)-parseInt(e.currentItem.css("paddingRight")||0,10))
}},update:function(e,h){e=a(h.item).parent();
e=a(">th",e);
var i={};
a.each(c.p.colModel,function(m){i[this.name]=m
});
var l=[];
e.each(function(){var m=a(">div",this).get(0).id.replace(/^jqgh_/,"");
m in i&&l.push(i[m])
});
a(c).jqGrid("remapColumns",l,true,true);
a.isFunction(c.p.sortable.update)&&c.p.sortable.update(l);
setTimeout(function(){c.p.disableClick=false
},50)
}};
if(c.p.sortable.options){a.extend(g,c.p.sortable.options)
}else{if(a.isFunction(c.p.sortable)){c.p.sortable={update:c.p.sortable}
}}if(g.start){var d=g.start;
g.start=function(e,h){j();
d.call(this,e,h)
}
}else{g.start=j
}if(c.p.sortable.exclude){g.items+=":not("+c.p.sortable.exclude+")"
}b.sortable(g).data("sortable").floating=true
})
},columnChooser:function(b){function j(f,k,p){if(k>=0){var o=f.slice(),r=o.splice(k,Math.max(f.length-k,k));
if(k>f.length){k=f.length
}o[k]=p;
return o.concat(r)
}}function c(f,k){if(f){if(typeof f=="string"){a.fn[f]&&a.fn[f].apply(k,a.makeArray(arguments).slice(2))
}else{a.isFunction(f)&&f.apply(k,a.makeArray(arguments).slice(2))
}}}var g=this;
if(!a("#colchooser_"+g[0].p.id).length){var d=a('<div id="colchooser_'+g[0].p.id+'" style="position:relative;overflow:hidden"><div><select multiple="multiple"></select></div></div>'),e=a("select",d);
b=a.extend({width:420,height:240,classname:null,done:function(f){f&&g.jqGrid("remapColumns",f,true)
},msel:"multiselect",dlog:"dialog",dlog_opts:function(f){var k={};
k[f.bSubmit]=function(){f.apply_perm();
f.cleanup(false)
};
k[f.bCancel]=function(){f.cleanup(true)
};
return{buttons:k,close:function(){f.cleanup(true)
},modal:false,resizable:false,width:f.width+20}
},apply_perm:function(){a("option",e).each(function(){this.selected?g.jqGrid("showCol",h[this.value].name):g.jqGrid("hideCol",h[this.value].name)
});
var f=[];
a("option[selected]",e).each(function(){f.push(parseInt(this.value,10))
});
a.each(f,function(){delete l[h[parseInt(this,10)].name]
});
a.each(l,function(){var k=parseInt(this,10);
f=j(f,k,k)
});
b.done&&b.done.call(g,f)
},cleanup:function(f){c(b.dlog,d,"destroy");
c(b.msel,e,"destroy");
d.remove();
f&&b.done&&b.done.call(g)
},msel_opts:{}},a.jgrid.col,b||{});
if(a.ui){if(a.ui.multiselect){if(b.msel=="multiselect"){if(!a.jgrid._multiselect){alert("Multiselect plugin loaded after jqGrid. Please load the plugin before the jqGrid!");
return
}b.msel_opts=a.extend(a.ui.multiselect.defaults,b.msel_opts)
}}}b.caption&&d.attr("title",b.caption);
if(b.classname){d.addClass(b.classname);
e.addClass(b.classname)
}if(b.width){a(">div",d).css({width:b.width,margin:"0 auto"});
e.css("width",b.width)
}if(b.height){a(">div",d).css("height",b.height);
e.css("height",b.height-10)
}var h=g.jqGrid("getGridParam","colModel"),i=g.jqGrid("getGridParam","colNames"),l={},m=[];
e.empty();
a.each(h,function(f){l[this.name]=f;
if(this.hidedlg){this.hidden||m.push(f)
}else{e.append("<option value='"+f+"' "+(this.hidden?"":"selected='selected'")+">"+i[f]+"</option>")
}});
var n=a.isFunction(b.dlog_opts)?b.dlog_opts.call(g,b):b.dlog_opts;
c(b.dlog,d,n);
n=a.isFunction(b.msel_opts)?b.msel_opts.call(g,b):b.msel_opts;
c(b.msel,e,n)
}},sortableRows:function(b){return this.each(function(){var j=this;
if(j.grid){if(!j.p.treeGrid){if(a.fn.sortable){b=a.extend({cursor:"move",axis:"y",items:".jqgrow"},b||{});
if(b.start&&a.isFunction(b.start)){b._start_=b.start;
delete b.start
}else{b._start_=false
}if(b.update&&a.isFunction(b.update)){b._update_=b.update;
delete b.update
}else{b._update_=false
}b.start=function(c,g){a(g.item).css("border-width","0px");
a("td",g.item).each(function(h){this.style.width=j.grid.cols[h].style.width
});
if(j.p.subGrid){var d=a(g.item).attr("id");
try{a(j).jqGrid("collapseSubGridRow",d)
}catch(e){}}b._start_&&b._start_.apply(this,[c,g])
};
b.update=function(c,g){a(g.item).css("border-width","");
j.p.rownumbers===true&&a("td.jqgrid-rownum",j.rows).each(function(d){a(this).html(d+1)
});
b._update_&&b._update_.apply(this,[c,g])
};
a("tbody:first",j).sortable(b);
a("tbody:first",j).disableSelection()
}}}})
},gridDnD:function(b){return this.each(function(){function j(){var d=a.data(c,"dnd");
a("tr.jqgrow:not(.ui-draggable)",c).draggable(a.isFunction(d.drag)?d.drag.call(a(c),d):d.drag)
}var c=this;
if(c.grid){if(!c.p.treeGrid){if(a.fn.draggable&&a.fn.droppable){a("#jqgrid_dnd").html()===null&&a("body").append("<table id='jqgrid_dnd' class='ui-jqgrid-dnd'></table>");
if(typeof b=="string"&&b=="updateDnD"&&c.p.jqgdnd===true){j()
}else{b=a.extend({drag:function(d){return a.extend({start:function(e,h){if(c.p.subGrid){var i=a(h.helper).attr("id");
try{a(c).jqGrid("collapseSubGridRow",i)
}catch(l){}}for(i=0;
i<a.data(c,"dnd").connectWith.length;
i++){a(a.data(c,"dnd").connectWith[i]).jqGrid("getGridParam","reccount")=="0"&&a(a.data(c,"dnd").connectWith[i]).jqGrid("addRowData","jqg_empty_row",{})
}h.helper.addClass("ui-state-highlight");
a("td",h.helper).each(function(m){this.style.width=c.grid.headers[m].width+"px"
});
d.onstart&&a.isFunction(d.onstart)&&d.onstart.call(a(c),e,h)
},stop:function(e,h){if(h.helper.dropped){var i=a(h.helper).attr("id");
a(c).jqGrid("delRowData",i)
}for(i=0;
i<a.data(c,"dnd").connectWith.length;
i++){a(a.data(c,"dnd").connectWith[i]).jqGrid("delRowData","jqg_empty_row")
}d.onstop&&a.isFunction(d.onstop)&&d.onstop.call(a(c),e,h)
}},d.drag_opts||{})
},drop:function(d){return a.extend({accept:function(e){var h=a(e).closest("table.ui-jqgrid-btable");
if(a.data(h[0],"dnd")!==undefined){e=a.data(h[0],"dnd").connectWith;
return a.inArray("#"+this.id,e)!=-1?true:false
}return e
},drop:function(e,h){var i=a(h.draggable).attr("id");
i=a("#"+c.id).jqGrid("getRowData",i);
if(!d.dropbyname){var l=0,m={},n,f=a("#"+this.id).jqGrid("getGridParam","colModel");
try{for(var k in i){if(i.hasOwnProperty(k)&&f[l]){n=f[l].name;
m[n]=i[k]
}l++
}i=m
}catch(p){}}h.helper.dropped=true;
if(d.beforedrop&&a.isFunction(d.beforedrop)){n=d.beforedrop.call(this,e,h,i,a("#"+c.id),a(this));
if(typeof n!="undefined"&&n!==null&&typeof n=="object"){i=n
}}if(h.helper.dropped){var o;
if(d.autoid){if(a.isFunction(d.autoid)){o=d.autoid.call(this,i)
}else{o=Math.ceil(Math.random()*1000);
o=d.autoidprefix+o
}}a("#"+this.id).jqGrid("addRowData",o,i,d.droppos)
}d.ondrop&&a.isFunction(d.ondrop)&&d.ondrop.call(this,e,h,i)
}},d.drop_opts||{})
},onstart:null,onstop:null,beforedrop:null,ondrop:null,drop_opts:{activeClass:"ui-state-active",hoverClass:"ui-state-hover"},drag_opts:{revert:"invalid",helper:"clone",cursor:"move",appendTo:"#jqgrid_dnd",zIndex:5000},dropbyname:false,droppos:"first",autoid:true,autoidprefix:"dnd_"},b||{});
if(b.connectWith){b.connectWith=b.connectWith.split(",");
b.connectWith=a.map(b.connectWith,function(d){return a.trim(d)
});
a.data(c,"dnd",b);
c.p.reccount!="0"&&!c.p.jqgdnd&&j();
c.p.jqgdnd=true;
for(var g=0;
g<b.connectWith.length;
g++){a(b.connectWith[g]).droppable(a.isFunction(b.drop)?b.drop.call(a(c),b):b.drop)
}}}}}}})
},gridResize:function(b){return this.each(function(){var j=this;
if(j.grid&&a.fn.resizable){b=a.extend({},b||{});
if(b.alsoResize){b._alsoResize_=b.alsoResize;
delete b.alsoResize
}else{b._alsoResize_=false
}if(b.stop&&a.isFunction(b.stop)){b._stop_=b.stop;
delete b.stop
}else{b._stop_=false
}b.stop=function(c,g){a(j).jqGrid("setGridParam",{height:a("#gview_"+j.p.id+" .ui-jqgrid-bdiv").height()});
a(j).jqGrid("setGridWidth",g.size.width,b.shrinkToFit);
b._stop_&&b._stop_.call(j,c,g)
};
b.alsoResize=b._alsoResize_?eval("("+("{'#gview_"+j.p.id+" .ui-jqgrid-bdiv':true,'"+b._alsoResize_+"':true}")+")"):a(".ui-jqgrid-bdiv","#gview_"+j.p.id);
delete b._alsoResize_;
a("#gbox_"+j.p.id).resizable(b)
}})
}})
})(jQuery);"use strict";
(function(a){a.vakata={};
a.vakata.css={get_css:function(f,c,d){f=f.toLowerCase();
var e=d.cssRules||d.rules,b=0;
do{if(e.length&&b>e.length+5){return false
}if(e[b].selectorText&&e[b].selectorText.toLowerCase()==f){if(c===true){if(d.removeRule){d.removeRule(b)
}if(d.deleteRule){d.deleteRule(b)
}return true
}else{return e[b]
}}}while(e[++b]);
return false
},add_css:function(c,b){if(a.jstree.css.get_css(c,false,b)){return false
}if(b.insertRule){b.insertRule(c+" { }",0)
}else{b.addRule(c,null,0)
}return a.vakata.css.get_css(c)
},remove_css:function(c,b){return a.vakata.css.get_css(c,true,b)
},add_sheet:function(c){var b;
if(c.str){b=document.createElement("style");
b.setAttribute("type","text/css");
if(b.styleSheet){document.getElementsByTagName("head")[0].appendChild(b);
b.styleSheet.cssText=c.str
}else{b.appendChild(document.createTextNode(c.str));
document.getElementsByTagName("head")[0].appendChild(b)
}return b.sheet||b.styleSheet
}if(c.url){if(document.createStyleSheet){try{b=document.createStyleSheet(c.url)
}catch(d){}}else{b=document.createElement("link");
b.rel="stylesheet";
b.type="text/css";
b.media="all";
b.href=c.url;
document.getElementsByTagName("head")[0].appendChild(b);
return b.styleSheet
}}}}
})(jQuery);
(function(e){var f=[],c=-1,b={},a={},d=false;
e.fn.jstree=function(j){var g=(typeof j=="string"),h=Array.prototype.slice.call(arguments,1),i=this;
if(!g&&e.meta){h.push(e.metadata.get(this).jstree)
}j=!g&&h.length?e.extend.apply(null,[true,j].concat(h)):j;
if(g&&j.substring(0,1)=="_"){return i
}if(g){this.each(function(){var k=f[e.data(this,"jstree-instance-id")],l=(k&&e.isFunction(k[j]))?k[j].apply(k,h):k;
if(typeof l!=="undefined"&&(j.indexOf("is_"===0)||(l!==true&&l!==false))){i=l;
return false
}})
}else{this.each(function(){var l=e.data(this,"jstree-instance-id"),k=false;
if(typeof l!=="undefined"&&f[l]){f[l].destroy()
}l=parseInt(f.push({}),10)-1;
e.data(this,"jstree-instance-id",l);
if(!j){j={}
}j.plugins=e.isArray(j.plugins)?j.plugins:e.jstree.defaults.plugins;
if(e.inArray("core",j.plugins)===-1){j.plugins.unshift("core")
}k=e.extend(true,{},e.jstree.defaults,j);
k.plugins=j.plugins;
e.each(b,function(m,n){if(e.inArray(m,k.plugins)===-1){k[m]=null;
delete k[m]
}});
f[l]=new e.jstree._instance(l,e(this).addClass("jstree jstree-"+l),k);
e.each(f[l]._get_settings().plugins,function(m,n){f[l].data[n]={}
});
e.each(f[l]._get_settings().plugins,function(m,n){if(b[n]){b[n].__init.apply(f[l])
}});
f[l].init()
})
}return i
};
e.jstree={defaults:{plugins:[]},_focused:function(){return f[c]||null
},_reference:function(g){if(f[g]){return f[g]
}var h=e(g);
if(!h.length&&typeof g==="string"){h=e("#"+g)
}if(!h.length){return null
}return f[h.closest(".jstree").data("jstree-instance-id")]||null
},_instance:function(h,g,i){this.data={core:{}};
this.get_settings=function(){return e.extend(true,{},i)
};
this._get_settings=function(){return i
};
this.get_index=function(){return h
};
this.get_container=function(){return g
};
this._set_settings=function(j){i=e.extend(true,{},i,j)
}
},_fn:{},plugin:function(g,h){h=e.extend({},{__init:e.noop,__destroy:e.noop,_fn:{},defaults:false},h);
b[g]=h;
e.jstree.defaults[g]=h.defaults;
e.each(h._fn,function(j,k){k.plugin=g;
k.old=e.jstree._fn[j];
e.jstree._fn[j]=function(){var i,m=k,l=Array.prototype.slice.call(arguments),o=new e.Event("before.jstree"),n=false;
do{if(m&&m.plugin&&e.inArray(m.plugin,this._get_settings().plugins)!==-1){break
}m=m.old
}while(m);
if(!m){return
}i=this.get_container().triggerHandler(o,{func:j,inst:this,args:l});
if(i===false){return
}if(typeof i!=="undefined"){l=i
}if(j.indexOf("_")===0){i=m.apply(this,l)
}else{i=m.apply(e.extend({},this,{__callback:function(p){this.get_container().triggerHandler(j+".jstree",{inst:this,args:l,rslt:p,rlbk:n})
},__rollback:function(){n=this.get_rollback();
return n
},__call_old:function(p){return m.old.apply(this,(p?Array.prototype.slice.call(arguments,1):l))
}}),l)
}return i
};
e.jstree._fn[j].old=k.old;
e.jstree._fn[j].plugin=g
})
},rollback:function(g){if(g){if(!e.isArray(g)){g=[g]
}e.each(g,function(h,j){f[j.i].set_rollback(j.h,j.d)
})
}}};
e.jstree._fn=e.jstree._instance.prototype={};
e(function(){var i=navigator.userAgent.toLowerCase(),h=(i.match(/.+?(?:rv|it|ra|ie)[\/: ]([\d.]+)/)||[0,"0"])[1],g=".jstree ul, .jstree li { display:block; margin:0 0 0 0; padding:0 0 0 0; list-style-type:none; } .jstree li { display:block; min-height:18px; line-height:18px; white-space:nowrap; margin-left:18px; } .jstree-rtl li { margin-left:0; margin-right:18px; } .jstree > ul > li { margin-left:0px; } .jstree-rtl > ul > li { margin-right:0px; } .jstree ins { display:inline-block; text-decoration:none; width:18px; height:18px; margin:0 0 0 0; padding:0; } .jstree a { display:inline-block; line-height:16px; height:16px; color:black; white-space:nowrap; text-decoration:none; padding:1px 2px; margin:0; } .jstree a:focus { outline: none; } .jstree a > ins { height:16px; width:16px; } .jstree a > .jstree-icon { margin-right:3px; } .jstree-rtl a > .jstree-icon { margin-left:3px; margin-right:0; } li.jstree-open > ul { display:block; } li.jstree-closed > ul { display:none; } ";
if(/msie/.test(i)&&parseInt(h,10)==6){d=true;
g+=".jstree li { height:18px; margin-left:0; margin-right:0; } .jstree li li { margin-left:18px; } .jstree-rtl li li { margin-left:0px; margin-right:18px; } li.jstree-open ul { display:block; } li.jstree-closed ul { display:none !important; } .jstree li a { display:inline; border-width:0 !important; padding:0px 2px !important; } .jstree li a ins { height:16px; width:16px; margin-right:3px; } .jstree-rtl li a ins { margin-right:0px; margin-left:3px; } "
}if(/msie/.test(i)&&parseInt(h,10)==7){g+=".jstree li a { border-width:0 !important; padding:0px 2px !important; } "
}e.vakata.css.add_sheet({str:g})
});
e.jstree.plugin("core",{__init:function(){this.data.core.to_open=e.map(e.makeArray(this.get_settings().core.initially_open),function(g){return"#"+g.toString().replace(/^#/,"").replace("\\/","/").replace("/","\\/")
})
},defaults:{html_titles:false,animation:500,initially_open:[],rtl:false,strings:{loading:"Loading ...",new_node:"New node"}},_fn:{init:function(){this.set_focus();
if(this._get_settings().core.rtl){this.get_container().addClass("jstree-rtl").css("direction","rtl")
}this.get_container().html("<ul><li class='jstree-last jstree-leaf'><ins>&#160;</ins><a class='jstree-loading' href='#'><ins class='jstree-icon'>&#160;</ins>"+this._get_settings().core.strings.loading+"</a></li></ul>");
this.data.core.li_height=this.get_container().find("ul li.jstree-closed, ul li.jstree-leaf").eq(0).height()||18;
this.get_container().delegate("li > ins","click.jstree",e.proxy(function(h){var g=e(h.target);
if(g.is("ins")&&h.pageY-g.offset().top<this.data.core.li_height){this.toggle_node(g)
}},this)).bind("mousedown.jstree",e.proxy(function(){this.set_focus()
},this)).bind("dblclick.jstree",function(h){var i;
if(document.selection&&document.selection.empty){document.selection.empty()
}else{if(window.getSelection){i=window.getSelection();
try{i.removeAllRanges();
i.collapse()
}catch(g){}}}});
this.__callback();
this.load_node(-1,function(){this.loaded();
this.reopen()
})
},destroy:function(){var g,k=this.get_index(),h=this._get_settings(),j=this;
e.each(h.plugins,function(l,n){try{b[n].__destroy.apply(j)
}catch(m){}});
this.__callback();
if(this.is_focused()){for(g in f){if(f.hasOwnProperty(g)&&g!=k){f[g].set_focus();
break
}}}if(k===c){c=-1
}this.get_container().unbind(".jstree").undelegate(".jstree").removeData("jstree-instance-id").find("[class^='jstree']").andSelf().attr("class",function(){return this.className.replace(/jstree[^ ]*|$/ig,"")
});
f[k]=null;
delete f[k]
},save_opened:function(){var g=this;
this.data.core.to_open=[];
this.get_container().find(".jstree-open").each(function(){g.data.core.to_open.push("#"+this.id.toString().replace(/^#/,"").replace("\\/","/").replace("/","\\/"))
});
this.__callback(g.data.core.to_open)
},reopen:function(h){var k=this,g=true,j=[],i=[];
if(!h){this.data.core.reopen=false;
this.data.core.refreshing=true
}if(this.data.core.to_open.length){e.each(this.data.core.to_open,function(l,m){if(m=="#"){return true
}if(e(m).length&&e(m).is(".jstree-closed")){j.push(m)
}else{i.push(m)
}});
if(j.length){this.data.core.to_open=i;
e.each(j,function(l,m){k.open_node(m,function(){k.reopen(true)
},true)
});
g=false
}}if(g){if(this.data.core.reopen){clearTimeout(this.data.core.reopen)
}this.data.core.reopen=setTimeout(function(){k.__callback({},k)
},50);
this.data.core.refreshing=false
}},refresh:function(g){var h=this;
this.save_opened();
if(!g){g=-1
}g=this._get_node(g);
if(!g){g=-1
}if(g!==-1){g.children("UL").remove()
}this.load_node(g,function(){h.__callback({obj:g});
h.reopen()
})
},loaded:function(){this.__callback()
},set_focus:function(){var g=e.jstree._focused();
if(g&&g!==this){g.get_container().removeClass("jstree-focused")
}if(g!==this){this.get_container().addClass("jstree-focused");
c=this.get_index()
}this.__callback()
},is_focused:function(){return c==this.get_index()
},_get_node:function(g){var h=e(g,this.get_container());
if(h.is(".jstree")||g==-1){return -1
}h=h.closest("li",this.get_container());
return h.length?h:false
},_get_next:function(h,g){h=this._get_node(h);
if(h===-1){return this.get_container().find("> ul > li:first-child")
}if(!h.length){return false
}if(g){return(h.nextAll("li").size()>0)?h.nextAll("li:eq(0)"):false
}if(h.hasClass("jstree-open")){return h.find("li:eq(0)")
}else{if(h.nextAll("li").size()>0){return h.nextAll("li:eq(0)")
}else{return h.parentsUntil(".jstree","li").next("li").eq(0)
}}},_get_prev:function(h,g){h=this._get_node(h);
if(h===-1){return this.get_container().find("> ul > li:last-child")
}if(!h.length){return false
}if(g){return(h.prevAll("li").length>0)?h.prevAll("li:eq(0)"):false
}if(h.prev("li").length){h=h.prev("li").eq(0);
while(h.hasClass("jstree-open")){h=h.children("ul:eq(0)").children("li:last")
}return h
}else{var i=h.parentsUntil(".jstree","li:eq(0)");
return i.length?i:false
}},_get_parent:function(g){g=this._get_node(g);
if(g==-1||!g.length){return false
}var h=g.parentsUntil(".jstree","li:eq(0)");
return h.length?h:-1
},_get_children:function(g){g=this._get_node(g);
if(g===-1){return this.get_container().children("ul:eq(0)").children("li")
}if(!g.length){return false
}return g.children("ul:eq(0)").children("li")
},get_path:function(i,g){var h=[],j=this;
i=this._get_node(i);
if(i===-1||!i||!i.length){return false
}i.parentsUntil(".jstree","li").each(function(){h.push(g?this.id:j.get_text(this))
});
h.reverse();
h.push(g?i.attr("id"):this.get_text(i));
return h
},is_open:function(g){g=this._get_node(g);
return g&&g!==-1&&g.hasClass("jstree-open")
},is_closed:function(g){g=this._get_node(g);
return g&&g!==-1&&g.hasClass("jstree-closed")
},is_leaf:function(g){g=this._get_node(g);
return g&&g!==-1&&g.hasClass("jstree-leaf")
},open_node:function(j,k,h){j=this._get_node(j);
if(!j.length){return false
}if(!j.hasClass("jstree-closed")){if(k){k.call()
}return false
}var i=h||d?0:this._get_settings().core.animation,g=this;
if(!this._is_loaded(j)){j.children("a").addClass("jstree-loading");
this.load_node(j,function(){g.open_node(j,k,h)
},k)
}else{if(i){j.children("ul").css("display","none")
}j.removeClass("jstree-closed").addClass("jstree-open").children("a").removeClass("jstree-loading");
if(i){j.children("ul").stop(true).slideDown(i,function(){this.style.display=""
})
}this.__callback({obj:j});
if(k){k.call()
}}},close_node:function(i,g){i=this._get_node(i);
var h=g||d?0:this._get_settings().core.animation;
if(!i.length||!i.hasClass("jstree-open")){return false
}if(h){i.children("ul").attr("style","display:block !important")
}i.removeClass("jstree-open").addClass("jstree-closed");
if(h){i.children("ul").stop(true).slideUp(h,function(){this.style.display=""
})
}this.__callback({obj:i})
},toggle_node:function(g){g=this._get_node(g);
if(g.hasClass("jstree-closed")){return this.open_node(g)
}if(g.hasClass("jstree-open")){return this.close_node(g)
}},open_all:function(h,g){h=h?this._get_node(h):this.get_container();
if(!h||h===-1){h=this.get_container()
}if(g){h=h.find("li.jstree-closed")
}else{g=h;
if(h.is(".jstree-closed")){h=h.find("li.jstree-closed").andSelf()
}else{h=h.find("li.jstree-closed")
}}var i=this;
h.each(function(){var j=this;
if(!i._is_loaded(this)){i.open_node(this,function(){i.open_all(j,g)
},true)
}else{i.open_node(this,false,true)
}});
if(g.find("li.jstree-closed").length===0){this.__callback({obj:g})
}},close_all:function(h,g){var i=this;
h=h?this._get_node(h):this.get_container();
if(!h||h===-1){h=this.get_container()
}h.find("li.jstree-open").andSelf().each(function(){i.close_node(this,g?g:false)
});
this.__callback({obj:h})
},clean_node:function(g){g=g&&g!=-1?e(g):this.get_container();
g=g.is("li")?g.find("li").andSelf():g.find("li");
g.removeClass("jstree-last").filter("li:last-child").addClass("jstree-last").end().filter(":has(li)").not(".jstree-open").removeClass("jstree-leaf").addClass("jstree-closed");
g.not(".jstree-open, .jstree-closed").addClass("jstree-leaf").children("ul").remove();
this.__callback({obj:g})
},get_rollback:function(){this.__callback();
return{i:this.get_index(),h:this.get_container().children("ul").clone(true),d:this.data}
},set_rollback:function(g,h){this.get_container().empty().append(g);
this.data=h;
this.__callback()
},load_node:function(i,g,h){this.__callback({obj:i})
},_is_loaded:function(g){return true
},create_node:function(l,g,k,n,h){l=this._get_node(l);
g=typeof g==="undefined"?"last":g;
var m=e("<li>"),j=this._get_settings().core,i;
if(l!==-1&&!l.length){return false
}if(!h&&!this._is_loaded(l)){this.load_node(l,function(){this.create_node(l,g,k,n,true)
});
return false
}this.__rollback();
if(typeof k==="string"){k={data:k}
}if(!k){k={}
}if(k.attr){m.attr(k.attr)
}if(k.state){m.addClass("jstree-"+k.state)
}if(!k.data){k.data=j.strings.new_node
}if(!e.isArray(k.data)){i=k.data;
k.data=[];
k.data.push(i)
}e.each(k.data,function(p,o){i=e("<a>");
if(e.isFunction(o)){o=o.call(this,k)
}if(typeof o=="string"){i.attr("href","#")[j.html_titles?"html":"text"](o)
}else{if(!o.attr){o.attr={}
}if(!o.attr.href){o.attr.href="#"
}i.attr(o.attr)[j.html_titles?"html":"text"](o.title);
if(o.language){i.addClass(o.language)
}}i.prepend("<ins class='jstree-icon'>&#160;</ins>");
if(o.icon){if(o.icon.indexOf("/")===-1){i.children("ins").addClass(o.icon)
}else{i.children("ins").css("background","url('"+o.icon+"') center center no-repeat")
}}m.append(i)
});
m.prepend("<ins class='jstree-icon'>&#160;</ins>");
if(l===-1){l=this.get_container();
if(g==="before"){g="first"
}if(g==="after"){g="last"
}}switch(g){case"before":l.before(m);
i=this._get_parent(l);
break;
case"after":l.after(m);
i=this._get_parent(l);
break;
case"inside":case"first":if(!l.children("ul").length){l.append("<ul>")
}l.children("ul").prepend(m);
i=l;
break;
case"last":if(!l.children("ul").length){l.append("<ul>")
}l.children("ul").append(m);
i=l;
break;
default:if(!l.children("ul").length){l.append("<ul>")
}if(!g){g=0
}i=l.children("ul").children("li").eq(g);
if(i.length){i.before(m)
}else{l.children("ul").append(m)
}i=l;
break
}if(i===-1||i.get(0)===this.get_container().get(0)){i=-1
}this.clean_node(i);
this.__callback({obj:m,parent:i});
if(n){n.call(this,m)
}return m
},get_text:function(h){h=this._get_node(h);
if(!h.length){return false
}var g=this._get_settings().core.html_titles;
h=h.children("a:eq(0)");
if(g){h=h.clone();
h.children("INS").remove();
return h.html()
}else{h=h.contents().filter(function(){return this.nodeType==3
})[0];
return h.nodeValue
}},set_text:function(h,i){h=this._get_node(h);
if(!h.length){return false
}h=h.children("a:eq(0)");
if(this._get_settings().core.html_titles){var g=h.children("INS").clone();
h.html(i).prepend(g);
this.__callback({obj:h,name:i});
return true
}else{h=h.contents().filter(function(){return this.nodeType==3
})[0];
this.__callback({obj:h,name:i});
return(h.nodeValue=i)
}},rename_node:function(g,h){g=this._get_node(g);
this.__rollback();
if(g&&g.length&&this.set_text.apply(this,Array.prototype.slice.call(arguments))){this.__callback({obj:g,name:h})
}},delete_node:function(i){i=this._get_node(i);
if(!i.length){return false
}this.__rollback();
var h=this._get_parent(i),g=this._get_prev(i);
i=i.remove();
if(h!==-1&&h.find("> ul > li").length===0){h.removeClass("jstree-open jstree-closed").addClass("jstree-leaf")
}this.clean_node(h);
this.__callback({obj:i,prev:g});
return i
},prepare_move:function(k,i,l,g,h){var j={};
j.ot=e.jstree._reference(j.o)||this;
j.o=j.ot._get_node(k);
j.r=i===-1?-1:this._get_node(i);
j.p=(typeof j==="undefined")?"last":l;
if(!h&&a.o&&a.o[0]===j.o[0]&&a.r[0]===j.r[0]&&a.p===j.p){this.__callback(a);
if(g){g.call(this,a)
}return
}j.ot=e.jstree._reference(j.o)||this;
j.rt=i===-1?j.ot:e.jstree._reference(j.r)||this;
if(j.r===-1){j.cr=-1;
switch(j.p){case"first":case"before":case"inside":j.cp=0;
break;
case"after":case"last":j.cp=j.rt.get_container().find(" > ul > li").length;
break;
default:j.cp=j.p;
break
}}else{if(!/^(before|after)$/.test(j.p)&&!this._is_loaded(j.r)){return this.load_node(j.r,function(){this.prepare_move(k,i,l,g,true)
})
}switch(j.p){case"before":j.cp=j.r.index();
j.cr=j.rt._get_parent(j.r);
break;
case"after":j.cp=j.r.index()+1;
j.cr=j.rt._get_parent(j.r);
break;
case"inside":case"first":j.cp=0;
j.cr=j.r;
break;
case"last":j.cp=j.r.find(" > ul > li").length;
j.cr=j.r;
break;
default:j.cp=j.p;
j.cr=j.r;
break
}}j.np=j.cr==-1?j.rt.get_container():j.cr;
j.op=j.ot._get_parent(j.o);
j.or=j.np.find(" > ul > li:nth-child("+(j.cp+1)+")");
a=j;
this.__callback(a);
if(g){g.call(this,a)
}},check_move:function(){var h=a,g=true;
if(h.or[0]===h.o[0]){return false
}h.o.each(function(){if(h.r.parentsUntil(".jstree").andSelf().filter("li").index(this)!==-1){g=false;
return false
}});
return g
},move_node:function(m,j,g,i,h,l){if(!h){return this.prepare_move(m,j,g,function(o){this.move_node(o,false,false,i,true,l)
})
}if(!l&&!this.check_move()){return false
}this.__rollback();
var n=false;
if(i){n=m.o.clone();
n.find("*[id]").andSelf().each(function(){if(this.id){this.id="copy_"+this.id
}})
}else{n=m.o
}if(m.or.length){m.or.before(n)
}else{if(!m.np.children("ul").length){e("<ul>").appendTo(m.np)
}m.np.children("ul:eq(0)").append(n)
}try{m.ot.clean_node(m.op);
m.rt.clean_node(m.np);
if(!m.op.find("> ul > li").length){m.op.removeClass("jstree-open jstree-closed").addClass("jstree-leaf").children("ul").remove()
}}catch(k){}if(i){a.cy=true;
a.oc=n
}this.__callback(a);
return a
},_get_move:function(){return a
}}})
})(jQuery);
(function(a){a.jstree.plugin("ui",{__init:function(){this.data.ui.selected=a();
this.data.ui.last_selected=false;
this.data.ui.hovered=null;
this.data.ui.to_select=this.get_settings().ui.initially_select;
this.get_container().delegate("a","click.jstree",a.proxy(function(b){b.preventDefault();
this.select_node(b.currentTarget,true,b)
},this)).delegate("a","mouseenter.jstree",a.proxy(function(b){this.hover_node(b.target)
},this)).delegate("a","mouseleave.jstree",a.proxy(function(b){this.dehover_node(b.target)
},this)).bind("reopen.jstree",a.proxy(function(){this.reselect()
},this)).bind("get_rollback.jstree",a.proxy(function(){this.dehover_node();
this.save_selected()
},this)).bind("set_rollback.jstree",a.proxy(function(){this.reselect()
},this)).bind("close_node.jstree",a.proxy(function(c,d){var b=this._get_settings().ui,e=this._get_node(d.rslt.obj),f=(e&&e.length)?e.children("ul").find(".jstree-clicked"):a(),g=this;
if(b.selected_parent_close===false||!f.length){return
}f.each(function(){g.deselect_node(this);
if(b.selected_parent_close==="select_parent"){g.select_node(e)
}})
},this)).bind("delete_node.jstree",a.proxy(function(c,d){var b=this._get_settings().ui.select_prev_on_delete,e=this._get_node(d.rslt.obj),f=(e&&e.length)?e.find(".jstree-clicked"):[],g=this;
f.each(function(){g.deselect_node(this)
});
if(b&&f.length){this.select_node(d.rslt.prev)
}},this)).bind("move_node.jstree",a.proxy(function(b,c){if(c.rslt.cy){c.rslt.oc.find(".jstree-clicked").removeClass("jstree-clicked")
}},this))
},defaults:{select_limit:-1,select_multiple_modifier:"ctrl",selected_parent_close:"select_parent",select_prev_on_delete:true,disable_selecting_children:false,initially_select:[]},_fn:{_get_node:function(b,c){if(typeof b==="undefined"||b===null){return c?this.data.ui.selected:this.data.ui.last_selected
}var d=a(b,this.get_container());
if(d.is(".jstree")||b==-1){return -1
}d=d.closest("li",this.get_container());
return d.length?d:false
},save_selected:function(){var b=this;
this.data.ui.to_select=[];
this.data.ui.selected.each(function(){b.data.ui.to_select.push("#"+this.id.toString().replace(/^#/,"").replace("\\/","/").replace("/","\\/"))
});
this.__callback(this.data.ui.to_select)
},reselect:function(){var c=this,b=this.data.ui.to_select;
b=a.map(a.makeArray(b),function(d){return"#"+d.toString().replace(/^#/,"").replace("\\/","/").replace("/","\\/")
});
this.deselect_all();
a.each(b,function(d,e){if(e&&e!=="#"){c.select_node(e)
}});
this.__callback()
},refresh:function(b){this.save_selected();
return this.__call_old()
},hover_node:function(b){b=this._get_node(b);
if(!b.length){return false
}if(!b.hasClass("jstree-hovered")){this.dehover_node()
}this.data.ui.hovered=b.children("a").addClass("jstree-hovered").parent();
this.__callback({obj:b})
},dehover_node:function(){var c=this.data.ui.hovered,b;
if(!c||!c.length){return false
}b=c.children("a").removeClass("jstree-hovered").parent();
if(this.data.ui.hovered[0]===b[0]){this.data.ui.hovered=null
}this.__callback({obj:c})
},select_node:function(h,b,g){h=this._get_node(h);
if(h==-1||!h||!h.length){return false
}var d=this._get_settings().ui,c=(d.select_multiple_modifier=="on"||(d.select_multiple_modifier!==false&&g&&g[d.select_multiple_modifier+"Key"])),i=this.is_selected(h),f=true;
if(b){if(d.disable_selecting_children&&c&&h.parents("li",this.get_container()).children(".jstree-clicked").length){return false
}f=false;
switch(!0){case (i&&!c):this.deselect_all();
i=false;
f=true;
break;
case (!i&&!c):if(d.select_limit==-1||d.select_limit>0){this.deselect_all();
f=true
}break;
case (i&&c):this.deselect_node(h);
break;
case (!i&&c):if(d.select_limit==-1||this.data.ui.selected.length+1<=d.select_limit){f=true
}break
}}if(f&&!i){h.children("a").addClass("jstree-clicked");
this.data.ui.selected=this.data.ui.selected.add(h);
this.data.ui.last_selected=h;
this.__callback({obj:h})
}},deselect_node:function(b){b=this._get_node(b);
if(!b.length){return false
}if(this.is_selected(b)){b.children("a").removeClass("jstree-clicked");
this.data.ui.selected=this.data.ui.selected.not(b);
if(this.data.ui.last_selected.get(0)===b.get(0)){this.data.ui.last_selected=this.data.ui.selected.eq(0)
}this.__callback({obj:b})
}},toggle_select:function(b){b=this._get_node(b);
if(!b.length){return false
}if(this.is_selected(b)){this.deselect_node(b)
}else{this.select_node(b)
}},is_selected:function(b){return this.data.ui.selected.index(this._get_node(b))>=0
},get_selected:function(b){return b?a(b).find(".jstree-clicked").parent():this.data.ui.selected
},deselect_all:function(b){if(b){a(b).find(".jstree-clicked").removeClass("jstree-clicked")
}else{this.get_container().find(".jstree-clicked").removeClass("jstree-clicked")
}this.data.ui.selected=a([]);
this.data.ui.last_selected=false;
this.__callback()
}}});
a.jstree.defaults.plugins.push("ui")
})(jQuery);
(function(a){a.jstree.plugin("crrm",{__init:function(){this.get_container().bind("move_node.jstree",a.proxy(function(d,c){if(this._get_settings().crrm.move.open_onmove){var b=this;
c.rslt.np.parentsUntil(".jstree").andSelf().filter(".jstree-closed").each(function(){b.open_node(this,false,true)
})
}},this))
},defaults:{input_width_limit:200,move:{always_copy:false,open_onmove:true,default_position:"last",check_move:function(b){return true
}}},_fn:{_show_input:function(b,i){b=this._get_node(b);
var g=this._get_settings().core.rtl,h=this._get_settings().crrm.input_width_limit,d=b.children("ins").width(),c=b.find("> a:visible > ins").width()*b.find("> a:visible > ins").length,j=this.get_text(b),f=a("<div>",{css:{position:"absolute",top:"-200px",left:(g?"0px":"-1000px"),visibility:"hidden"}}).appendTo("body"),e=b.css("position","relative").append(a("<input>",{value:j,css:{padding:"0",border:"1px solid silver",position:"absolute",left:(g?"auto":(d+c+4)+"px"),right:(g?(d+c+4)+"px":"auto"),top:"0px",height:(this.data.core.li_height-2)+"px",lineHeight:(this.data.core.li_height-2)+"px",width:"150px"},blur:a.proxy(function(){var l=b.children("input"),k=l.val();
if(k===""){k=j
}l.remove();
this.set_text(b,j);
this.rename_node(b,k);
i.call(this,b,k,j);
b.css("position","")
},this),keyup:function(l){var k=l.keyCode||l.which;
if(k==27){this.value=j;
this.blur();
return
}else{if(k==13){this.blur();
return
}else{e.width(Math.min(f.text("pW"+this.value).width(),h))
}}}})).children("input");
this.set_text(b,"");
f.css({fontFamily:e.css("fontFamily")||"",fontSize:e.css("fontSize")||"",fontWeight:e.css("fontWeight")||"",fontStyle:e.css("fontStyle")||"",fontStretch:e.css("fontStretch")||"",fontVariant:e.css("fontVariant")||"",letterSpacing:e.css("letterSpacing")||"",wordSpacing:e.css("wordSpacing")||""});
e.width(Math.min(f.text("pW"+e[0].value).width(),h))[0].select()
},rename:function(c){c=this._get_node(c);
this.__rollback();
var b=this.__callback;
this._show_input(c,function(f,e,d){b.call(this,{obj:f,new_name:e,old_name:d})
})
},create:function(f,c,e,h,b){var d,g=this;
f=this._get_node(f);
if(!f){f=-1
}this.__rollback();
d=this.create_node(f,c,e,function(i){var j=this._get_parent(i),k=a(i).index();
if(h){h.call(this,i)
}if(j.length&&j.hasClass("jstree-closed")){this.open_node(j,false,true)
}if(!b){this._show_input(i,function(n,m,l){g.__callback({obj:n,name:m,parent:j,position:k})
})
}else{g.__callback({obj:i,name:this.get_text(i),parent:j,position:k})
}});
return d
},remove:function(b){b=this._get_node(b,true);
this.__rollback();
this.delete_node(b);
this.__callback({obj:b})
},check_move:function(){if(!this.__call_old()){return false
}var b=this._get_settings().crrm.move;
if(!b.check_move.call(this,this._get_move())){return false
}return true
},move_node:function(h,f,b,d,c,g){var e=this._get_settings().crrm.move;
if(!c){if(!b){b=e.default_position
}if(b==="inside"&&!e.default_position.match(/^(before|after)$/)){b=e.default_position
}return this.__call_old(true,h,f,b,d,false,g)
}if(e.always_copy===true||(e.always_copy==="multitree"&&h.rt.get_index()!==h.ot.get_index())){d=true
}this.__call_old(true,h,f,b,d,true,g)
},cut:function(b){b=this._get_node(b);
this.data.crrm.cp_nodes=false;
this.data.crrm.ct_nodes=false;
if(!b||!b.length){return false
}this.data.crrm.ct_nodes=b
},copy:function(b){b=this._get_node(b);
this.data.crrm.cp_nodes=false;
this.data.crrm.ct_nodes=false;
if(!b||!b.length){return false
}this.data.crrm.cp_nodes=b
},paste:function(b){b=this._get_node(b);
if(!b||!b.length){return false
}if(!this.data.crrm.ct_nodes&&!this.data.crrm.cp_nodes){return false
}if(this.data.crrm.ct_nodes){this.move_node(this.data.crrm.ct_nodes,b)
}if(this.data.crrm.cp_nodes){this.move_node(this.data.crrm.cp_nodes,b,false,true)
}this.data.crrm.cp_nodes=false;
this.data.crrm.ct_nodes=false
}}});
a.jstree.defaults.plugins.push("crrm")
})(jQuery);
(function(a){var b=[];
a.jstree._themes=false;
a.jstree.plugin("themes",{__init:function(){this.get_container().bind("init.jstree",a.proxy(function(){var c=this._get_settings().themes;
this.data.themes.dots=c.dots;
this.data.themes.icons=c.icons;
this.set_theme(c.theme,c.url)
},this)).bind("loaded.jstree",a.proxy(function(){if(!this.data.themes.dots){this.hide_dots()
}else{this.show_dots()
}if(!this.data.themes.icons){this.hide_icons()
}else{this.show_icons()
}},this))
},defaults:{theme:"default",url:false,dots:true,icons:true},_fn:{set_theme:function(d,c){if(!d){return false
}if(!c){c=a.jstree._themes+d+"/style.css"
}if(a.inArray(c,b)==-1){a.vakata.css.add_sheet({url:c,rel:"jstree"});
b.push(c)
}if(this.data.themes.theme!=d){this.get_container().removeClass("jstree-"+this.data.themes.theme);
this.data.themes.theme=d
}this.get_container().addClass("jstree-"+d);
if(!this.data.themes.dots){this.hide_dots()
}else{this.show_dots()
}if(!this.data.themes.icons){this.hide_icons()
}else{this.show_icons()
}this.__callback()
},get_theme:function(){return this.data.themes.theme
},show_dots:function(){this.data.themes.dots=true;
this.get_container().children("ul").removeClass("jstree-no-dots")
},hide_dots:function(){this.data.themes.dots=false;
this.get_container().children("ul").addClass("jstree-no-dots")
},toggle_dots:function(){if(this.data.themes.dots){this.hide_dots()
}else{this.show_dots()
}},show_icons:function(){this.data.themes.icons=true;
this.get_container().children("ul").removeClass("jstree-no-icons")
},hide_icons:function(){this.data.themes.icons=false;
this.get_container().children("ul").addClass("jstree-no-icons")
},toggle_icons:function(){if(this.data.themes.icons){this.hide_icons()
}else{this.show_icons()
}}}});
a(function(){if(a.jstree._themes===false){a("script").each(function(){if(this.src.toString().match(/jquery\.jstree[^\/]*?\.js(\?.*)?$/)){a.jstree._themes=this.src.toString().replace(/jquery\.jstree[^\/]*?\.js(\?.*)?$/,"")+"themes/";
return false
}})
}if(a.jstree._themes===false){a.jstree._themes="themes/"
}});
a.jstree.defaults.plugins.push("themes")
})(jQuery);
(function(c){var b=[];
function a(e,g){var h=c.jstree._focused(),d;
if(h&&h.data&&h.data.hotkeys&&h.data.hotkeys.enabled){d=h._get_settings().hotkeys[e];
if(d){return d.call(h,g)
}}}c.jstree.plugin("hotkeys",{__init:function(){if(typeof c.hotkeys==="undefined"){throw"jsTree hotkeys: jQuery hotkeys plugin not included."
}if(!this.data.ui){throw"jsTree hotkeys: jsTree UI plugin not included."
}c.each(this._get_settings().hotkeys,function(d,e){if(c.inArray(d,b)==-1){c(document).bind("keydown",d,function(f){return a(d,f)
});
b.push(d)
}});
this.enable_hotkeys()
},defaults:{up:function(){var d=this.data.ui.hovered||this.data.ui.last_selected||-1;
this.hover_node(this._get_prev(d));
return false
},down:function(){var d=this.data.ui.hovered||this.data.ui.last_selected||-1;
this.hover_node(this._get_next(d));
return false
},left:function(){var d=this.data.ui.hovered||this.data.ui.last_selected;
if(d){if(d.hasClass("jstree-open")){this.close_node(d)
}else{this.hover_node(this._get_prev(d))
}}return false
},right:function(){var d=this.data.ui.hovered||this.data.ui.last_selected;
if(d&&d.length){if(d.hasClass("jstree-closed")){this.open_node(d)
}else{this.hover_node(this._get_next(d))
}}return false
},space:function(){if(this.data.ui.hovered){this.data.ui.hovered.children("a:eq(0)").click()
}return false
},"ctrl+space":function(d){d.type="click";
if(this.data.ui.hovered){this.data.ui.hovered.children("a:eq(0)").trigger(d)
}return false
},f2:function(){this.rename(this.data.ui.hovered||this.data.ui.last_selected)
},del:function(){this.remove(this.data.ui.hovered||this._get_node(null))
}},_fn:{enable_hotkeys:function(){this.data.hotkeys.enabled=true
},disable_hotkeys:function(){this.data.hotkeys.enabled=false
}}})
})(jQuery);
(function(a){a.jstree.plugin("json_data",{defaults:{data:false,ajax:false,correct_state:true,progressive_render:false},_fn:{load_node:function(d,b,c){var e=this;
this.load_node_json(d,function(){e.__callback({obj:d});
b.call(this)
},c)
},_is_loaded:function(c){var b=this._get_settings().json_data,e;
c=this._get_node(c);
if(c&&c!==-1&&b.progressive_render&&!c.is(".jstree-open, .jstree-leaf")&&c.children("ul").children("li").length===0&&c.data("jstree-children")){e=this._parse_json(c.data("jstree-children"));
if(e){c.append(e);
a.removeData(c,"jstree-children")
}this.clean_node(c);
return true
}return c==-1||!c||!b.ajax||c.is(".jstree-open, .jstree-leaf")||c.children("ul").children("li").size()>0
},load_node_json:function(g,b,e){var f=this.get_settings().json_data,i,c=function(){},h=function(){};
g=this._get_node(g);
if(g&&g!==-1){if(g.data("jstree-is-loading")){return
}else{g.data("jstree-is-loading",true)
}}switch(!0){case (!f.data&&!f.ajax):throw"Neither data nor ajax settings supplied.";
case (!!f.data&&!f.ajax)||(!!f.data&&!!f.ajax&&(!g||g===-1)):if(!g||g==-1){i=this._parse_json(f.data);
if(i){this.get_container().children("ul").empty().append(i.children());
this.clean_node()
}else{if(f.correct_state){this.get_container().children("ul").empty()
}}}if(b){b.call(this)
}break;
case (!f.data&&!!f.ajax)||(!!f.data&&!!f.ajax&&g&&g!==-1):c=function(j,k,l){var d=this.get_settings().json_data.ajax.error;
if(d){d.call(this,j,k,l)
}if(g!=-1&&g.length){g.children(".jstree-loading").removeClass("jstree-loading");
g.data("jstree-is-loading",false);
if(k==="success"&&f.correct_state){g.removeClass("jstree-open jstree-closed").addClass("jstree-leaf")
}}else{if(k==="success"&&f.correct_state){this.get_container().children("ul").empty()
}}if(e){e.call(this)
}};
h=function(m,k,j){var l=this.get_settings().json_data.ajax.success;
if(l){m=l.call(this,m,k,j)||m
}if(m===""||(!a.isArray(m)&&!a.isPlainObject(m))){return c.call(this,j,k,"")
}m=this._parse_json(m);
if(m){if(g===-1||!g){this.get_container().children("ul").empty().append(m.children())
}else{g.append(m).children(".jstree-loading").removeClass("jstree-loading");
g.data("jstree-is-loading",false)
}this.clean_node(g);
if(b){b.call(this)
}}else{if(g===-1||!g){if(f.correct_state){this.get_container().children("ul").empty();
if(b){b.call(this)
}}}else{g.children(".jstree-loading").removeClass("jstree-loading");
g.data("jstree-is-loading",false);
if(f.correct_state){g.removeClass("jstree-open jstree-closed").addClass("jstree-leaf");
if(b){b.call(this)
}}}}};
f.ajax.context=this;
f.ajax.error=c;
f.ajax.success=h;
if(!f.ajax.dataType){f.ajax.dataType="json"
}if(a.isFunction(f.ajax.url)){f.ajax.url=f.ajax.url.call(this,g)
}if(a.isFunction(f.ajax.data)){f.ajax.data=f.ajax.data.call(this,g)
}a.ajax(f.ajax);
break
}},_parse_json:function(b,m){var h=false,c=this._get_settings(),o=c.json_data,n=c.core.html_titles,f,g,e,l,k;
if(!b){return h
}if(a.isFunction(b)){b=b.call(this)
}if(a.isArray(b)){h=a();
if(!b.length){return false
}for(g=0,e=b.length;
g<e;
g++){f=this._parse_json(b[g],true);
if(f.length){h=h.add(f)
}}}else{if(typeof b=="string"){b={data:b}
}if(!b.data&&b.data!==""){return h
}h=a("<li>");
if(b.attr){h.attr(b.attr)
}if(b.metadata){h.data("jstree",b.metadata)
}if(b.state){h.addClass("jstree-"+b.state)
}if(!a.isArray(b.data)){f=b.data;
b.data=[];
b.data.push(f)
}a.each(b.data,function(j,d){f=a("<a>");
if(a.isFunction(d)){d=d.call(this,b)
}if(typeof d=="string"){f.attr("href","#")[n?"html":"text"](d)
}else{if(!d.attr){d.attr={}
}if(!d.attr.href){d.attr.href="#"
}f.attr(d.attr)[n?"html":"text"](d.title);
if(d.language){f.addClass(d.language)
}}f.prepend("<ins class='jstree-icon'>&#160;</ins>");
if(!d.icon&&b.icon){d.icon=b.icon
}if(d.icon){if(d.icon.indexOf("/")===-1){f.children("ins").addClass(d.icon)
}else{f.children("ins").css("background","url('"+d.icon+"') center center no-repeat")
}}h.append(f)
});
h.prepend("<ins class='jstree-icon'>&#160;</ins>");
if(b.children){if(o.progressive_render&&b.state!=="open"){h.addClass("jstree-closed").data("jstree-children",b.children)
}else{if(a.isFunction(b.children)){b.children=b.children.call(this,b)
}if(a.isArray(b.children)&&b.children.length){f=this._parse_json(b.children,true);
if(f.length){k=a("<ul>");
k.append(f);
h.append(k)
}}}}}if(!m){l=a("<ul>");
l.append(h);
h=l
}return h
},get_json:function(g,d,b,k){var n=[],m=this._get_settings(),h=this,f,e,j,i,l,c;
g=this._get_node(g);
if(!g||g===-1){g=this.get_container().find("> ul > li")
}d=a.isArray(d)?d:["id","class"];
if(!k&&this.data.types){d.push(m.types.type_attr)
}b=a.isArray(b)?b:[];
g.each(function(){j=a(this);
f={data:[]};
if(d.length){f.attr={}
}a.each(d,function(p,o){e=j.attr(o);
if(e&&e.length&&e.replace(/jstree[^ ]*|$/ig,"").length){f.attr[o]=e.replace(/jstree[^ ]*|$/ig,"")
}});
if(j.hasClass("jstree-open")){f.state="open"
}if(j.hasClass("jstree-closed")){f.state="closed"
}i=j.children("a");
i.each(function(){l=a(this);
if(b.length||a.inArray("languages",m.plugins)!==-1||l.children("ins").get(0).style.backgroundImage.length||(l.children("ins").get(0).className&&l.children("ins").get(0).className.replace(/jstree[^ ]*|$/ig,"").length)){c=false;
if(a.inArray("languages",m.plugins)!==-1&&a.isArray(m.languages)&&m.languages.length){a.each(m.languages,function(o,p){if(l.hasClass(p)){c=p;
return false
}})
}e={attr:{},title:h.get_text(l,c)};
a.each(b,function(o,p){f.attr[p]=(l.attr(p)||"").replace(/jstree[^ ]*|$/ig,"")
});
if(l.children("ins").get(0).className.replace(/jstree[^ ]*|$/ig,"").replace(/^\s+$/ig,"").length){e.icon=l.children("ins").get(0).className.replace(/jstree[^ ]*|$/ig,"").replace(/^\s+$/ig,"")
}if(l.children("ins").get(0).style.backgroundImage.length){e.icon=l.children("ins").get(0).style.backgroundImage.replace("url(","").replace(")","")
}}else{e=h.get_text(l)
}if(i.length>1){f.data.push(e)
}else{f.data=e
}});
j=j.find("> ul > li");
if(j.length){f.children=h.get_json(j,d,b,true)
}n.push(f)
});
return n
}}})
})(jQuery);
(function(a){a.jstree.plugin("languages",{__init:function(){this._load_css()
},defaults:[],_fn:{set_lang:function(d){var e=this._get_settings().languages,c=false,b=".jstree-"+this.get_index()+" a";
if(!a.isArray(e)||e.length===0){return false
}if(a.inArray(d,e)==-1){if(!!e[d]){d=e[d]
}else{return false
}}if(d==this.data.languages.current_language){return true
}c=a.vakata.css.get_css(b+"."+this.data.languages.current_language,false,this.data.languages.language_css);
if(c!==false){c.style.display="none"
}c=a.vakata.css.get_css(b+"."+d,false,this.data.languages.language_css);
if(c!==false){c.style.display=""
}this.data.languages.current_language=d;
this.__callback(d);
return true
},get_lang:function(){return this.data.languages.current_language
},get_text:function(d,e){d=this._get_node(d)||this.data.ui.last_selected;
if(!d.size()){return false
}var c=this._get_settings().languages,b=this._get_settings().core.html_titles;
if(a.isArray(c)&&c.length){e=(e&&a.inArray(e,c)!=-1)?e:this.data.languages.current_language;
d=d.children("a."+e)
}else{d=d.children("a:eq(0)")
}if(b){d=d.clone();
d.children("INS").remove();
return d.html()
}else{d=d.contents().filter(function(){return this.nodeType==3
})[0];
return d.nodeValue
}},set_text:function(e,g,f){e=this._get_node(e)||this.data.ui.last_selected;
if(!e.size()){return false
}var d=this._get_settings().languages,c=this._get_settings().core.html_titles,b;
if(a.isArray(d)&&d.length){f=(f&&a.inArray(f,d)!=-1)?f:this.data.languages.current_language;
e=e.children("a."+f)
}else{e=e.children("a:eq(0)")
}if(c){b=e.children("INS").clone();
e.html(g).prepend(b);
this.__callback({obj:e,name:g,lang:f});
return true
}else{e=e.contents().filter(function(){return this.nodeType==3
})[0];
this.__callback({obj:e,name:g,lang:f});
return(e.nodeValue=g)
}},_load_css:function(){var d=this._get_settings().languages,e="/* languages css */",b=".jstree-"+this.get_index()+" a",c;
if(a.isArray(d)&&d.length){this.data.languages.current_language=d[0];
for(c=0;
c<d.length;
c++){e+=b+"."+d[c]+" {";
if(d[c]!=this.data.languages.current_language){e+=" display:none; "
}e+=" } "
}this.data.languages.language_css=a.vakata.css.add_sheet({str:e})
}},create_node:function(e,b,d,f){var c=this.__call_old(true,e,b,d,function(h){var j=this._get_settings().languages,g=h.children("a"),i;
if(a.isArray(j)&&j.length){for(i=0;
i<j.length;
i++){if(!g.is("."+j[i])){h.append(g.eq(0).clone().removeClass(j.join(" ")).addClass(j[i]))
}}g.not("."+j.join(", .")).remove()
}if(f){f.call(this,h)
}});
return c
}}})
})(jQuery);
(function(a){a.jstree.plugin("cookies",{__init:function(){if(typeof a.cookie==="undefined"){throw"jsTree cookie: jQuery cookie plugin not included."
}var c=this._get_settings().cookies,b;
if(!!c.save_opened){b=a.cookie(c.save_opened);
if(b&&b.length){this.data.core.to_open=b.split(",")
}}if(!!c.save_selected){b=a.cookie(c.save_selected);
if(b&&b.length&&this.data.ui){this.data.ui.to_select=b.split(",")
}}this.get_container().one((this.data.ui?"reselect":"reopen")+".jstree",a.proxy(function(){this.get_container().bind("open_node.jstree close_node.jstree select_node.jstree deselect_node.jstree",a.proxy(function(d){if(this._get_settings().cookies.auto_save){this.save_cookie((d.handleObj.namespace+d.handleObj.type).replace("jstree",""))
}},this))
},this))
},defaults:{save_opened:"jstree_open",save_selected:"jstree_select",auto_save:true,cookie_options:{}},_fn:{save_cookie:function(d){if(this.data.core.refreshing){return
}var b=this._get_settings().cookies;
if(!d){if(b.save_opened){this.save_opened();
a.cookie(b.save_opened,this.data.core.to_open.join(","),b.cookie_options)
}if(b.save_selected&&this.data.ui){this.save_selected();
a.cookie(b.save_selected,this.data.ui.to_select.join(","),b.cookie_options)
}return
}switch(d){case"open_node":case"close_node":if(!!b.save_opened){this.save_opened();
a.cookie(b.save_opened,this.data.core.to_open.join(","),b.cookie_options)
}break;
case"select_node":case"deselect_node":if(!!b.save_selected&&this.data.ui){this.save_selected();
a.cookie(b.save_selected,this.data.ui.to_select.join(","),b.cookie_options)
}break
}}}});
a.jstree.defaults.plugins.push("cookies")
})(jQuery);
(function(a){a.jstree.plugin("sort",{__init:function(){this.get_container().bind("load_node.jstree",a.proxy(function(d,b){var c=this._get_node(b.rslt.obj);
c=c===-1?this.get_container().children("ul"):c.children("ul");
this.sort(c)
},this)).bind("rename_node.jstree",a.proxy(function(c,b){this.sort(b.rslt.obj.parent())
},this)).bind("move_node.jstree",a.proxy(function(d,c){var b=c.rslt.np==-1?this.get_container():c.rslt.np;
this.sort(b.children("ul"))
},this))
},defaults:function(d,c){return this.get_text(d)>this.get_text(c)?1:-1
},_fn:{sort:function(d){var c=this._get_settings().sort,b=this;
d.append(a.makeArray(d.children("li")).sort(a.proxy(c,b)));
d.find("> li > ul").each(function(){b.sort(a(this))
});
this.clean_node(d)
}}})
})(jQuery);
(function(f){var h=false,c=false,a=false,b=false,e=false,g=false,d=false;
f.vakata.dnd={is_down:false,is_drag:false,helper:false,scroll_spd:10,init_x:0,init_y:0,threshold:5,user_data:{},drag_start:function(l,k,i){if(f.vakata.dnd.is_drag){f.vakata.drag_stop({})
}try{l.currentTarget.unselectable="on";
l.currentTarget.onselectstart=function(){return false
};
if(l.currentTarget.style){l.currentTarget.style.MozUserSelect="none"
}}catch(j){}f.vakata.dnd.init_x=l.pageX;
f.vakata.dnd.init_y=l.pageY;
f.vakata.dnd.user_data=k;
f.vakata.dnd.is_down=true;
f.vakata.dnd.helper=f("<div id='vakata-dragged'>").html(i).css("opacity","0.75");
f(document).bind("mousemove",f.vakata.dnd.drag);
f(document).bind("mouseup",f.vakata.dnd.drag_stop);
return false
},drag:function(k){if(!f.vakata.dnd.is_down){return
}if(!f.vakata.dnd.is_drag){if(Math.abs(k.pageX-f.vakata.dnd.init_x)>5||Math.abs(k.pageY-f.vakata.dnd.init_y)>5){f.vakata.dnd.helper.appendTo("body");
f.vakata.dnd.is_drag=true;
f(document).triggerHandler("drag_start.vakata",{event:k,data:f.vakata.dnd.user_data})
}else{return
}}if(k.type==="mousemove"){var m=f(document),j=m.scrollTop(),i=m.scrollLeft();
if(k.pageY-j<20){if(e&&g==="down"){clearInterval(e);
e=false
}if(!e){g="up";
e=setInterval(function(){f(document).scrollTop(f(document).scrollTop()-f.vakata.dnd.scroll_spd)
},150)
}}else{if(e&&g==="up"){clearInterval(e);
e=false
}}if(f(window).height()-(k.pageY-j)<20){if(e&&g==="up"){clearInterval(e);
e=false
}if(!e){g="down";
e=setInterval(function(){f(document).scrollTop(f(document).scrollTop()+f.vakata.dnd.scroll_spd)
},150)
}}else{if(e&&g==="down"){clearInterval(e);
e=false
}}if(k.pageX-i<20){if(b&&d==="right"){clearInterval(b);
b=false
}if(!b){d="left";
b=setInterval(function(){f(document).scrollLeft(f(document).scrollLeft()-f.vakata.dnd.scroll_spd)
},150)
}}else{if(b&&d==="left"){clearInterval(b);
b=false
}}if(f(window).width()-(k.pageX-i)<20){if(b&&d==="left"){clearInterval(b);
b=false
}if(!b){d="right";
b=setInterval(function(){f(document).scrollLeft(f(document).scrollLeft()+f.vakata.dnd.scroll_spd)
},150)
}}else{if(b&&d==="right"){clearInterval(b);
b=false
}}}f.vakata.dnd.helper.css({left:(k.pageX+5)+"px",top:(k.pageY+10)+"px"});
f(document).triggerHandler("drag.vakata",{event:k,data:f.vakata.dnd.user_data})
},drag_stop:function(i){f(document).unbind("mousemove",f.vakata.dnd.drag);
f(document).unbind("mouseup",f.vakata.dnd.drag_stop);
f(document).triggerHandler("drag_stop.vakata",{event:i,data:f.vakata.dnd.user_data});
f.vakata.dnd.helper.remove();
f.vakata.dnd.init_x=0;
f.vakata.dnd.init_y=0;
f.vakata.dnd.user_data={};
f.vakata.dnd.is_down=false;
f.vakata.dnd.is_drag=false
}};
f(function(){var i="#vakata-dragged { display:block; margin:0 0 0 0; padding:4px 4px 4px 24px; position:absolute; top:-2000px; line-height:16px; z-index:10000; } ";
f.vakata.css.add_sheet({str:i})
});
f.jstree.plugin("dnd",{__init:function(){this.data.dnd={active:false,after:false,inside:false,before:false,off:false,prepared:false,w:0,to1:false,to2:false,cof:false,cw:false,ch:false,i1:false,i2:false};
this.get_container().bind("mouseenter.jstree",f.proxy(function(){if(f.vakata.dnd.is_drag&&f.vakata.dnd.user_data.jstree&&this.data.themes){a.attr("class","jstree-"+this.data.themes.theme);
f.vakata.dnd.helper.attr("class","jstree-dnd-helper jstree-"+this.data.themes.theme)
}},this)).bind("mouseleave.jstree",f.proxy(function(){if(f.vakata.dnd.is_drag&&f.vakata.dnd.user_data.jstree){if(this.data.dnd.i1){clearInterval(this.data.dnd.i1)
}if(this.data.dnd.i2){clearInterval(this.data.dnd.i2)
}}},this)).bind("mousemove.jstree",f.proxy(function(k){if(f.vakata.dnd.is_drag&&f.vakata.dnd.user_data.jstree){var j=this.get_container()[0];
if(k.pageX+24>this.data.dnd.cof.left+this.data.dnd.cw){if(this.data.dnd.i1){clearInterval(this.data.dnd.i1)
}this.data.dnd.i1=setInterval(f.proxy(function(){this.scrollLeft+=f.vakata.dnd.scroll_spd
},j),100)
}else{if(k.pageX-24<this.data.dnd.cof.left){if(this.data.dnd.i1){clearInterval(this.data.dnd.i1)
}this.data.dnd.i1=setInterval(f.proxy(function(){this.scrollLeft-=f.vakata.dnd.scroll_spd
},j),100)
}else{if(this.data.dnd.i1){clearInterval(this.data.dnd.i1)
}}}if(k.pageY+24>this.data.dnd.cof.top+this.data.dnd.ch){if(this.data.dnd.i2){clearInterval(this.data.dnd.i2)
}this.data.dnd.i2=setInterval(f.proxy(function(){this.scrollTop+=f.vakata.dnd.scroll_spd
},j),100)
}else{if(k.pageY-24<this.data.dnd.cof.top){if(this.data.dnd.i2){clearInterval(this.data.dnd.i2)
}this.data.dnd.i2=setInterval(f.proxy(function(){this.scrollTop-=f.vakata.dnd.scroll_spd
},j),100)
}else{if(this.data.dnd.i2){clearInterval(this.data.dnd.i2)
}}}}},this)).delegate("a","mousedown.jstree",f.proxy(function(j){if(j.which===1){this.start_drag(j.currentTarget,j);
return false
}},this)).delegate("a","mouseenter.jstree",f.proxy(function(j){if(f.vakata.dnd.is_drag&&f.vakata.dnd.user_data.jstree){this.dnd_enter(j.currentTarget)
}},this)).delegate("a","mousemove.jstree",f.proxy(function(j){if(f.vakata.dnd.is_drag&&f.vakata.dnd.user_data.jstree){if(typeof this.data.dnd.off.top==="undefined"){this.data.dnd.off=f(j.target).offset()
}this.data.dnd.w=(j.pageY-(this.data.dnd.off.top||0))%this.data.core.li_height;
if(this.data.dnd.w<0){this.data.dnd.w+=this.data.core.li_height
}this.dnd_show()
}},this)).delegate("a","mouseleave.jstree",f.proxy(function(j){if(f.vakata.dnd.is_drag&&f.vakata.dnd.user_data.jstree){this.data.dnd.after=false;
this.data.dnd.before=false;
this.data.dnd.inside=false;
f.vakata.dnd.helper.children("ins").attr("class","jstree-invalid");
a.hide();
if(c&&c[0]===j.target.parentNode){if(this.data.dnd.to1){clearTimeout(this.data.dnd.to1);
this.data.dnd.to1=false
}if(this.data.dnd.to2){clearTimeout(this.data.dnd.to2);
this.data.dnd.to2=false
}}}},this)).delegate("a","mouseup.jstree",f.proxy(function(j){if(f.vakata.dnd.is_drag&&f.vakata.dnd.user_data.jstree){this.dnd_finish(j)
}},this));
f(document).bind("drag_stop.vakata",f.proxy(function(){this.data.dnd.after=false;
this.data.dnd.before=false;
this.data.dnd.inside=false;
this.data.dnd.off=false;
this.data.dnd.prepared=false;
this.data.dnd.w=false;
this.data.dnd.to1=false;
this.data.dnd.to2=false;
this.data.dnd.active=false;
this.data.dnd.foreign=false;
if(a){a.css({top:"-2000px"})
}},this)).bind("drag_start.vakata",f.proxy(function(l,j){if(j.data.jstree){var k=f(j.event.target);
if(k.closest(".jstree").hasClass("jstree-"+this.get_index())){this.dnd_enter(k)
}}},this));
var i=this._get_settings().dnd;
if(i.drag_target){f(document).delegate(i.drag_target,"mousedown.jstree",f.proxy(function(k){h=k.target;
f.vakata.dnd.drag_start(k,{jstree:true,obj:k.target},"<ins class='jstree-icon'></ins>"+f(k.target).text());
if(this.data.themes){a.attr("class","jstree-"+this.data.themes.theme);
f.vakata.dnd.helper.attr("class","jstree-dnd-helper jstree-"+this.data.themes.theme)
}f.vakata.dnd.helper.children("ins").attr("class","jstree-invalid");
var j=this.get_container();
this.data.dnd.cof=j.offset();
this.data.dnd.cw=parseInt(j.width(),10);
this.data.dnd.ch=parseInt(j.height(),10);
this.data.dnd.foreign=true;
return false
},this))
}if(i.drop_target){f(document).delegate(i.drop_target,"mouseenter.jstree",f.proxy(function(j){if(this.data.dnd.active&&this._get_settings().dnd.drop_check.call(this,{o:h,r:f(j.target)})){f.vakata.dnd.helper.children("ins").attr("class","jstree-ok")
}},this)).delegate(i.drop_target,"mouseleave.jstree",f.proxy(function(j){if(this.data.dnd.active){f.vakata.dnd.helper.children("ins").attr("class","jstree-invalid")
}},this)).delegate(i.drop_target,"mouseup.jstree",f.proxy(function(j){if(this.data.dnd.active&&f.vakata.dnd.helper.children("ins").hasClass("jstree-ok")){this._get_settings().dnd.drop_finish.call(this,{o:h,r:f(j.target)})
}},this))
}},defaults:{copy_modifier:"ctrl",check_timeout:200,open_timeout:500,drop_target:".jstree-drop",drop_check:function(i){return true
},drop_finish:f.noop,drag_target:".jstree-draggable",drag_finish:f.noop,drag_check:function(i){return{after:false,before:false,inside:true}
},dnd_enabled:false},_fn:{dnd_prepare:function(){if(!c||!c.length){return
}this.data.dnd.off=c.offset();
if(this._get_settings().core.rtl){this.data.dnd.off.right=this.data.dnd.off.left+c.width()
}if(this.data.dnd.foreign){var i=this._get_settings().dnd.drag_check.call(this,{o:h,r:c});
this.data.dnd.after=i.after;
this.data.dnd.before=i.before;
this.data.dnd.inside=i.inside;
this.data.dnd.prepared=true;
return this.dnd_show()
}this.prepare_move(h,c,"before");
this.data.dnd.before=this.check_move();
this.prepare_move(h,c,"after");
this.data.dnd.after=this.check_move();
if(this._is_loaded(c)){this.prepare_move(h,c,"inside");
this.data.dnd.inside=this.check_move()
}else{this.data.dnd.inside=false
}this.data.dnd.prepared=true;
return this.dnd_show()
},dnd_show:function(){if(!this.data.dnd.prepared){return
}var k=["before","inside","after"],i=false,j=this._get_settings().core.rtl,l;
if(this.data.dnd.w<this.data.core.li_height/3){k=["before","inside","after"]
}else{if(this.data.dnd.w<=this.data.core.li_height*2/3){k=this.data.dnd.w<this.data.core.li_height/2?["inside","before","after"]:["inside","after","before"]
}else{k=["after","inside","before"]
}}f.each(k,f.proxy(function(m,n){if(this.data.dnd[n]){f.vakata.dnd.helper.children("ins").attr("class","jstree-ok");
i=n;
return false
}},this));
if(i===false){f.vakata.dnd.helper.children("ins").attr("class","jstree-invalid")
}l=j?(this.data.dnd.off.right-18):(this.data.dnd.off.left+10);
switch(i){case"before":a.css({left:l+"px",top:(this.data.dnd.off.top-6)+"px"}).show();
break;
case"after":a.css({left:l+"px",top:(this.data.dnd.off.top+this.data.core.li_height-7)+"px"}).show();
break;
case"inside":a.css({left:l+(j?-4:4)+"px",top:(this.data.dnd.off.top+this.data.core.li_height/2-5)+"px"}).show();
break;
default:a.hide();
break
}return i
},dnd_open:function(){this.data.dnd.to2=false;
this.open_node(c,f.proxy(this.dnd_prepare,this),true)
},dnd_finish:function(i){if(this.data.dnd.foreign){if(this.data.dnd.after||this.data.dnd.before||this.data.dnd.inside){this._get_settings().dnd.drag_finish.call(this,{o:h,r:c})
}}else{this.dnd_prepare();
this.move_node(h,c,this.dnd_show(),i[this._get_settings().dnd.copy_modifier+"Key"]);
this._get_settings().dnd.drag_finish.call(this,{o:h,r:c})
}h=false;
c=false;
a.hide()
},dnd_enter:function(j){var i=this._get_settings().dnd;
this.data.dnd.prepared=false;
c=this._get_node(j);
if(i.check_timeout){if(this.data.dnd.to1){clearTimeout(this.data.dnd.to1)
}this.data.dnd.to1=setTimeout(f.proxy(this.dnd_prepare,this),i.check_timeout)
}else{this.dnd_prepare()
}if(i.open_timeout){if(this.data.dnd.to2){clearTimeout(this.data.dnd.to2)
}if(c&&c.length&&c.hasClass("jstree-closed")){this.data.dnd.to2=setTimeout(f.proxy(this.dnd_open,this),i.open_timeout)
}}else{if(c&&c.length&&c.hasClass("jstree-closed")){this.dnd_open()
}}},start_drag:function(k,j){if(!this._get_settings().dnd.dnd_enabled){return
}h=this._get_node(k);
if(this.data.ui&&this.is_selected(h)){h=this._get_node(null,true)
}f.vakata.dnd.drag_start(j,{jstree:true,obj:h},"<ins class='jstree-icon'></ins>"+(h.length>1?"Multiple selection":this.get_text(h)));
if(this.data.themes){a.attr("class","jstree-"+this.data.themes.theme);
f.vakata.dnd.helper.attr("class","jstree-dnd-helper jstree-"+this.data.themes.theme)
}var i=this.get_container();
this.data.dnd.cof=i.children("ul").offset();
this.data.dnd.cw=parseInt(i.width(),10);
this.data.dnd.ch=parseInt(i.height(),10);
this.data.dnd.active=true
}}});
f(function(){var i="#vakata-dragged ins { display:block; text-decoration:none; width:16px; height:16px; margin:0 0 0 0; padding:0; position:absolute; top:4px; left:4px; } #vakata-dragged .jstree-ok { background:green; } #vakata-dragged .jstree-invalid { background:red; } #jstree-marker { padding:0; margin:0; line-height:12px; font-size:1px; overflow:hidden; height:12px; width:8px; position:absolute; top:-30px; z-index:10000; background-repeat:no-repeat; display:none; background-color:silver; } ";
f.vakata.css.add_sheet({str:i});
a=f("<div>").attr({id:"jstree-marker"}).hide().appendTo("body");
f(document).bind("drag_start.vakata",function(k,j){if(j.data.jstree){a.show()
}});
f(document).bind("drag_stop.vakata",function(k,j){if(j.data.jstree){a.hide()
}})
})
})(jQuery);
(function(a){a.jstree.plugin("checkbox",{__init:function(){this.select_node=this.deselect_node=this.deselect_all=a.noop;
this.get_selected=this.get_checked;
this.get_container().bind("open_node.jstree create_node.jstree clean_node.jstree",a.proxy(function(c,b){this._prepare_checkboxes(b.rslt.obj)
},this)).bind("loaded.jstree",a.proxy(function(b){this._prepare_checkboxes()
},this)).delegate("a","click.jstree",a.proxy(function(b){if(this._get_node(b.target).hasClass("jstree-checked")){this.uncheck_node(b.target)
}else{this.check_node(b.target)
}if(this.data.ui){this.save_selected()
}if(this.data.cookies){this.save_cookie("select_node")
}b.preventDefault()
},this))
},__destroy:function(){this.get_container().find(".jstree-checkbox").remove()
},_fn:{_prepare_checkboxes:function(d){d=!d||d==-1?this.get_container():this._get_node(d);
var f,e=this,b;
d.each(function(){b=a(this);
f=b.is("li")&&b.hasClass("jstree-checked")?"jstree-checked":"jstree-unchecked";
b.find("a").not(":has(.jstree-checkbox)").prepend("<ins class='jstree-checkbox'>&#160;</ins>").parent().not(".jstree-checked, .jstree-unchecked").addClass(f)
});
if(d.is("li")){this._repair_state(d)
}else{d.find("> ul > li").each(function(){e._repair_state(this)
})
}},change_state:function(c,b){c=this._get_node(c);
b=(b===false||b===true)?b:c.hasClass("jstree-checked");
if(b){c.find("li").andSelf().removeClass("jstree-checked jstree-undetermined").addClass("jstree-unchecked")
}else{c.find("li").andSelf().removeClass("jstree-unchecked jstree-undetermined").addClass("jstree-checked");
if(this.data.ui){this.data.ui.last_selected=c
}this.data.checkbox.last_selected=c
}c.parentsUntil(".jstree","li").each(function(){var d=a(this);
if(b){if(d.children("ul").children(".jstree-checked, .jstree-undetermined").length){d.parentsUntil(".jstree","li").andSelf().removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined");
return false
}else{d.removeClass("jstree-checked jstree-undetermined").addClass("jstree-unchecked")
}}else{if(d.children("ul").children(".jstree-unchecked, .jstree-undetermined").length){d.parentsUntil(".jstree","li").andSelf().removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined");
return false
}else{d.removeClass("jstree-unchecked jstree-undetermined").addClass("jstree-checked")
}}});
if(this.data.ui){this.data.ui.selected=this.get_checked()
}this.__callback(c)
},check_node:function(b){this.change_state(b,false)
},uncheck_node:function(b){this.change_state(b,true)
},check_all:function(){var b=this;
this.get_container().children("ul").children("li").each(function(){b.check_node(this,false)
})
},uncheck_all:function(){var b=this;
this.get_container().children("ul").children("li").each(function(){b.change_state(this,true)
})
},is_checked:function(b){b=this._get_node(b);
return b.length?b.is(".jstree-checked"):false
},get_checked:function(b){b=!b||b===-1?this.get_container():this._get_node(b);
return b.find("> ul > .jstree-checked, .jstree-undetermined > ul > .jstree-checked")
},get_unchecked:function(b){b=!b||b===-1?this.get_container():this._get_node(b);
return b.find("> ul > .jstree-unchecked, .jstree-undetermined > ul > .jstree-unchecked")
},show_checkboxes:function(){this.get_container().children("ul").removeClass("jstree-no-checkboxes")
},hide_checkboxes:function(){this.get_container().children("ul").addClass("jstree-no-checkboxes")
},_repair_state:function(f){f=this._get_node(f);
if(!f.length){return
}var e=f.find("> ul > .jstree-checked").length,d=f.find("> ul > .jstree-undetermined").length,g=f.find("> ul > li").length;
if(g===0){if(f.hasClass("jstree-undetermined")){this.check_node(f)
}}else{if(e===0&&d===0){this.uncheck_node(f)
}else{if(e===g){this.check_node(f)
}else{f.parentsUntil(".jstree","li").removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined")
}}}},reselect:function(){if(this.data.ui){var c=this,b=this.data.ui.to_select;
b=a.map(a.makeArray(b),function(d){return"#"+d.toString().replace(/^#/,"").replace("\\/","/").replace("/","\\/")
});
this.deselect_all();
a.each(b,function(d,e){c.check_node(e)
});
this.__callback()
}}}})
})(jQuery);
(function(b){b.vakata.xslt=function(e,f,j){var c="",i,d,h,g;
if(document.recalc){i=document.createElement("xml");
d=document.createElement("xml");
i.innerHTML=e;
d.innerHTML=f;
b("body").append(i).append(d);
setTimeout((function(l,k,m){return function(){m.call(null,l.transformNode(k.XMLDocument));
setTimeout((function(o,n){return function(){jQuery("body").remove(o).remove(n)
}
})(l,k),200)
}
})(i,d,j),100);
return true
}if(typeof window.DOMParser!=="undefined"&&typeof window.XMLHttpRequest!=="undefined"&&typeof window.XSLTProcessor!=="undefined"){h=new XSLTProcessor();
g=b.isFunction(h.transformDocument)?(typeof window.XMLSerializer!=="undefined"):true;
if(!g){return false
}e=new DOMParser().parseFromString(e,"text/xml");
f=new DOMParser().parseFromString(f,"text/xml");
if(b.isFunction(h.transformDocument)){c=document.implementation.createDocument("","",null);
h.transformDocument(e,f,c,null);
j.call(null,XMLSerializer().serializeToString(c));
return true
}else{h.importStylesheet(f);
c=h.transformToFragment(e,document);
j.call(null,b("<div>").append(c).html());
return true
}}return false
};
var a={nest:'<?xml version="1.0" encoding="utf-8" ?><xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" ><xsl:output method="html" encoding="utf-8" omit-xml-declaration="yes" standalone="no" indent="no" media-type="text/html" /><xsl:template match="/">	<xsl:call-template name="nodes">		<xsl:with-param name="node" select="/root" />	</xsl:call-template></xsl:template><xsl:template name="nodes">	<xsl:param name="node" />	<ul>	<xsl:for-each select="$node/item">		<xsl:variable name="children" select="count(./item) &gt; 0" />		<li>			<xsl:attribute name="class">				<xsl:if test="position() = last()">jstree-last </xsl:if>				<xsl:choose>					<xsl:when test="@state = \'open\'">jstree-open </xsl:when>					<xsl:when test="$children or @hasChildren or @state = \'closed\'">jstree-closed </xsl:when>					<xsl:otherwise>jstree-leaf </xsl:otherwise>				</xsl:choose>				<xsl:value-of select="@class" />			</xsl:attribute>			<xsl:for-each select="@*">				<xsl:if test="name() != \'class\' and name() != \'state\' and name() != \'hasChildren\'">					<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>				</xsl:if>			</xsl:for-each>	<ins class="jstree-icon"><xsl:text>&#xa0;</xsl:text></ins>			<xsl:for-each select="content/name">				<a>				<xsl:attribute name="href">					<xsl:choose>					<xsl:when test="@href"><xsl:value-of select="@href" /></xsl:when>					<xsl:otherwise>#</xsl:otherwise>					</xsl:choose>				</xsl:attribute>				<xsl:attribute name="class"><xsl:value-of select="@lang" /> <xsl:value-of select="@class" /></xsl:attribute>				<xsl:attribute name="style"><xsl:value-of select="@style" /></xsl:attribute>				<xsl:for-each select="@*">					<xsl:if test="name() != \'style\' and name() != \'class\' and name() != \'href\'">						<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>					</xsl:if>				</xsl:for-each>					<ins>						<xsl:attribute name="class">jstree-icon 							<xsl:if test="string-length(attribute::icon) > 0 and not(contains(@icon,\'/\'))"><xsl:value-of select="@icon" /></xsl:if>						</xsl:attribute>						<xsl:if test="string-length(attribute::icon) > 0 and contains(@icon,\'/\')"><xsl:attribute name="style">background:url(<xsl:value-of select="@icon" />) center center no-repeat;</xsl:attribute></xsl:if>						<xsl:text>&#xa0;</xsl:text>					</ins>					<xsl:value-of select="current()" />				</a>			</xsl:for-each>			<xsl:if test="$children or @hasChildren"><xsl:call-template name="nodes"><xsl:with-param name="node" select="current()" /></xsl:call-template></xsl:if>		</li>	</xsl:for-each>	</ul></xsl:template></xsl:stylesheet>',flat:'<?xml version="1.0" encoding="utf-8" ?><xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" ><xsl:output method="html" encoding="utf-8" omit-xml-declaration="yes" standalone="no" indent="no" media-type="text/xml" /><xsl:template match="/">	<ul>	<xsl:for-each select="//item[not(@parent_id) or @parent_id=0 or not(@parent_id = //item/@id)]">		<xsl:call-template name="nodes">			<xsl:with-param name="node" select="." />			<xsl:with-param name="is_last" select="number(position() = last())" />		</xsl:call-template>	</xsl:for-each>	</ul></xsl:template><xsl:template name="nodes">	<xsl:param name="node" />	<xsl:param name="is_last" />	<xsl:variable name="children" select="count(//item[@parent_id=$node/attribute::id]) &gt; 0" />	<li>	<xsl:attribute name="class">		<xsl:if test="$is_last = true()">jstree-last </xsl:if>		<xsl:choose>			<xsl:when test="@state = \'open\'">jstree-open </xsl:when>			<xsl:when test="$children or @hasChildren or @state = \'closed\'">jstree-closed </xsl:when>			<xsl:otherwise>jstree-leaf </xsl:otherwise>		</xsl:choose>		<xsl:value-of select="@class" />	</xsl:attribute>	<xsl:for-each select="@*">		<xsl:if test="name() != \'parent_id\' and name() != \'hasChildren\' and name() != \'class\' and name() != \'state\'">		<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>		</xsl:if>	</xsl:for-each>	<ins class="jstree-icon"><xsl:text>&#xa0;</xsl:text></ins>	<xsl:for-each select="content/name">		<a>		<xsl:attribute name="href">			<xsl:choose>			<xsl:when test="@href"><xsl:value-of select="@href" /></xsl:when>			<xsl:otherwise>#</xsl:otherwise>			</xsl:choose>		</xsl:attribute>		<xsl:attribute name="class"><xsl:value-of select="@lang" /> <xsl:value-of select="@class" /></xsl:attribute>		<xsl:attribute name="style"><xsl:value-of select="@style" /></xsl:attribute>		<xsl:for-each select="@*">			<xsl:if test="name() != \'style\' and name() != \'class\' and name() != \'href\'">				<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>			</xsl:if>		</xsl:for-each>			<ins>				<xsl:attribute name="class">jstree-icon 					<xsl:if test="string-length(attribute::icon) > 0 and not(contains(@icon,\'/\'))"><xsl:value-of select="@icon" /></xsl:if>				</xsl:attribute>				<xsl:if test="string-length(attribute::icon) > 0 and contains(@icon,\'/\')"><xsl:attribute name="style">background:url(<xsl:value-of select="@icon" />) center center no-repeat;</xsl:attribute></xsl:if>				<xsl:text>&#xa0;</xsl:text>			</ins>			<xsl:value-of select="current()" />		</a>	</xsl:for-each>	<xsl:if test="$children">		<ul>		<xsl:for-each select="//item[@parent_id=$node/attribute::id]">			<xsl:call-template name="nodes">				<xsl:with-param name="node" select="." />				<xsl:with-param name="is_last" select="number(position() = last())" />			</xsl:call-template>		</xsl:for-each>		</ul>	</xsl:if>	</li></xsl:template></xsl:stylesheet>'};
b.jstree.plugin("xml_data",{defaults:{data:false,ajax:false,xsl:"flat",clean_node:false,correct_state:true},_fn:{load_node:function(e,c,d){var f=this;
this.load_node_xml(e,function(){f.__callback({obj:e});
c.call(this)
},d)
},_is_loaded:function(d){var c=this._get_settings().xml_data;
d=this._get_node(d);
return d==-1||!d||!c.ajax||d.is(".jstree-open, .jstree-leaf")||d.children("ul").children("li").size()>0
},load_node_xml:function(g,c,e){var f=this.get_settings().xml_data,d=function(){},h=function(){};
g=this._get_node(g);
if(g&&g!==-1){if(g.data("jstree-is-loading")){return
}else{g.data("jstree-is-loading",true)
}}switch(!0){case (!f.data&&!f.ajax):throw"Neither data nor ajax settings supplied.";
case (!!f.data&&!f.ajax)||(!!f.data&&!!f.ajax&&(!g||g===-1)):if(!g||g==-1){this.parse_xml(f.data,b.proxy(function(i){if(i){i=i.replace(/ ?xmlns="[^"]*"/ig,"");
if(i.length>10){i=b(i);
this.get_container().children("ul").empty().append(i.children());
if(f.clean_node){this.clean_node(g)
}if(c){c.call(this)
}}}else{if(f.correct_state){this.get_container().children("ul").empty();
if(c){c.call(this)
}}}},this))
}break;
case (!f.data&&!!f.ajax)||(!!f.data&&!!f.ajax&&g&&g!==-1):d=function(j,k,l){var i=this.get_settings().xml_data.ajax.error;
if(i){i.call(this,j,k,l)
}if(g!==-1&&g.length){g.children(".jstree-loading").removeClass("jstree-loading");
g.data("jstree-is-loading",false);
if(k==="success"&&f.correct_state){g.removeClass("jstree-open jstree-closed").addClass("jstree-leaf")
}}else{if(k==="success"&&f.correct_state){this.get_container().children("ul").empty()
}}if(e){e.call(this)
}};
h=function(l,j,i){l=i.responseText;
var k=this.get_settings().xml_data.ajax.success;
if(k){l=k.call(this,l,j,i)||l
}if(l==""){return d.call(this,i,j,"")
}this.parse_xml(l,b.proxy(function(m){if(m){m=m.replace(/ ?xmlns="[^"]*"/ig,"");
if(m.length>10){m=b(m);
if(g===-1||!g){this.get_container().children("ul").empty().append(m.children())
}else{g.children(".jstree-loading").removeClass("jstree-loading");
g.append(m);
g.data("jstree-is-loading",false)
}if(f.clean_node){this.clean_node(g)
}if(c){c.call(this)
}}else{if(g&&g!==-1){g.children(".jstree-loading").removeClass("jstree-loading");
g.data("jstree-is-loading",false);
if(f.correct_state){g.removeClass("jstree-open jstree-closed").addClass("jstree-leaf");
if(c){c.call(this)
}}}else{if(f.correct_state){this.get_container().children("ul").empty();
if(c){c.call(this)
}}}}}},this))
};
f.ajax.context=this;
f.ajax.error=d;
f.ajax.success=h;
if(!f.ajax.dataType){f.ajax.dataType="xml"
}if(b.isFunction(f.ajax.url)){f.ajax.url=f.ajax.url.call(this,g)
}if(b.isFunction(f.ajax.data)){f.ajax.data=f.ajax.data.call(this,g)
}b.ajax(f.ajax);
break
}},parse_xml:function(c,e){var d=this._get_settings().xml_data;
b.vakata.xslt(c,a[d.xsl],e)
},get_xml:function(k,h,e,c,m){var o="",n=this._get_settings(),i=this,g,f,l,j,d;
if(!k){k="flat"
}if(!m){m=0
}h=this._get_node(h);
if(!h||h===-1){h=this.get_container().find("> ul > li")
}e=b.isArray(e)?e:["id","class"];
if(!m&&this.data.types&&b.inArray(n.types.type_attr,e)===-1){e.push(n.types.type_attr)
}c=b.isArray(c)?c:[];
if(!m){o+="<root>"
}h.each(function(){o+="<item";
l=b(this);
b.each(e,function(q,p){o+=" "+p+'="'+(l.attr(p)||"").replace(/jstree[^ ]*|$/ig,"").replace(/^\s+$/ig,"")+'"'
});
if(l.hasClass("jstree-open")){o+=' state="open"'
}if(l.hasClass("jstree-closed")){o+=' state="closed"'
}if(k==="flat"){o+=' parent_id="'+m+'"'
}o+=">";
o+="<content>";
j=l.children("a");
j.each(function(){g=b(this);
d=false;
o+="<name";
if(b.inArray("languages",n.plugins)!==-1){b.each(n.languages,function(p,q){if(g.hasClass(q)){o+=' lang="'+q+'"';
d=q;
return false
}})
}if(c.length){b.each(c,function(p,q){o+=" "+q+'="'+(g.attr(q)||"").replace(/jstree[^ ]*|$/ig,"")+'"'
})
}if(g.children("ins").get(0).className.replace(/jstree[^ ]*|$/ig,"").replace(/^\s+$/ig,"").length){o+=' icon="'+g.children("ins").get(0).className.replace(/jstree[^ ]*|$/ig,"").replace(/^\s+$/ig,"")+'"'
}if(g.children("ins").get(0).style.backgroundImage.length){o+=' icon="'+g.children("ins").get(0).style.backgroundImage.replace("url(","").replace(")","")+'"'
}o+=">";
o+="<![CDATA["+i.get_text(g,d)+"]]>";
o+="</name>"
});
o+="</content>";
f=l[0].id;
l=l.find("> ul > li");
if(l.length){f=i.get_xml(k,l,e,c,f)
}else{f=""
}if(k=="nest"){o+=f
}o+="</item>";
if(k=="flat"){o+=f
}});
if(!m){o+="</root>"
}return o
}}})
})(jQuery);
(function(a){a.expr[":"].jstree_contains=function(c,d,b){return(c.textContent||c.innerText||"").toLowerCase().indexOf(b[3].toLowerCase())>=0
};
a.jstree.plugin("search",{__init:function(){this.data.search.str="";
this.data.search.result=a()
},defaults:{ajax:false,case_insensitive:false},_fn:{search:function(g,b){if(g===""){return
}var e=this.get_settings().search,d=this,c=function(){},f=function(){};
this.data.search.str=g;
if(!b&&e.ajax!==false&&this.get_container().find(".jstree-closed:eq(0)").length>0){this.search.supress_callback=true;
c=function(){};
f=function(k,i,h){var j=this.get_settings().search.ajax.success;
if(j){k=j.call(this,k,i,h)||k
}this.data.search.to_open=k;
this._search_open()
};
e.ajax.context=this;
e.ajax.error=c;
e.ajax.success=f;
if(a.isFunction(e.ajax.url)){e.ajax.url=e.ajax.url.call(this,g)
}if(a.isFunction(e.ajax.data)){e.ajax.data=e.ajax.data.call(this,g)
}if(!e.ajax.data){e.ajax.data={search_string:g}
}if(!e.ajax.dataType||/^json/.exec(e.ajax.dataType)){e.ajax.dataType="json"
}a.ajax(e.ajax);
return
}if(this.data.search.result.length){this.clear_search()
}this.data.search.result=this.get_container().find("a"+(this.data.languages?"."+this.get_lang():"")+":"+(e.case_insensitive?"jstree_contains":"contains")+"("+this.data.search.str+")");
this.data.search.result.addClass("jstree-search").parents(".jstree-closed").each(function(){d.open_node(this,false,true)
});
this.__callback({nodes:this.data.search.result,str:g})
},clear_search:function(b){this.data.search.result.removeClass("jstree-search");
this.__callback(this.data.search.result);
this.data.search.result=a()
},_search_open:function(c){var f=this,b=true,e=[],d=[];
if(this.data.search.to_open.length){a.each(this.data.search.to_open,function(g,h){if(h=="#"){return true
}if(a(h).length&&a(h).is(".jstree-closed")){e.push(h)
}else{d.push(h)
}});
if(e.length){this.data.search.to_open=d;
a.each(e,function(g,h){f.open_node(h,function(){f._search_open(true)
})
});
b=false
}}if(b){this.search(this.data.search.str,true)
}}}})
})(jQuery);
(function(a){a.vakata.context={cnt:a("<div id='vakata-contextmenu'>"),vis:false,tgt:false,par:false,func:false,data:false,show:function(l,k,i,g,f,b){var e=a.vakata.context.parse(l),c,j;
if(!e){return
}a.vakata.context.vis=true;
a.vakata.context.tgt=k;
a.vakata.context.par=b||k||null;
a.vakata.context.data=f||null;
a.vakata.context.cnt.html(e).css({visibility:"hidden",display:"block",left:0,top:0});
c=a.vakata.context.cnt.height();
j=a.vakata.context.cnt.width();
if(i+j>a(document).width()){i=a(document).width()-(j+5);
a.vakata.context.cnt.find("li > ul").addClass("right")
}if(g+c>a(document).height()){g=g-(c+k[0].offsetHeight);
a.vakata.context.cnt.find("li > ul").addClass("bottom")
}a.vakata.context.cnt.css({left:i,top:g}).find("li:has(ul)").bind("mouseenter",function(o){var d=a(document).width(),n=a(document).height(),m=a(this).children("ul").show();
if(d!==a(document).width()){m.toggleClass("right")
}if(n!==a(document).height()){m.toggleClass("bottom")
}}).bind("mouseleave",function(d){a(this).children("ul").hide()
}).end().css({visibility:"visible"}).show();
a(document).triggerHandler("context_show.vakata")
},hide:function(){a.vakata.context.vis=false;
a.vakata.context.cnt.attr("class","").hide();
a(document).triggerHandler("context_hide.vakata")
},parse:function(e,d){if(!e){return false
}var f="",c=false,b=true;
if(!d){a.vakata.context.func={}
}f+="<ul>";
a.each(e,function(g,h){if(!h){return true
}a.vakata.context.func[g]=h.action;
if(!b&&h.separator_before){f+="<li class='vakata-separator vakata-separator-before'></li>"
}b=false;
f+="<li class='"+(h._class||"")+(h._disabled?" jstree-contextmenu-disabled ":"")+"'><ins ";
if(h.icon&&h.icon.indexOf("/")===-1){f+=" class='"+h.icon+"' "
}if(h.icon&&h.icon.indexOf("/")!==-1){f+=" style='background:url("+h.icon+") center center no-repeat;' "
}f+=">&#160;</ins><a href='#' rel='"+g+"'>";
if(h.submenu){f+="<span style='float:right;'>&raquo;</span>"
}f+=h.label+"</a>";
if(h.submenu){c=a.vakata.context.parse(h.submenu,true);
if(c){f+=c
}}f+="</li>";
if(h.separator_after){f+="<li class='vakata-separator vakata-separator-after'></li>";
b=true
}});
f=f.replace(/<li class\='vakata-separator vakata-separator-after'\><\/li\>$/,"");
f+="</ul>";
return f.length>10?f:false
},exec:function(b){if(a.isFunction(a.vakata.context.func[b])){a.vakata.context.func[b].call(a.vakata.context.data,a.vakata.context.par);
return true
}else{return false
}}};
a(function(){var b="#vakata-contextmenu { display:none; position:absolute; margin:0; padding:0; min-width:180px; background:#ebebeb; border:1px solid silver; z-index:10000; *width:180px; } #vakata-contextmenu ul { min-width:180px; *width:180px; } #vakata-contextmenu ul, #vakata-contextmenu li { margin:0; padding:0; list-style-type:none; display:block; } #vakata-contextmenu li { line-height:20px; min-height:20px; position:relative; padding:0px; } #vakata-contextmenu li a { padding:1px 6px; line-height:17px; display:block; text-decoration:none; margin:1px 1px 0 1px; } #vakata-contextmenu li ins { float:left; width:16px; height:16px; text-decoration:none; margin-right:2px; } #vakata-contextmenu li a:hover, #vakata-contextmenu li.vakata-hover > a { background:gray; color:white; } #vakata-contextmenu li ul { display:none; position:absolute; top:-2px; left:100%; background:#ebebeb; border:1px solid gray; } #vakata-contextmenu .right { right:100%; left:auto; } #vakata-contextmenu .bottom { bottom:-1px; top:auto; } #vakata-contextmenu li.vakata-separator { min-height:0; height:1px; line-height:1px; font-size:1px; overflow:hidden; margin:0 2px; background:silver; /* border-top:1px solid #fefefe; */ padding:0; } ";
a.vakata.css.add_sheet({str:b});
a.vakata.context.cnt.delegate("a","click",function(c){c.preventDefault()
}).delegate("a","mouseup",function(c){if(!a(this).parent().hasClass("jstree-contextmenu-disabled")&&a.vakata.context.exec(a(this).attr("rel"))){a.vakata.context.hide()
}else{a(this).blur()
}}).delegate("a","mouseover",function(){a.vakata.context.cnt.find(".vakata-hover").removeClass("vakata-hover")
}).appendTo("body");
a(document).bind("mousedown",function(c){if(a.vakata.context.vis&&!a.contains(a.vakata.context.cnt[0],c.target)){a.vakata.context.hide()
}});
if(typeof a.hotkeys!=="undefined"){a(document).bind("keydown","up",function(c){if(a.vakata.context.vis){var d=a.vakata.context.cnt.find("ul:visible").last().children(".vakata-hover").removeClass("vakata-hover").prevAll("li:not(.vakata-separator)").first();
if(!d.length){d=a.vakata.context.cnt.find("ul:visible").last().children("li:not(.vakata-separator)").last()
}d.addClass("vakata-hover");
c.stopImmediatePropagation();
c.preventDefault()
}}).bind("keydown","down",function(c){if(a.vakata.context.vis){var d=a.vakata.context.cnt.find("ul:visible").last().children(".vakata-hover").removeClass("vakata-hover").nextAll("li:not(.vakata-separator)").first();
if(!d.length){d=a.vakata.context.cnt.find("ul:visible").last().children("li:not(.vakata-separator)").first()
}d.addClass("vakata-hover");
c.stopImmediatePropagation();
c.preventDefault()
}}).bind("keydown","right",function(c){if(a.vakata.context.vis){a.vakata.context.cnt.find(".vakata-hover").children("ul").show().children("li:not(.vakata-separator)").removeClass("vakata-hover").first().addClass("vakata-hover");
c.stopImmediatePropagation();
c.preventDefault()
}}).bind("keydown","left",function(c){if(a.vakata.context.vis){a.vakata.context.cnt.find(".vakata-hover").children("ul").hide().children(".vakata-separator").removeClass("vakata-hover");
c.stopImmediatePropagation();
c.preventDefault()
}}).bind("keydown","esc",function(c){a.vakata.context.hide();
c.preventDefault()
}).bind("keydown","space",function(c){a.vakata.context.cnt.find(".vakata-hover").last().children("a").click();
c.preventDefault()
})
}});
a.jstree.plugin("contextmenu",{__init:function(){this.get_container().delegate("a","contextmenu.jstree",a.proxy(function(b){b.preventDefault();
this.show_contextmenu(b.currentTarget,b.pageX,b.pageY)
},this)).bind("destroy.jstree",a.proxy(function(){if(this.data.contextmenu){a.vakata.context.hide()
}},this));
a(document).bind("context_hide.vakata",a.proxy(function(){this.data.contextmenu=false
},this))
},defaults:{select_node:false,show_at_node:true,items:{create:{separator_before:false,separator_after:true,label:"Create",action:function(b){this.create(b)
}},rename:{separator_before:false,separator_after:false,label:"Rename",action:function(b){this.rename(b)
}},remove:{separator_before:false,icon:false,separator_after:false,label:"Delete",action:function(b){this.remove(b)
}},ccp:{separator_before:true,icon:false,separator_after:false,label:"Edit",action:false,submenu:{cut:{separator_before:false,separator_after:false,label:"Cut",action:function(b){this.cut(b)
}},copy:{separator_before:false,icon:false,separator_after:false,label:"Copy",action:function(b){this.copy(b)
}},paste:{separator_before:false,icon:false,separator_after:false,label:"Paste",action:function(b){this.paste(b)
}}}}}},_fn:{show_contextmenu:function(e,b,g){e=this._get_node(e);
var d=this.get_settings().contextmenu,c=e.children("a:visible:eq(0)"),f=false;
if(d.select_node&&this.data.ui&&!this.is_selected(e)){this.deselect_all();
this.select_node(e,true)
}if(d.show_at_node||typeof b==="undefined"||typeof g==="undefined"){f=c.offset();
b=f.left;
g=f.top+this.data.core.li_height
}if(a.isFunction(d.items)){d.items=d.items.call(this,e)
}this.data.contextmenu=true;
a.vakata.context.show(d.items,c,b,g,this,e);
if(this.data.themes){a.vakata.context.cnt.attr("class","jstree-"+this.data.themes.theme+"-context")
}}}})
})(jQuery);
(function(a){a.jstree.plugin("types",{__init:function(){var b=this._get_settings().types;
this.data.types.attach_to=[];
this.get_container().bind("init.jstree",a.proxy(function(){var e=b.types,d=b.type_attr,c="",f=this;
a.each(e,function(g,h){a.each(h,function(j,i){if(!/^(max_depth|max_children|icon|valid_children)$/.test(j)){f.data.types.attach_to.push(j)
}});
if(!h.icon){return true
}if(h.icon.image||h.icon.position){if(g=="default"){c+=".jstree-"+f.get_index()+" a > .jstree-icon { "
}else{c+=".jstree-"+f.get_index()+" li["+d+"="+g+"] > a > .jstree-icon { "
}if(h.icon.image){c+=" background-image:url("+h.icon.image+"); "
}if(h.icon.position){c+=" background-position:"+h.icon.position+"; "
}else{c+=" background-position:0 0; "
}c+="} "
}});
if(c!=""){a.vakata.css.add_sheet({str:c})
}},this)).bind("before.jstree",a.proxy(function(g,f){if(a.inArray(f.func,this.data.types.attach_to)!==-1){var d=this._get_settings().types.types,c=this._get_type(f.args[0]);
if(((d[c]&&typeof d[c][f.func]!=="undefined")||(d["default"]&&typeof d["default"][f.func]!=="undefined"))&&!this._check(f.func,f.args[0])){g.stopImmediatePropagation();
return false
}}},this))
},defaults:{max_children:-1,max_depth:-1,valid_children:"all",type_attr:"rel",types:{"default":{max_children:-1,max_depth:-1,valid_children:"all"}}},_fn:{_get_type:function(b){b=this._get_node(b);
return(!b||!b.length)?false:b.attr(this._get_settings().types.type_attr)||"default"
},set_type:function(c,b){b=this._get_node(b);
return(!b.length||!c)?false:b.attr(this._get_settings().types.type_attr,c)
},_check:function(h,g,f){var b=false,c=this._get_type(g),i=0,j=this,e=this._get_settings().types;
if(g===-1){if(!!e[h]){b=e[h]
}else{return
}}else{if(c===false){return
}if(!!e.types[c]&&!!e.types[c][h]){b=e.types[c][h]
}else{if(!!e.types["default"]&&!!e.types["default"][h]){b=e.types["default"][h]
}}}if(a.isFunction(b)){b=b.call(this,g)
}if(h==="max_depth"&&g!==-1&&f!==false&&e.max_depth!==-2&&b!==0){this._get_node(g).children("a:eq(0)").parentsUntil(".jstree","li").each(function(d){if(e.max_depth!==-1&&e.max_depth-(d+1)<=0){b=0;
return false
}i=(d===0)?b:j._check(h,this,false);
if(i!==-1&&i-(d+1)<=0){b=0;
return false
}if(i>=0&&(i-(d+1)<b||b<0)){b=i-(d+1)
}if(e.max_depth>=0&&(e.max_depth-(d+1)<b||b<0)){b=e.max_depth-(d+1)
}})
}return b
},check_move:function(){if(!this.__call_old()){return false
}var b=this._get_move(),g=b.rt._get_settings().types,i=b.rt._check("max_children",b.cr),h=b.rt._check("max_depth",b.cr),e=b.rt._check("valid_children",b.cr),f=0,j=1,c;
if(e==="none"){return false
}if(a.isArray(e)&&b.ot&&b.ot._get_type){b.o.each(function(){if(a.inArray(b.ot._get_type(this),e)===-1){j=false;
return false
}});
if(j===false){return false
}}if(g.max_children!==-2&&i!==-1){f=b.cr===-1?this.get_container().children("> ul > li").not(b.o).length:b.cr.children("> ul > li").not(b.o).length;
if(f+b.o.length>i){return false
}}if(g.max_depth!==-2&&h!==-1){j=0;
if(h===0){return false
}if(typeof b.o.d==="undefined"){c=b.o;
while(c.length>0){c=c.find("> ul > li");
j++
}b.o.d=j
}if(h-b.o.d<0){return false
}}return true
},create_node:function(e,f,c,l,h,k){if(!k&&(h||this._is_loaded(e))){var d=(f&&f.match(/^before|after$/i)&&e!==-1)?this._get_parent(e):this._get_node(e),m=this._get_settings().types,j=this._check("max_children",d),i=this._check("max_depth",d),g=this._check("valid_children",d),b;
if(!c){c={}
}if(g==="none"){return false
}if(a.isArray(g)){if(!c.attr||!c.attr[m.type_attr]){if(!c.attr){c.attr={}
}c.attr[m.type_attr]=g[0]
}else{if(a.inArray(c.attr[m.type_attr],g)===-1){return false
}}}if(m.max_children!==-2&&j!==-1){b=d===-1?this.get_container().children("> ul > li").length:d.children("> ul > li").length;
if(b+1>j){return false
}}if(m.max_depth!==-2&&i!==-1&&(i-1)<0){return false
}}return this.__call_old(true,e,f,c,l,h,k)
}}})
})(jQuery);
(function(a){a.jstree.plugin("html_data",{__init:function(){this.data.html_data.original_container_html=this.get_container().find(" > ul > li").clone(true);
this.data.html_data.original_container_html.find("li").andSelf().contents().filter(function(){return this.nodeType==3
}).remove()
},defaults:{data:false,ajax:false,correct_state:true},_fn:{load_node:function(d,b,c){var e=this;
this.load_node_html(d,function(){e.__callback({obj:d});
b.call(this)
},c)
},_is_loaded:function(b){b=this._get_node(b);
return b==-1||!b||!this._get_settings().html_data.ajax||b.is(".jstree-open, .jstree-leaf")||b.children("ul").children("li").size()>0
},load_node_html:function(g,b,e){var i,f=this.get_settings().html_data,c=function(){},h=function(){};
g=this._get_node(g);
if(g&&g!==-1){if(g.data("jstree-is-loading")){return
}else{g.data("jstree-is-loading",true)
}}switch(!0){case (!f.data&&!f.ajax):if(!g||g==-1){this.get_container().children("ul").empty().append(this.data.html_data.original_container_html).find("li, a").filter(function(){return this.firstChild.tagName!=="INS"
}).prepend("<ins class='jstree-icon'>&#160;</ins>").end().filter("a").children("ins:first-child").not(".jstree-icon").addClass("jstree-icon");
this.clean_node()
}if(b){b.call(this)
}break;
case (!!f.data&&!f.ajax)||(!!f.data&&!!f.ajax&&(!g||g===-1)):if(!g||g==-1){i=a(f.data);
if(!i.is("ul")){i=a("<ul>").append(i)
}this.get_container().children("ul").empty().append(i.children()).find("li, a").filter(function(){return this.firstChild.tagName!=="INS"
}).prepend("<ins class='jstree-icon'>&#160;</ins>").end().filter("a").children("ins:first-child").not(".jstree-icon").addClass("jstree-icon");
this.clean_node()
}if(b){b.call(this)
}break;
case (!f.data&&!!f.ajax)||(!!f.data&&!!f.ajax&&g&&g!==-1):g=this._get_node(g);
c=function(j,k,l){var d=this.get_settings().html_data.ajax.error;
if(d){d.call(this,j,k,l)
}if(g!=-1&&g.length){g.children(".jstree-loading").removeClass("jstree-loading");
g.data("jstree-is-loading",false);
if(k==="success"&&f.correct_state){g.removeClass("jstree-open jstree-closed").addClass("jstree-leaf")
}}else{if(k==="success"&&f.correct_state){this.get_container().children("ul").empty()
}}if(e){e.call(this)
}};
h=function(m,k,j){var l=this.get_settings().html_data.ajax.success;
if(l){m=l.call(this,m,k,j)||m
}if(m==""){return c.call(this,j,k,"")
}if(m){m=a(m);
if(!m.is("ul")){m=a("<ul>").append(m)
}if(g==-1||!g){this.get_container().children("ul").empty().append(m.children()).find("li, a").filter(function(){return this.firstChild.tagName!=="INS"
}).prepend("<ins class='jstree-icon'>&#160;</ins>").end().filter("a").children("ins:first-child").not(".jstree-icon").addClass("jstree-icon")
}else{g.children(".jstree-loading").removeClass("jstree-loading");
g.append(m).find("li, a").filter(function(){return this.firstChild.tagName!=="INS"
}).prepend("<ins class='jstree-icon'>&#160;</ins>").end().filter("a").children("ins:first-child").not(".jstree-icon").addClass("jstree-icon");
g.data("jstree-is-loading",false)
}this.clean_node(g);
if(b){b.call(this)
}}else{if(g&&g!==-1){g.children(".jstree-loading").removeClass("jstree-loading");
g.data("jstree-is-loading",false);
if(f.correct_state){g.removeClass("jstree-open jstree-closed").addClass("jstree-leaf");
if(b){b.call(this)
}}}else{if(f.correct_state){this.get_container().children("ul").empty();
if(b){b.call(this)
}}}}};
f.ajax.context=this;
f.ajax.error=c;
f.ajax.success=h;
if(!f.ajax.dataType){f.ajax.dataType="html"
}if(a.isFunction(f.ajax.url)){f.ajax.url=f.ajax.url.call(this,g)
}if(a.isFunction(f.ajax.data)){f.ajax.data=f.ajax.data.call(this,g)
}a.ajax(f.ajax);
break
}}}});
a.jstree.defaults.plugins.push("html_data")
})(jQuery);
(function(a){a.jstree.plugin("themeroller",{__init:function(){var b=this._get_settings().themeroller;
this.get_container().addClass("ui-widget-content").delegate("a","mouseenter.jstree",function(){a(this).addClass(b.item_h)
}).delegate("a","mouseleave.jstree",function(){a(this).removeClass(b.item_h)
}).bind("open_node.jstree create_node.jstree",a.proxy(function(d,c){this._themeroller(c.rslt.obj)
},this)).bind("loaded.jstree refresh.jstree",a.proxy(function(c){this._themeroller()
},this)).bind("close_node.jstree",a.proxy(function(d,c){c.rslt.obj.children("ins").removeClass(b.opened).addClass(b.closed)
},this)).bind("select_node.jstree",a.proxy(function(d,c){c.rslt.obj.children("a").addClass(b.item_a)
},this)).bind("deselect_node.jstree deselect_all.jstree",a.proxy(function(d,c){this.get_container().find("."+b.item_a).removeClass(b.item_a).end().find(".jstree-clicked").addClass(b.item_a)
},this)).bind("move_node.jstree",a.proxy(function(d,c){this._themeroller(c.rslt.o)
},this))
},__destroy:function(){var b=this._get_settings().themeroller,d=["ui-icon"];
a.each(b,function(e,c){c=c.split(" ");
if(c.length){d=d.concat(c)
}});
this.get_container().removeClass("ui-widget-content").find("."+d.join(", .")).removeClass(d.join(" "))
},_fn:{_themeroller:function(c){var b=this._get_settings().themeroller;
c=!c||c==-1?this.get_container():this._get_node(c).parent();
c.find("li.jstree-closed > ins.jstree-icon").removeClass(b.opened).addClass("ui-icon "+b.closed).end().find("li.jstree-open > ins.jstree-icon").removeClass(b.closed).addClass("ui-icon "+b.opened).end().find("a").addClass(b.item).children("ins.jstree-icon").addClass("ui-icon "+b.item_icon)
}},defaults:{opened:"ui-icon-triangle-1-se",closed:"ui-icon-triangle-1-e",item:"ui-state-default",item_h:"ui-state-hover",item_a:"ui-state-active",item_icon:"ui-icon-folder-collapsed"}});
a(function(){var b=".jstree .ui-icon { overflow:visible; } .jstree a { padding:0 2px; }";
a.vakata.css.add_sheet({str:b})
})
})(jQuery);
(function(a){a.jstree.plugin("unique",{__init:function(){this.get_container().bind("before.jstree",a.proxy(function(h,f){var b=[],d=true,g,c;
if(f.func=="move_node"){if(f.args[4]===true){if(f.args[0].o&&f.args[0].o.length){f.args[0].o.children("a").each(function(){b.push(a(this).text().replace(/^\s+/g,""))
});
d=this._check_unique(b,f.args[0].np.find("> ul > li").not(f.args[0].o))
}}}if(f.func=="create_node"){if(f.args[4]||this._is_loaded(f.args[0])){g=this._get_node(f.args[0]);
if(f.args[1]&&(f.args[1]==="before"||f.args[1]==="after")){g=this._get_parent(f.args[0]);
if(!g||g===-1){g=this.get_container()
}}if(typeof f.args[2]==="string"){b.push(f.args[2])
}else{if(!f.args[2]||!f.args[2].data){b.push(this._get_settings().core.strings.new_node)
}else{b.push(f.args[2].data)
}}d=this._check_unique(b,g.find("> ul > li"))
}}if(f.func=="rename_node"){b.push(f.args[1]);
c=this._get_node(f.args[0]);
g=this._get_parent(c);
if(!g||g===-1){g=this.get_container()
}d=this._check_unique(b,g.find("> ul > li").not(c))
}if(!d){h.stopPropagation();
return false
}},this))
},_fn:{_check_unique:function(c,d){var b=[];
d.children("a").each(function(){b.push(a(this).text().replace(/^\s+/g,""))
});
if(!b.length||!c.length){return true
}b=b.sort().join(",,").replace(/(,|^)([^,]+)(,,\2)+(,|$)/g,"$1$2$4").replace(/,,+/g,",").replace(/,$/,"").split(",");
if((b.length+c.length)!=b.concat(c).sort().join(",,").replace(/(,|^)([^,]+)(,,\2)+(,|$)/g,"$1$2$4").replace(/,,+/g,",").replace(/,$/,"").split(",").length){return false
}return true
},check_move:function(){if(!this.__call_old()){return false
}var c=this._get_move(),b=[];
if(c.o&&c.o.length){c.o.children("a").each(function(){b.push(a(this).text().replace(/^\s+/g,""))
});
return this._check_unique(b,c.np.find("> ul > li").not(c.o))
}return true
}}})
})(jQuery);(function(d){var i,a;
var e;
var b;
d.extend({pnotify_remove_all:function(){var k=e.data("pnotify");
if(k&&k.length){d.each(k,function(){if(this.pnotify_remove){this.pnotify_remove()
}})
}},pnotify_position_all:function(){if(a){clearTimeout(a)
}a=null;
var k=e.data("pnotify");
if(!k||!k.length){return
}d.each(k,function(){var o=this.opts.pnotify_stack;
if(!o){return
}if(!o.nextpos1){o.nextpos1=o.firstpos1
}if(!o.nextpos2){o.nextpos2=o.firstpos2
}if(!o.addpos2){o.addpos2=0
}if(this.css("display")!="none"){var q,p;
var l={};
var n;
switch(o.dir1){case"down":n="top";
break;
case"up":n="bottom";
break;
case"left":n="right";
break;
case"right":n="left";
break
}q=parseInt(this.css(n));
if(isNaN(q)){q=0
}if(typeof o.firstpos1=="undefined"){o.firstpos1=q;
o.nextpos1=o.firstpos1
}var m;
switch(o.dir2){case"down":m="top";
break;
case"up":m="bottom";
break;
case"left":m="right";
break;
case"right":m="left";
break
}p=parseInt(this.css(m));
if(isNaN(p)){p=0
}if(typeof o.firstpos2=="undefined"){o.firstpos2=p;
o.nextpos2=o.firstpos2
}if((o.dir1=="down"&&o.nextpos1+this.height()>b.height())||(o.dir1=="up"&&o.nextpos1+this.height()>b.height())||(o.dir1=="left"&&o.nextpos1+this.width()>b.width())||(o.dir1=="right"&&o.nextpos1+this.width()>b.width())){o.nextpos1=o.firstpos1;
o.nextpos2+=o.addpos2+10;
o.addpos2=0
}if(o.animation&&o.nextpos2<p){switch(o.dir2){case"down":l.top=o.nextpos2+"px";
break;
case"up":l.bottom=o.nextpos2+"px";
break;
case"left":l.right=o.nextpos2+"px";
break;
case"right":l.left=o.nextpos2+"px";
break
}}else{this.css(m,o.nextpos2+"px")
}switch(o.dir2){case"down":case"up":if(this.outerHeight(true)>o.addpos2){o.addpos2=this.height()
}break;
case"left":case"right":if(this.outerWidth(true)>o.addpos2){o.addpos2=this.width()
}break
}if(o.nextpos1){if(o.animation&&(q>o.nextpos1||l.top||l.bottom||l.right||l.left)){switch(o.dir1){case"down":l.top=o.nextpos1+"px";
break;
case"up":l.bottom=o.nextpos1+"px";
break;
case"left":l.right=o.nextpos1+"px";
break;
case"right":l.left=o.nextpos1+"px";
break
}}else{this.css(n,o.nextpos1+"px")
}}if(l.top||l.bottom||l.right||l.left){this.animate(l,{duration:500,queue:false})
}switch(o.dir1){case"down":case"up":o.nextpos1+=this.height()+10;
break;
case"left":case"right":o.nextpos1+=this.width()+10;
break
}}});
d.each(k,function(){var l=this.opts.pnotify_stack;
if(!l){return
}l.nextpos1=l.firstpos1;
l.nextpos2=l.firstpos2;
l.addpos2=0;
l.animation=true
})
},pnotify:function(r){if(!e){e=d("body")
}if(!b){b=d(window)
}var s;
var k;
if(typeof r!="object"){k=d.extend({},d.pnotify.defaults);
k.pnotify_text=r
}else{k=d.extend({},d.pnotify.defaults,r)
}if(k.pnotify_before_init){if(k.pnotify_before_init(k)===false){return null
}}var l;
var m=function(x,u){o.css("display","none");
var t=document.elementFromPoint(x.clientX,x.clientY);
o.css("display","block");
var w=d(t);
var v=w.css("cursor");
o.css("cursor",v!="auto"?v:"default");
if(!l||l.get(0)!=t){if(l){f.call(l.get(0),"mouseleave",x.originalEvent);
f.call(l.get(0),"mouseout",x.originalEvent)
}f.call(t,"mouseenter",x.originalEvent);
f.call(t,"mouseover",x.originalEvent)
}f.call(t,u,x.originalEvent);
l=w
};
var o=d("<div />",{"class":"ui-pnotify "+k.pnotify_addclass,css:{display:"none"},mouseenter:function(t){if(k.pnotify_nonblock){t.stopPropagation()
}if(k.pnotify_mouse_reset&&s=="out"){o.stop(true);
s="in";
o.css("height","auto").animate({width:k.pnotify_width,opacity:k.pnotify_nonblock?k.pnotify_nonblock_opacity:k.pnotify_opacity},"fast")
}if(k.pnotify_nonblock){o.animate({opacity:k.pnotify_nonblock_opacity},"fast")
}if(k.pnotify_hide&&k.pnotify_mouse_reset){o.pnotify_cancel_remove()
}if(k.pnotify_closer&&!k.pnotify_nonblock){o.closer.show()
}},mouseleave:function(t){if(k.pnotify_nonblock){t.stopPropagation()
}l=null;
o.css("cursor","auto");
if(k.pnotify_nonblock&&s!="out"){o.animate({opacity:k.pnotify_opacity},"fast")
}if(k.pnotify_hide&&k.pnotify_mouse_reset){o.pnotify_queue_remove()
}o.closer.hide();
d.pnotify_position_all()
},mouseover:function(t){if(k.pnotify_nonblock){t.stopPropagation()
}},mouseout:function(t){if(k.pnotify_nonblock){t.stopPropagation()
}},mousemove:function(t){if(k.pnotify_nonblock){t.stopPropagation();
m(t,"onmousemove")
}},mousedown:function(t){if(k.pnotify_nonblock){t.stopPropagation();
t.preventDefault();
m(t,"onmousedown")
}},mouseup:function(t){if(k.pnotify_nonblock){t.stopPropagation();
t.preventDefault();
m(t,"onmouseup")
}},click:function(t){if(k.pnotify_nonblock){t.stopPropagation();
m(t,"onclick")
}},dblclick:function(t){if(k.pnotify_nonblock){t.stopPropagation();
m(t,"ondblclick")
}}});
o.opts=k;
if(k.pnotify_shadow&&!d.browser.msie){o.shadow_container=d("<div />",{"class":"ui-widget-shadow ui-corner-all ui-pnotify-shadow"}).prependTo(o)
}o.container=d("<div />",{"class":"ui-widget ui-widget-content ui-corner-all ui-pnotify-container "+(k.pnotify_type=="error"?"ui-state-error":"ui-state-highlight")}).appendTo(o);
o.pnotify_version="1.0.1";
o.pnotify=function(t){var u=k;
if(typeof t=="string"){k.pnotify_text=t
}else{k=d.extend({},k,t)
}o.opts=k;
if(k.pnotify_shadow!=u.pnotify_shadow){if(k.pnotify_shadow&&!d.browser.msie){o.shadow_container=d("<div />",{"class":"ui-widget-shadow ui-pnotify-shadow"}).prependTo(o)
}else{o.children(".ui-pnotify-shadow").remove()
}}if(k.pnotify_addclass===false){o.removeClass(u.pnotify_addclass)
}else{if(k.pnotify_addclass!==u.pnotify_addclass){o.removeClass(u.pnotify_addclass).addClass(k.pnotify_addclass)
}}if(k.pnotify_title===false){o.title_container.hide("fast")
}else{if(k.pnotify_title!==u.pnotify_title){o.title_container.html(k.pnotify_title).show(200)
}}if(k.pnotify_text===false){o.text_container.hide("fast")
}else{if(k.pnotify_text!==u.pnotify_text){if(k.pnotify_insert_brs){k.pnotify_text=k.pnotify_text.replace(/\n/g,"<br />")
}o.text_container.html(k.pnotify_text).show(200)
}}o.pnotify_history=k.pnotify_history;
if(k.pnotify_type!=u.pnotify_type){o.container.toggleClass("ui-state-error ui-state-highlight")
}if((k.pnotify_notice_icon!=u.pnotify_notice_icon&&k.pnotify_type=="notice")||(k.pnotify_error_icon!=u.pnotify_error_icon&&k.pnotify_type=="error")||(k.pnotify_type!=u.pnotify_type)){o.container.find("div.ui-pnotify-icon").remove();
if((k.pnotify_error_icon&&k.pnotify_type=="error")||(k.pnotify_notice_icon)){d("<div />",{"class":"ui-pnotify-icon"}).append(d("<span />",{"class":k.pnotify_type=="error"?k.pnotify_error_icon:k.pnotify_notice_icon})).prependTo(o.container)
}}if(k.pnotify_width!==u.pnotify_width){o.animate({width:k.pnotify_width})
}if(k.pnotify_min_height!==u.pnotify_min_height){o.container.animate({minHeight:k.pnotify_min_height})
}if(k.pnotify_opacity!==u.pnotify_opacity){o.fadeTo(k.pnotify_animate_speed,k.pnotify_opacity)
}if(!k.pnotify_hide){o.pnotify_cancel_remove()
}else{if(!u.pnotify_hide){o.pnotify_queue_remove()
}}o.pnotify_queue_position();
return o
};
o.pnotify_queue_position=function(){if(a){clearTimeout(a)
}a=setTimeout(d.pnotify_position_all,10)
};
o.pnotify_display=function(){if(!o.parent().length){o.appendTo(e)
}if(k.pnotify_before_open){if(k.pnotify_before_open(o)===false){return
}}o.pnotify_queue_position();
if(k.pnotify_animation=="fade"||k.pnotify_animation.effect_in=="fade"){o.show().fadeTo(0,0).hide()
}else{if(k.pnotify_opacity!=1){o.show().fadeTo(0,k.pnotify_opacity).hide()
}}o.animate_in(function(){if(k.pnotify_after_open){k.pnotify_after_open(o)
}o.pnotify_queue_position();
if(k.pnotify_hide){o.pnotify_queue_remove()
}})
};
o.pnotify_remove=function(){if(o.timer){window.clearTimeout(o.timer);
o.timer=null
}if(k.pnotify_before_close){if(k.pnotify_before_close(o)===false){return
}}o.animate_out(function(){if(k.pnotify_after_close){if(k.pnotify_after_close(o)===false){return
}}o.pnotify_queue_position();
if(k.pnotify_remove){o.detach()
}})
};
o.animate_in=function(u){s="in";
var t;
if(typeof k.pnotify_animation.effect_in!="undefined"){t=k.pnotify_animation.effect_in
}else{t=k.pnotify_animation
}if(t=="none"){o.show();
u()
}else{if(t=="show"){o.show(k.pnotify_animate_speed,u)
}else{if(t=="fade"){o.show().fadeTo(k.pnotify_animate_speed,k.pnotify_opacity,u)
}else{if(t=="slide"){o.slideDown(k.pnotify_animate_speed,u)
}else{if(typeof t=="function"){t("in",u,o)
}else{if(o.effect){o.effect(t,{},k.pnotify_animate_speed,u)
}}}}}}};
o.animate_out=function(u){s="out";
var t;
if(typeof k.pnotify_animation.effect_out!="undefined"){t=k.pnotify_animation.effect_out
}else{t=k.pnotify_animation
}if(t=="none"){o.hide();
u()
}else{if(t=="show"){o.hide(k.pnotify_animate_speed,u)
}else{if(t=="fade"){o.fadeOut(k.pnotify_animate_speed,u)
}else{if(t=="slide"){o.slideUp(k.pnotify_animate_speed,u)
}else{if(typeof t=="function"){t("out",u,o)
}else{if(o.effect){o.effect(t,{},k.pnotify_animate_speed,u)
}}}}}}};
o.pnotify_cancel_remove=function(){if(o.timer){window.clearTimeout(o.timer)
}};
o.pnotify_queue_remove=function(){o.pnotify_cancel_remove();
o.timer=window.setTimeout(function(){o.pnotify_remove()
},(isNaN(k.pnotify_delay)?0:k.pnotify_delay))
};
o.closer=d("<div />",{"class":"ui-pnotify-closer",css:{cursor:"pointer",display:"none"},click:function(){o.pnotify_remove();
o.closer.hide()
}}).append(d("<span />",{"class":"ui-icon ui-icon-circle-close"})).appendTo(o.container);
if((k.pnotify_error_icon&&k.pnotify_type=="error")||(k.pnotify_notice_icon)){d("<div />",{"class":"ui-pnotify-icon"}).append(d("<span />",{"class":k.pnotify_type=="error"?k.pnotify_error_icon:k.pnotify_notice_icon})).appendTo(o.container)
}o.title_container=d("<div />",{"class":"ui-pnotify-title",html:k.pnotify_title}).appendTo(o.container);
if(k.pnotify_title===false){o.title_container.hide()
}if(k.pnotify_insert_brs&&typeof k.pnotify_text=="string"){k.pnotify_text=k.pnotify_text.replace(/\n/g,"<br />")
}o.text_container=d("<div />",{"class":"ui-pnotify-text",html:k.pnotify_text}).appendTo(o.container);
if(k.pnotify_text===false){o.text_container.hide()
}if(typeof k.pnotify_width=="string"){o.css("width",k.pnotify_width)
}if(typeof k.pnotify_min_height=="string"){o.container.css("min-height",k.pnotify_min_height)
}o.pnotify_history=k.pnotify_history;
var q=e.data("pnotify");
if(q==null||typeof q!="object"){q=[]
}if(k.pnotify_stack.push=="top"){q=d.merge([o],q)
}else{q=d.merge(q,[o])
}e.data("pnotify",q);
if(k.pnotify_after_init){k.pnotify_after_init(o)
}if(k.pnotify_history){var p=e.data("pnotify_history");
if(typeof p=="undefined"){p=d("<div />",{"class":"ui-pnotify-history-container ui-state-default ui-corner-bottom",mouseleave:function(){p.animate({top:"-"+i+"px"},{duration:100,queue:false})
}}).append(d("<div />",{"class":"ui-pnotify-history-header",text:"Redisplay"})).append(d("<button />",{"class":"ui-pnotify-history-all ui-state-default ui-corner-all",text:"All",mouseenter:function(){d(this).addClass("ui-state-hover")
},mouseleave:function(){d(this).removeClass("ui-state-hover")
},click:function(){d.each(q,function(){if(this.pnotify_history&&this.pnotify_display){this.pnotify_display()
}});
return false
}})).append(d("<button />",{"class":"ui-pnotify-history-last ui-state-default ui-corner-all",text:"Last",mouseenter:function(){d(this).addClass("ui-state-hover")
},mouseleave:function(){d(this).removeClass("ui-state-hover")
},click:function(){var t=1;
while(!q[q.length-t]||!q[q.length-t].pnotify_history||q[q.length-t].is(":visible")){if(q.length-t===0){return false
}t++
}var u=q[q.length-t];
if(u.pnotify_display){u.pnotify_display()
}return false
}})).appendTo(e);
var n=d("<span />",{"class":"ui-pnotify-history-pulldown ui-icon ui-icon-grip-dotted-horizontal",mouseenter:function(){p.animate({top:"0"},{duration:100,queue:false})
}}).appendTo(p);
i=n.offset().top+2;
p.css({top:"-"+i+"px"});
e.data("pnotify_history",p)
}}k.pnotify_stack.animation=false;
o.pnotify_display();
return o
}});
var j=/^on/;
var c=/^(dbl)?click$|^mouse(move|down|up|over|out|enter|leave)$|^contextmenu$/;
var h=/^(focus|blur|select|change|reset)$|^key(press|down|up)$/;
var g=/^(scroll|resize|(un)?load|abort|error)$/;
var f=function(l,k){var m;
l=l.toLowerCase();
if(document.createEvent&&this.dispatchEvent){l=l.replace(j,"");
if(l.match(c)){d(this).offset();
m=document.createEvent("MouseEvents");
m.initMouseEvent(l,k.bubbles,k.cancelable,k.view,k.detail,k.screenX,k.screenY,k.clientX,k.clientY,k.ctrlKey,k.altKey,k.shiftKey,k.metaKey,k.button,k.relatedTarget)
}else{if(l.match(h)){m=document.createEvent("UIEvents");
m.initUIEvent(l,k.bubbles,k.cancelable,k.view,k.detail)
}else{if(l.match(g)){m=document.createEvent("HTMLEvents");
m.initEvent(l,k.bubbles,k.cancelable)
}}}if(!m){return
}this.dispatchEvent(m)
}else{if(!l.match(j)){l="on"+l
}m=document.createEventObject(k);
this.fireEvent(l,m)
}};
d.pnotify.defaults={pnotify_title:false,pnotify_text:false,pnotify_addclass:"",pnotify_nonblock:false,pnotify_nonblock_opacity:0.2,pnotify_history:true,pnotify_width:"300px",pnotify_min_height:"16px",pnotify_type:"notice",pnotify_notice_icon:"ui-icon ui-icon-info",pnotify_error_icon:"ui-icon ui-icon-alert",pnotify_animation:"fade",pnotify_animate_speed:"slow",pnotify_opacity:1,pnotify_shadow:false,pnotify_closer:true,pnotify_hide:true,pnotify_delay:8000,pnotify_mouse_reset:true,pnotify_remove:true,pnotify_insert_brs:true,pnotify_stack:{dir1:"down",dir2:"left",push:"bottom"}}
})(jQuery);if(!this.JSON){this.JSON={}
}(function(){function f(n){return n<10?"0"+n:n
}if(typeof Date.prototype.toJSON!=="function"){Date.prototype.toJSON=function(key){return isFinite(this.valueOf())?this.getUTCFullYear()+"-"+f(this.getUTCMonth()+1)+"-"+f(this.getUTCDate())+"T"+f(this.getUTCHours())+":"+f(this.getUTCMinutes())+":"+f(this.getUTCSeconds())+"Z":null
};
String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(key){return this.valueOf()
}
}var cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,escapable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,gap,indent,meta={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},rep;
function quote(string){escapable.lastIndex=0;
return escapable.test(string)?'"'+string.replace(escapable,function(a){var c=meta[a];
return typeof c==="string"?c:"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)
})+'"':'"'+string+'"'
}function str(key,holder){var i,k,v,length,mind=gap,partial,value=holder[key];
if(value&&typeof value==="object"&&typeof value.toJSON==="function"){value=value.toJSON(key)
}if(typeof rep==="function"){value=rep.call(holder,key,value)
}switch(typeof value){case"string":return quote(value);
case"number":return isFinite(value)?String(value):"null";
case"boolean":case"null":return String(value);
case"object":if(!value){return"null"
}gap+=indent;
partial=[];
if(Object.prototype.toString.apply(value)==="[object Array]"){length=value.length;
for(i=0;
i<length;
i+=1){partial[i]=str(i,value)||"null"
}v=partial.length===0?"[]":gap?"[\n"+gap+partial.join(",\n"+gap)+"\n"+mind+"]":"["+partial.join(",")+"]";
gap=mind;
return v
}if(rep&&typeof rep==="object"){length=rep.length;
for(i=0;
i<length;
i+=1){k=rep[i];
if(typeof k==="string"){v=str(k,value);
if(v){partial.push(quote(k)+(gap?": ":":")+v)
}}}}else{for(k in value){if(Object.hasOwnProperty.call(value,k)){v=str(k,value);
if(v){partial.push(quote(k)+(gap?": ":":")+v)
}}}}v=partial.length===0?"{}":gap?"{\n"+gap+partial.join(",\n"+gap)+"\n"+mind+"}":"{"+partial.join(",")+"}";
gap=mind;
return v
}}if(typeof JSON.stringify!=="function"){JSON.stringify=function(value,replacer,space){var i;
gap="";
indent="";
if(typeof space==="number"){for(i=0;
i<space;
i+=1){indent+=" "
}}else{if(typeof space==="string"){indent=space
}}rep=replacer;
if(replacer&&typeof replacer!=="function"&&(typeof replacer!=="object"||typeof replacer.length!=="number")){throw new Error("JSON.stringify")
}return str("",{"":value})
}
}if(typeof JSON.parse!=="function"){JSON.parse=function(text,reviver){var j;
function walk(holder,key){var k,v,value=holder[key];
if(value&&typeof value==="object"){for(k in value){if(Object.hasOwnProperty.call(value,k)){v=walk(value,k);
if(v!==undefined){value[k]=v
}else{delete value[k]
}}}}return reviver.call(holder,key,value)
}text=String(text);
cx.lastIndex=0;
if(cx.test(text)){text=text.replace(cx,function(a){return"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)
})
}if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){j=eval("("+text+")");
return typeof reviver==="function"?walk({"":j},""):j
}throw new SyntaxError("JSON.parse")
}
}}());var jsonParse=(function(){var e="(?:-?\\b(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][+-]?[0-9]+)?\\b)";
var j='(?:[^\\0-\\x08\\x0a-\\x1f"\\\\]|\\\\(?:["/\\\\bfnrt]|u[0-9A-Fa-f]{4}))';
var i='(?:"'+j+'*")';
var d=new RegExp("(?:false|true|null|[\\{\\}\\[\\]]|"+e+"|"+i+")","g");
var k=new RegExp("\\\\(?:([^u])|u(.{4}))","g");
var g={'"':'"',"/":"/","\\":"\\",b:"\b",f:"\f",n:"\n",r:"\r",t:"\t"};
function h(l,m,n){return m?g[m]:String.fromCharCode(parseInt(n,16))
}var c=new String("");
var a="\\";
var f={"{":Object,"[":Array};
var b=Object.hasOwnProperty;
return function(u,q){var p=u.match(d);
var x;
var v=p[0];
var l=false;
if("{"===v){x={}
}else{if("["===v){x=[]
}else{x=[];
l=true
}}var t;
var r=[x];
for(var o=1-l,m=p.length;
o<m;
++o){v=p[o];
var w;
switch(v.charCodeAt(0)){default:w=r[0];
w[t||w.length]=+(v);
t=void 0;
break;
case 34:v=v.substring(1,v.length-1);
if(v.indexOf(a)!==-1){v=v.replace(k,h)
}w=r[0];
if(!t){if(w instanceof Array){t=w.length
}else{t=v||c;
break
}}w[t]=v;
t=void 0;
break;
case 91:w=r[0];
r.unshift(w[t||w.length]=[]);
t=void 0;
break;
case 93:r.shift();
break;
case 102:w=r[0];
w[t||w.length]=false;
t=void 0;
break;
case 110:w=r[0];
w[t||w.length]=null;
t=void 0;
break;
case 116:w=r[0];
w[t||w.length]=true;
t=void 0;
break;
case 123:w=r[0];
r.unshift(w[t||w.length]={});
t=void 0;
break;
case 125:r.shift();
break
}}if(l){if(r.length!==1){throw new Error()
}x=x[0]
}else{if(r.length){throw new Error()
}}if(q){var s=function(C,B){var D=C[B];
if(D&&typeof D==="object"){var n=null;
for(var z in D){if(b.call(D,z)&&D!==C){var y=s(D,z);
if(y!==void 0){D[z]=y
}else{if(!n){n=[]
}n.push(z)
}}}if(n){for(var A=n.length;
--A>=0;
){delete D[n[A]]
}}}return q.call(C,B,D)
};
x=s({"":x},"")
}return x
}
})();var QCD=QCD||{};
var pnotify_stack={dir1:"up",dir2:"left",firstpos1:15,firstpos2:30};
QCD.MessagesController=function(){this.clearMessager=function(){$.pnotify_remove_all()
};
this.addMessage=function(a){if(a.autoClose==undefined){a.autoClose=true
}type=a.type.toLowerCase();
if(type=="failure"){type="error"
}var b={pnotify_title:a.title,pnotify_text:a.content,pnotify_stack:pnotify_stack,pnotify_history:false,pnotify_width:"300px",pnotify_type:type,pnotify_addclass:type=="success"?"ui-state-success":"",pnotify_notice_icon:type=="success"?"ui-icon ui-icon-success":"ui-icon ui-icon-notify",pnotify_error_icon:"ui-icon ui-icon-error",pnotify_opacity:0.9,pnotify_delay:4000,pnotify_hide:a.autoClose};
if(!a.autoClose){b.pnotify_width="400px";
b.pnotify_addclass=b.pnotify_addclass+" noAutoCloseClass"
}$.pnotify(b)
}
};var QCD=QCD||{};
QCD.WindowController=function(p){var i=null;
var n;
var a=new Array();
var g=new Array();
var k=new Object();
var o=null;
var j=null;
var m=new QCD.MessagesController();
var e=p;
var f;
var h;
function d(q){i=$("#mainPageIframe");
n=$("#loadingIndicator");
n.hide();
i.load(function(){l(this)
});
$(window).bind("resize",c);
f=new QCD.menu.MenuController(e,q);
c()
}this.addMessage=function(q,r){m.addMessage(q,r)
};
this.performLogout=function(){window.location="j_spring_security_logout"
};
this.goToPage=function(q,r,s){if(r){a.push(r)
}if(s){j="page/"+q
}else{j=q
}b(j)
};
window.openModal=function(s,q,r){a.push(r);
if(!k[s]){k[s]=QCD.utils.Modal.createModal()
}g.push(k[s]);
if(q.indexOf("?")!=-1){q+="&"
}else{q+="?"
}q+="popup=true";
k[s].show("page/"+q,function(){if(this.src!=""&&this.contentWindow.init){this.contentWindow.init(o);
o=null
}})
};
window.changeModalSize=function(r,q){if(g.length==0){return
}var s=g[g.length-1];
s.changeSize(r,q)
};
this.onLoginSuccess=function(){this.goToLastPage()
};
this.goBack=function(q){h=q;
var r=a.pop();
o=r;
if(g.length==0){j=r.url;
b(j)
}else{modal=g.pop();
modal.hide();
l()
}};
this.getLastPageController=function(){return h
};
this.goToLastPage=function(){b(j)
};
this.goToMenuPosition=function(q){f.goToMenuPosition(q)
};
this.hasMenuPosition=function(q){return f.hasMenuPosition(q)
};
this.onSessionExpired=function(q,s){o=q;
if(s){var r=g[g.length-1];
r.show("login.html?popup=true&targetUrl="+escape(q.url),function(){if(this.src!=""&&this.contentWindow.init){this.contentWindow.init(o);
o=null
}})
}else{b("login.html")
}};
this.restoreMenuState=function(){f.restoreState()
};
this.canChangePage=function(){try{if(i[0].contentWindow.canClose){return i[0].contentWindow.canClose()
}}catch(q){}return true
};
this.onMenuClicked=function(q){j=q;
a=new Array();
b(j)
};
function b(q){n.show();
if(q.search("://")<=0){if(q.indexOf("?")==-1){q+="?iframe=true"
}else{if(q.charAt(q.length-1)=="?"){q+="iframe=true"
}else{q+="&iframe=true"
}}}i.attr("src",q)
}function l(){try{if(i[0].contentWindow.init){i[0].contentWindow.init(o);
o=null
}}catch(q){}n.hide()
}function c(){var r=$(document).width();
var s=Math.round(r*0.02);
var q=Math.round(r-2*s);
$("#q_menu_row1").width(q);
$("#secondLevelMenu").width(q)
}this.updateSize=c;
d(this)
};var QCD=QCD||{};
QCD.menu=QCD.menu||{};
QCD.menu.MenuController=function(f,d){var j=d;
var c=$("#firstLevelMenu");
var l=$("#secondLevelMenu");
var i=new Object();
i.first=null;
i.second=null;
var h=new Object();
h.first=null;
h.second=null;
function e(r){model=new QCD.menu.MenuModel(r.menuItems);
var v=$("<ul>").addClass("q_row1");
var q=$("<div>").attr("id","q_menu_row1");
q.append(v);
var p=$("<div>").attr("id","q_row1");
p.append(q);
var u=$("<div>").attr("id","q_row1_out");
u.append(p);
c.append(u);
for(var o in model.items){var t=model.items[o];
var s=$("<li>").html("<a href='#'><span>"+t.label+"</span></a>").attr("id","firstLevelButton_"+t.name);
v.append(s);
t.element=s;
s.click(function(w){k($(this),w)
})
}i.first=model.selectedItem;
model.selectedItem.element.addClass("path");
i.second=model.selectedItem.selectedItem;
n();
m(model.selectedItem.selectedItem.page)
}function k(q,r){q.children().blur();
var o=q.attr("id").substring(17);
var p=model.itemsMap[o];
model.selectedItem=p;
if(model.selectedItem.selectedItem){h.second=model.selectedItem.selectedItem
}n()
}function b(p){p.children().blur();
if(!g()){return
}var o=p.attr("id").substring(18);
var q=model.selectedItem.itemsMap[o];
model.selectedItem.selectedItem=q;
i.first.element.removeClass("path");
i.first=model.selectedItem;
i.second=model.selectedItem.selectedItem;
i.first.element.addClass("path");
n();
m(model.selectedItem.selectedItem.page)
}this.goToMenuPosition=function(o){var r=o.split(".");
var q=model.itemsMap[r[0]];
var p=q.itemsMap[r[0]+"_"+r[1]];
model.selectedItem.element.removeClass("path");
q.selectedItem=p;
i.first=q;
i.second=p;
model.selectedItem=q;
n();
m(model.selectedItem.selectedItem.page)
};
this.hasMenuPosition=function(o){var r=o.split(".");
var q=model.itemsMap[r[0]];
if(q==null){return false
}var p=q.itemsMap[r[0]+"_"+r[1]];
if(p==null){return false
}return true
};
this.restoreState=function(){model.selectedItem=i.first;
if(i.second){model.selectedItem.selectedItem=i.second
}n()
};
function n(){if(h.first!=model.selectedItem){if(h.first){h.first.element.removeClass("activ");
h.first.selectedItem=null
}h.first=model.selectedItem;
h.first.element.addClass("activ");
a()
}else{if(h.second!=model.selectedItem.selectedItem){if(h.second){h.second.element.removeClass("activ")
}h.second=model.selectedItem.selectedItem;
if(h.second){h.second.element.addClass("activ")
}}}}function a(){l.children().remove();
var t=$("<ul>").addClass("q_row2");
var q=$("<div>").attr("id","q_menu_row2");
q.append(t);
var o=$("<div>").attr("id","q_row2_out");
o.append(q);
l.append(o);
for(var r in model.selectedItem.items){var p=model.selectedItem.items[r];
var s=$("<li>").html("<a href='#'><span>"+p.label+"</span></a>").attr("id","secondLevelButton_"+p.name);
t.append(s);
p.element=s;
s.click(function(){b($(this))
});
if(i.second&&i.second.name==p.name){p.element.addClass("activ");
h.second=p
}}}function m(o){j.onMenuClicked(o)
}function g(){return j.canChangePage()
}e(f)
};var QCD=QCD||{};
QCD.menu=QCD.menu||{};
QCD.menu.MenuModel=function(c){this.selectedItem=null;
this.items=new Array();
this.itemsMap=new Object();
for(var b in c){var a=new QCD.menu.FirstButton(c[b]);
this.items.push(a);
this.itemsMap[a.name]=a;
if(!this.selectedItem){this.selectedItem=a;
a.selectedItem=a.items[0]
}}};
QCD.menu.FirstButton=function(c){this.name=c.name;
this.label=c.label;
this.element=null;
this.selectedItem=null;
this.itemsMap=new Object();
this.items=new Array();
for(var b in c.items){var a=new QCD.menu.SecondButton(c.items[b],this);
this.itemsMap[a.name]=a;
this.items.push(a)
}};
QCD.menu.SecondButton=function(a,b){this.name=b.name+"_"+a.name;
this.label=a.label;
this.page=a.page;
this.element=null
};var QCDConnector={};
QCDConnector.windowName=null;
QCDConnector.mainController=null;
QCDConnector.sendGet=function(d,f,e,b){if(!QCDConnector.windowName){throw ("no window name defined in conector")
}var a=QCDConnector.windowName+"/"+d+".html";
if(f){var g=true;
for(var c in f){if(g){a+="?"
}else{a+="&"
}a+=c+"="+f[c];
g=false
}}$.ajax({url:a,type:"GET",dataType:"json",contentType:"application/json; charset=utf-8",complete:function(i,l){if(i.status==200){var j=$.trim(i.responseText);
if(j=="sessionExpired"){QCDConnector.mainController.onSessionExpired();
return
}if(j.substring(0,20)=="<![CDATA[ERROR PAGE:"){var k=j.substring(20,j.search("]]>"));
QCDConnector.showErrorMessage(k,b);
return
}if(e){if(j!=""){var h=JSON.parse(j);
e(h)
}else{e(null)
}}}else{QCDConnector.showErrorMessage("connection error: "+i.statusText);
if(b){b(i.statusText)
}}}})
};
QCDConnector.sendPost=function(d,c,b){if(!QCDConnector.windowName){throw ("no window name defined in conector")
}var a=QCDConnector.windowName+".html";
$.ajax({url:a,type:"POST",data:d,dataType:"json",contentType:"application/json; charset=utf-8",complete:function(f,i){if(f.status==200){var g=$.trim(f.responseText);
if(g=="sessionExpired"){QCDConnector.mainController.onSessionExpired();
return
}if(g.substring(0,20)=="<![CDATA[ERROR PAGE:"){var h=g.substring(20,g.search("]]>"));
QCDConnector.showErrorMessage(h,b);
return
}if(c){if(g!=""){var e=JSON.parse(g);
c(e)
}else{c(null)
}}}else{QCDConnector.showErrorMessage("connection error: "+f.statusText);
if(b){b(f.statusText)
}}}})
};
QCDConnector.showErrorMessage=function(d,b){var a=d.split("##");
var c={};
if(a.length==2){c.title=a[0];
c.content=a[1]
}else{c.content=d
}c.type="failure";
QCDConnector.mainController.showMessage(c);
if(b){b(c)
}};var QCD=QCD||{};
QCD.info=function(a){if(window.console&&window.console.info){window.console.info(a)
}};
QCD.debug=function(a){if(window.console&&window.console.debug){window.console.debug(a)
}};
QCD.error=function(a){if(window.console&&window.console.error){window.console.error(a)
}};var QCD=QCD||{};
QCD.utils=QCD.utils||{};
QCD.utils.Modal={};
QCD.utils.Modal.createModal=function(){var b=$("<div>").addClass("jqmWindow").width(600);
var a=$("<div>").css("border","solid red 0px").width(600).height(400);
b.append(a);
var c=$('<iframe frameborder="0" src="" width="600" height="400">');
a.append(c);
$("body").append(b);
b.jqm({modal:true});
return{dialog:b,container:a,iframe:c,show:function(e,d){this.iframe.hide();
this.dialog.jqmShow();
QCD.components.elements.utils.LoadingIndicator.blockElement(this.dialog);
this.iframe.load(function(){c.show();
d.call(this);
QCD.components.elements.utils.LoadingIndicator.unblockElement(b)
});
this.iframe.attr("src",e)
},refresh:function(){this.iframe.attr("src",this.iframe.attr("src"))
},hide:function(){this.iframe.unbind("load");
this.dialog.jqmHide()
},changeSize:function(e,d){this.dialog.css("marginLeft","-"+(e/2)+"px");
this.dialog.width(e);
this.container.width(e);
this.container.height(d);
this.iframe.width(e);
this.iframe.height(d)
}}
};var QCDOptions={};
QCDOptions.getElementOptions=function(a){var c=$($("#"+a+" .element_options")[0]);
if(!c.html()||$.trim(c.html())==""){var b=new Object()
}else{var b=jsonParse(c.html())
}c.remove();
return b
};var QCDPageConstructor={};
QCDPageConstructor.getChildrenComponents=function(elements,mainController){var components=new Object();
elements.each(function(i,e){var element=$(e);
if(element.hasClass("component")){var component=null;
var elementFullName=element.attr("id");
var elementSearchName=elementFullName.replace(/\./g,"\\.");
var elementName=elementFullName.split(".")[elementFullName.split(".").length-1];
var jsObjectElement=$("#"+elementSearchName+" > .element_js_object");
var jsObjectClassName=$.trim(jsObjectElement.html());
jsObjectElement.remove();
component=eval("new "+jsObjectClassName+"(element, mainController);");
components[elementName]=component
}});
return components
};var QCDSerializator={};
QCDSerializator.serializeForm=function(c){var b=c.serializeArray();
var d={};
$.each(b,function(){if(/.*\[.*\]/.test(this.name)){var e=this.name.substring(0,this.name.search(/\[/));
var a=this.name.substring(this.name.search(/\[/)+1,this.name.search(/\]/));
if(!d[e]){d[e]=new Object()
}d[e][a]=this.value||""
}else{d[this.name]=this.value||""
}});
return d
};
QCDSerializator.equals=function(b,a){if(b==null&&a==null){return true
}if(b==null||a==null){return false
}if(typeof(b)!=typeof(a)){return false
}var d={};
for(var c in b){d[c]=1
}for(var c in a){d[c]=1
}for(var c in d){if(b.hasOwnProperty(c)!=a.hasOwnProperty(c)){if((b.hasOwnProperty(c)&&typeof(b[c])=="function")||(a.hasOwnProperty(c)&&typeof(a[c])=="function")){continue
}else{return false
}}if(typeof(b[c])!=typeof(a[c])){return false
}if(typeof(b[c])=="object"){if(!QCDSerializator.equals(b[c],a[c])){return false
}}else{if(b[c]!==a[c]){return false
}}}return true
};var SNOW_Picture="/img/core/snow6.gif";
var SNOW_no=100;
var SNOW_browser_IE_NS=(document.body)?1:0;
var SNOW_browser_MOZ=(self.innerWidth)?1:0;
var SNOW_browser_IE7=(document.documentElement.clientHeight)?1:0;
var SNOW_Time;
var SNOW_dx,SNOW_xp,SNOW_yp;
var SNOW_am,SNOW_stx,SNOW_sty;
var i,SNOW_Browser_Width,SNOW_Browser_Height;
if(SNOW_browser_IE_NS){SNOW_Browser_Width=document.body.clientWidth;
SNOW_Browser_Height=document.body.clientHeight
}else{if(SNOW_browser_MOZ){SNOW_Browser_Width=self.innerWidth-20;
SNOW_Browser_Height=self.innerHeight
}else{if(SNOW_browser_IE7){SNOW_Browser_Width=document.documentElement.clientWidth;
SNOW_Browser_Height=document.documentElement.clientHeight
}}}SNOW_dx=new Array();
SNOW_xp=new Array();
SNOW_yp=new Array();
SNOW_am=new Array();
SNOW_stx=new Array();
SNOW_sty=new Array();
function SNOW_Weather(a){if(a){for(i=0;
i<SNOW_no;
++i){SNOW_dx[i]=0;
SNOW_xp[i]=Math.random()*(SNOW_Browser_Width-50);
SNOW_yp[i]=Math.random()*SNOW_Browser_Height;
SNOW_am[i]=Math.random()*20;
SNOW_stx[i]=0.02+Math.random()/10;
SNOW_sty[i]=0.7+Math.random();
var c=$("<div>").attr("id","SNOW_flake"+i).attr("style","position: absolute; z-index: "+i+"; visibility: visible; top: 15px; left: 15px;");
var b=$("<img>").attr("src",SNOW_Picture).attr("border","0");
c.append(b);
$("body").append(c)
}}for(i=0;
i<SNOW_no;
++i){SNOW_yp[i]+=SNOW_sty[i];
if(SNOW_yp[i]>SNOW_Browser_Height-50){SNOW_xp[i]=Math.random()*(SNOW_Browser_Width-SNOW_am[i]-30);
SNOW_yp[i]=0;
SNOW_stx[i]=0.02+Math.random()/10;
SNOW_sty[i]=0.7+Math.random()
}SNOW_dx[i]+=SNOW_stx[i];
document.getElementById("SNOW_flake"+i).style.top=SNOW_yp[i]+"px";
document.getElementById("SNOW_flake"+i).style.left=SNOW_xp[i]+SNOW_am[i]*Math.sin(SNOW_dx[i])+"px"
}SNOW_Time=setTimeout("SNOW_Weather(false)",10)
}jQuery(document).ready(function(){if(QCD&&QCD.global&&QCD.global.isSonowOnPage){SNOW_Weather(true)
}});var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.Component=function(b,h){var i=h;
var c=b;
var g=c.attr("id");
var j=g.replace(/\./g,"\\.");
var k=g.split(".")[g.split(".").length-1];
this.element=c;
this.elementPath=g;
this.elementSearchName=j;
this.elementName=k;
var d=true;
var f=true;
this.contextObject=null;
var e;
function a(m){var l=$("#"+j+" > .element_options");
if(!l.html()||$.trim(l.html())==""){m.options=new Object()
}else{m.options=jsonParse(l.html())
}l.remove();
d=m.options.defaultVisible;
f=m.options.defaultEnabled
}this.getValue=function(){var l=new Object();
l.enabled=f;
l.visible=d;
if(this.getComponentValue){l.content=this.getComponentValue()
}else{l.content=null
}if(this.contextObject){l.context=this.contextObject
}if(this.getComponentsValue){l.components=this.getComponentsValue()
}return l
};
this.setValue=function(l){this.setEnabled(l.enabled);
this.setVisible(l.visible);
this.setMessages(l.messages);
if(l.components){this.setComponentsValue(l)
}if(l.content!=null){this.setComponentValue(l.content)
}if(l.updateState){this.performUpdateState()
}if(e){this.fireOnChangeListeners("onSetValue",[l])
}};
this.performUpdateState=function(){};
this.addContext=function(m,l){if(!this.contextObject){this.contextObject=new Object
}this.contextObject[m]=l
};
this.fireEvent=function(l,m){this.beforeEventFunction();
i.callEvent(l,g,null,m)
};
this.setState=function(l){this.setEnabled(l.enabled);
this.setVisible(l.visible);
if(this.setComponentState){this.setComponentState(l.content)
}else{QCD.error(this.elementPath+".setComponentState() no implemented")
}if(l.components){this.setComponentsState(l)
}if(e){this.fireOnChangeListeners("onSetValue",[l])
}};
this.setEditable=function(l){this.setComponentEditable(l)
};
this.performScript=function(){if(this.options.script){i.getActionEvaluator().performJsAction(this.options.script,this)
}if(this.performComponentScript){this.performComponentScript()
}};
this.updateSize=function(m,l){};
this.setMessages=function(m){for(var l in m){i.showMessage(m[l])
}};
this.setEnabled=function(n,l){f=n;
this.setComponentEnabled(f);
if(l&&this.components){for(var m in this.components){this.components[m].setEnabled(n,l)
}}};
this.isEnabled=function(){return f
};
this.setVisible=function(l){d=l;
if(this.setComponentVisible){this.setComponentVisible(d)
}else{if(d){c.show()
}else{c.hide()
}}};
this.isVisible=function(){return d
};
this.addOnChangeListener=function(l){if(!e){e=new Array()
}e.push(l)
};
this.removeOnChangeListeners=function(l){e=new Array()
};
this.fireOnChangeListeners=function(o,l){for(var m in e){var n=e[m][o];
if(n){n.apply(this,l)
}}};
this.isChanged=function(){return this.isComponentChanged()
};
this.isComponentChanged=function(){return false
};
a(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.Container=function(a,e,c){$.extend(this,new QCD.components.Component(a,e));
var d=e;
var b;
this.constructChildren=function(f){b=QCDPageConstructor.getChildrenComponents(f,d);
this.components=b
};
this.getChildren=function(){return b
};
this.getComponentsValue=function(){var f=new Object();
for(var g in b){f[g]=b[g].getValue()
}return f
};
this.setComponentsValue=function(h){for(var g in h.components){var f=h.components[g];
b[g].setValue(f)
}};
this.setComponentsState=function(h){for(var g in h.components){var f=h.components[g];
b[g].setState(f)
}};
this.performScript=function(){if(this.performComponentScript){this.performComponentScript()
}if(this.options.script){d.getActionEvaluator().performJsAction(this.options.script,this)
}for(var f in b){b[f].performScript()
}};
this.isChanged=function(){changed=this.isComponentChanged();
if(changed==true){return true
}for(var f in b){if(b[f].isChanged()){changed=true;
break
}}return changed
}
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.Form=function(d,l){$.extend(this,new QCD.components.Container(d,l));
var m=l;
var f=d;
var k=this.elementPath;
var o=null;
var a=null;
var i=null;
var h=null;
var e=this.options.translations;
var j=this.options.header;
if(this.options.referenceName){m.registerReferenceName(this.options.referenceName,this)
}function b(q){var p=$("#"+q.elementSearchName+"_formComponents");
q.constructChildren(p.children())
}this.getComponentValue=function(){return{entityId:o,baseValue:a,headerEntityIdentifier:i,header:h}
};
this.setComponentValue=function(p){if(p.valid){if(j){if(p.headerEntityIdentifier){m.setWindowHeader(p.header+" <span>"+p.headerEntityIdentifier+"</span>")
}else{m.setWindowHeader(p.header)
}}}i=p.headerEntityIdentifier;
h=p.header;
o=p.entityId;
g()
};
this.setComponentState=function(p){if(j){if(p.headerEntityIdentifier){m.setWindowHeader(p.header+" <span>"+p.headerEntityIdentifier+"</span>")
}else{m.setWindowHeader(p.header)
}}i=p.headerEntityIdentifier;
h=p.header;
o=p.entityId;
if(p.baseValue){a=p.baseValue
}g()
};
this.setComponentEnabled=function(p){};
this.setComponentLoading=function(p){if(p){c()
}else{g()
}};
this.performUpdateState=function(){a=o
};
this.isComponentChanged=function(){return !(a==o)
};
this.performSave=function(p){n("save",p)
};
this.performSaveAndClear=function(p){n("saveAndClear",p)
};
this.performCopy=function(p){if(m.canClose()){n("copy",p)
}};
this.performDelete=function(p){if(window.confirm(e.confirmDeleteMessage)){n("delete",p)
}};
this.performCancel=function(p){if(window.confirm(e.confirmCancelMessage)){n("reset",p)
}};
this.performEvent=function(p,q){this.fireEvent(null,p,q)
};
this.fireEvent=function(r,p,q){n(p,r,q)
};
function n(p,r,q){c();
m.callEvent(p,k,function(){g()
},q,r)
}this.updateSize=function(p,r){for(var q in this.components){this.components[q].updateSize(p,r)
}};
function c(){QCD.components.elements.utils.LoadingIndicator.blockElement(f)
}function g(){QCD.components.elements.utils.LoadingIndicator.unblockElement(f)
}b(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.layout=QCD.components.containers.layout||{};
QCD.components.containers.layout.BorderLayout=function(a,c){$.extend(this,new QCD.components.containers.layout.Layout(a,c));
if(this.options.referenceName){c.registerReferenceName(this.options.referenceName,this)
}function b(d){d.constructChildren(d.getLayoutChildren())
}this.getLayoutChildren=function(){return $("#"+this.elementSearchName+"_layoutComponents").children()
};
this.updateSize=function(d,f){for(var e in this.components){this.components[e].updateSize(d-20,f-20)
}};
this.setBackground=function(d){$("#"+this.elementSearchName+"_layoutComponents").css("backgroundColor",d);
var e=$("#"+this.elementSearchName+"_layoutComponents > .borderLayoutLabel");
e.css("backgroundColor",d);
if(d==QCD.components.containers.layout.Layout.COLOR_NORMAL){$("#"+this.elementSearchName+"_layoutComponents > .borderLayoutLabel").css("borderTop","none");
$("#"+this.elementSearchName+"_layoutComponents > .borderLayoutLabel > .borderLayoutLabelSideBorder").css("background","transparent")
}else{$("#"+this.elementSearchName+"_layoutComponents > .borderLayoutLabel").css("borderTop","solid #A7A7A7 1px");
$("#"+this.elementSearchName+"_layoutComponents > .borderLayoutLabel > .borderLayoutLabelSideBorder").css("background","#A7A7A7")
}};
b(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.layout=QCD.components.containers.layout||{};
QCD.components.containers.layout.FlowLayout=function(a,c){$.extend(this,new QCD.components.containers.layout.Layout(a,c));
function b(d){d.constructChildren(d.getLayoutChildren())
}this.getLayoutChildren=function(){return $("#"+this.elementSearchName+"_layoutComponents").children()
};
this.updateSize=function(d,f){for(var e in this.components){this.components[e].updateSize(d,f)
}};
b(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.layout=QCD.components.containers.layout||{};
QCD.components.containers.layout.GridLayout=function(b,h){$.extend(this,new QCD.components.containers.layout.Layout(b,h));
var g=this.elementSearchName;
var a=$("#"+g+"_layoutComponents > table > tbody");
var f=this.options.colsNumber;
var d=this.options.fixedRowHeight;
function e(i){i.constructChildren(c())
}function c(){var i=a.children().children().children();
return i
}this.updateSize=function(l,o){var n=l/f;
var q=50;
var t=a.children().children();
for(var p=0;
p<t.length;
p++){var k=$(t[p]);
var j=k.attr("colspan")?k.attr("colspan"):1;
var s=n*j;
k.width(s)
}for(var p in this.components){var k=this.components[p].element.parent();
var m=k.attr("rowspan")?k.attr("rowspan"):1;
var j=k.attr("colspan")?k.attr("colspan"):1;
var s=n*j;
if(d){var r=q*m;
this.components[p].updateSize(s,r)
}else{this.components[p].updateSize(s)
}}};
e(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.layout=QCD.components.containers.layout||{};
QCD.components.containers.layout.Layout=function(a,c){$.extend(this,new QCD.components.Container(a,c));
var b=this.element;
this.getComponentValue=function(){return{}
};
this.setComponentValue=function(d){};
this.setComponentState=function(d){};
this.setMessages=function(d){};
this.setComponentEnabled=function(d){};
this.setComponentLoading=function(){};
this.setBackground=function(d){b.css("backgroundColor",d)
}
};
QCD.components.containers.layout.Layout.COLOR_DISABLED="#dbdbdb";
QCD.components.containers.layout.Layout.COLOR_NORMAL="#FAFAFA";var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.layout=QCD.components.containers.layout||{};
QCD.components.containers.layout.SmallTabLayout=function(b,g){$.extend(this,new QCD.components.containers.layout.Layout(b,g));
var d;
var a;
if(this.options.referenceName){g.registerReferenceName(this.options.referenceName,this)
}function f(o){var l=$("#"+o.elementSearchName+"_layoutComponents").children();
d=new Object();
var m=null;
for(var k=0;
k<l.length;
k++){var h=$(l[k]);
var n=h.attr("id").split("_")[2];
var j=$("#"+o.elementSearchName+"_headerItem_"+n);
j.click(e);
d[n]={header:j,content:h};
if(!m){m=n
}}c(m);
o.constructChildren(o.getLayoutChildren())
}this.setComponentValue=function(k){for(var j in d){d[j].header.removeClass("errorHeader");
d[j].content.removeClass("errorContent")
}for(var h in k.errors){var j=k.errors[h];
d[j].header.addClass("errorHeader");
d[j].content.addClass("errorContent")
}};
this.getLayoutChildren=function(){return $("#"+this.elementSearchName+"_layoutComponents").children().children()
};
function e(){var h=$(this);
h.blur();
if(h.hasClass("active")){return
}var i=h.attr("id").split("_")[2];
c(i)
}function c(i){if(a){a.header.removeClass("active");
a.content.hide()
}var h=d[i];
h.header.addClass("active");
h.content.show();
a=h
}this.updateSize=function(h,k){this.element.height(k);
for(var l in d){d[l].content.height(k-34)
}for(var j in this.components){this.components[j].updateSize(h-20,k-32)
}};
f(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.layout=QCD.components.containers.layout||{};
QCD.components.containers.layout.VerticalLayout=function(a,c){$.extend(this,new QCD.components.containers.layout.Layout(a,c));
function b(d){d.constructChildren(d.getLayoutChildren())
}this.getLayoutChildren=function(){return $("#"+this.elementSearchName+"_layoutComponents > div").children()
};
this.updateSize=function(d,f){divCount=$("#"+this.elementSearchName+"_layoutComponents > div").length;
if(divCount>0){divWidth=parseInt(d/divCount);
firstDivWidth=d-(divWidth*(divCount-1));
first=true;
$("#"+this.elementSearchName+"_layoutComponents > div").each(function(){if(first){$(this).width(firstDivWidth);
first=false
}else{$(this).width(divWidth)
}});
for(var e in this.components){this.components[e].updateSize(divWidth,f)
}}};
b(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.Window=function(m,n){$.extend(this,new QCD.components.Container(m,n));
var b=n;
var a;
var h;
var k;
var g;
var s;
var l;
var f;
this.element.css("height","100%");
var i;
var e;
var q;
var o=new Object();
var r=this.options.oneTab;
var c;
var d=$("#"+this.elementSearchName+"_windowContainerContentBodyWidthMarker");
function p(z){var x=$("#"+z.elementSearchName+"_windowComponents");
z.constructChildren(x.children());
b.setWindowHeaderComponent(z);
q=z.getChildren();
var t=$("#"+z.elementSearchName+"_windowTabs > div");
for(var w in q){var u=$("<a href='#'>").html(z.options.translations["tab."+w]).bind("click",{tabName:w},function(A){A.target.blur();
j(A.data.tabName)
});
o[w]=u;
t.append(u)
}if(z.options.hasRibbon){if(z.options.ribbon){a=new QCD.components.Ribbon(z.options.ribbon,z.elementName,b,z.options.translations)
}element=$("<div>");
var v=$("<div>").attr("id","q_row3_out_container");
element.append(v);
h=$("<div>").attr("id","q_row3_out_left");
v.append(h);
g=$("<div>").attr("id","q_row3_out_main");
v.append(g);
if(a){g.append(a.constructElementContent())
}if(!r){k=$("<div>").attr("id","q_row3_out_tabs_left");
k.append($("<div>"));
v.append(k);
var t=$("<div>").attr("id","q_row3_out_tabs");
v.append(t);
l=$("<div>").attr("id","q_row3_out_tabs_right");
l.append($("<div>"));
v.append(l);
s=t
}f=$("<div>").attr("id","q_row4_out");
element.append(f);
var y=$("#"+z.elementPath+"_windowContainerRibbon");
y.append(element)
}else{$("#"+z.elementPath+"_windowContainerContentBody").css("top","5px")
}if(z.options.firstTabName){j(z.options.firstTabName)
}if(z.options.referenceName){b.registerReferenceName(z.options.referenceName,z)
}}function j(u){if(c){q[c].element.children().hide();
o[c].removeClass("activeTab")
}c=u;
if(!r){o[u].addClass("activeTab")
}q[u].element.children().show();
if(s){if(q[u].getRibbonElement){if(q[u].getRibbonElement()){s.empty();
var t=q[u].getRibbonElement();
if(t){s.append(t)
}s.css("display","inline-block");
k.css("display","inline-block");
l.css("display","inline-block")
}else{s.css("display","none");
k.css("display","none");
l.css("display","none")
}}}}this.getComponentValue=function(){return{}
};
this.setComponentValue=function(v){for(var u in q){o[u].removeClass("errorTab")
}for(var t in v.errors){o[v.errors[t]].addClass("errorTab")
}if(v.ribbon){a.updateRibbonState(v.ribbon)
}};
this.setComponentState=function(t){};
this.setMessages=function(t){};
this.setComponentEnabled=function(t){};
this.setComponentLoading=function(){};
this.setHeader=function(u){var t=$("#"+this.elementPath+"_windowHeader");
if(t){t.html(u)
}};
this.updateSize=function(u,w){i=u;
e=w;
var y=!b.isPopup();
var t=$("#"+this.elementSearchName+"_windowContent");
var v=Math.round(u*0.02);
if(v<20&&y){v=20
}var D=u-v;
width=Math.round(u-2*v);
var B=d.innerWidth();
if(B!=$(window).width()){width-=15
}if(width<960&&y){width=960;
t.css("marginLeft",v+"px");
t.css("marginRight",v+"px")
}else{t.css("marginLeft","auto");
t.css("marginRight","auto")
}t.width(width);
t.css("marginTop",v+"px");
if(!this.options.fixedHeight){t.css("marginBottom",v+"px")
}height=null;
if(this.options.fixedHeight){var C=Math.round(w-2*v-70);
height=C;
if(this.options.header){height-=24
}t.height(C)
}if(!r){var z=height?height-35:null;
for(var x in this.components){this.components[x].updateSize(width,z)
}}else{var z=height;
for(var x in this.components){this.components[x].updateSize(width,z)
}}if(this.options.hasRibbon){h.width(v);
f.width(B);
if(s){var A=width-g.width()-16;
s.width(A)
}}};
this.performBack=function(t){b.goBack();
if(t){t.performNext()
}};
this.performCloseWindow=function(t){b.closeWindow();
if(t){t.performNext()
}};
this.performComponentScript=function(){if(a){a.performScripts()
}};
this.getRibbonItem=function(t){return a.getRibbonItem(t)
};
p(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.WindowTab=function(a,f){$.extend(this,new QCD.components.Container(a,f));
var e=f;
var d;
var b;
function c(h){var g=$("#"+h.elementSearchName+" > div");
h.constructChildren(g.children());
if(h.options.ribbon){d=new QCD.components.Ribbon(h.options.ribbon,h.elementName,e,h.options.translations);
b=d.constructElementContent()
}if(h.options.referenceName){e.registerReferenceName(h.options.referenceName,h)
}}this.getRibbonElement=function(){return b
};
this.getRibbonItem=function(g){return d.getRibbonItem(g)
};
this.performComponentScript=function(){if(d){d.performScripts()
}};
this.getComponentValue=function(){return{}
};
this.setComponentValue=function(g){};
this.setComponentState=function(g){};
this.setMessages=function(g){};
this.setComponentEnabled=function(g){};
this.setComponentLoading=function(){};
this.setVisible=function(g){};
this.updateSize=function(g,k){var h=k?k-20:null;
for(var j in this.components){this.components[j].updateSize(g-20,h)
}};
c(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.AwesomeDynamicList=function(r,u){$.extend(this,new QCD.components.Container(r,u));
var c=u;
var a=this.elementPath;
var n=this.elementSearchName;
var m;
var f;
var h;
var A;
var x;
var y=1;
var k;
var d;
var s=new Array();
var o;
var b=70;
var z=this.options.hasButtons;
var j=true;
var g=false;
var q=new Object();
var e=false;
function w(B){m=$("#"+B.elementSearchName+" > .awesomeDynamicList > .awesomeDynamicListInnerForm").children();
f=$("#"+B.elementSearchName+" > .awesomeDynamicList > .awesomeDynamicListContent");
h=$("#"+B.elementSearchName+" > .awesomeDynamicList > .awesomeDynamicListHeader");
if(h&&h.length>0){A=QCDPageConstructor.getChildrenComponents(h.children(),c)["header"];
A.setEnabled(true,true)
}x=new Array();
formObjectsMap=new Object();
if(!z){b=0
}B.components=q;
p()
}this.getComponentValue=function(){var C=new Array();
for(var B in x){if(!x[B]){continue
}C.push({name:x[B].elementName,value:x[B].getValue()})
}return{forms:C}
};
this.setComponentValue=function(F){var B=F.forms;
if(F.required!=undefined){e=F.required
}if(B){x=new Array();
f.empty();
this.components=new Object();
q=this.components;
y=1;
for(var C in B){var E=B[C];
var G=i(y);
G.setValue(E);
x[y]=G;
this.components[G.elementName]=G;
y++
}if(e&&y==1){var G=i(y,true);
x[y]=G;
this.components[G.elementName]=G;
y++
}p()
}else{var D=F.innerFormChanges;
for(var C in D){this.components[C].setValue(D[C])
}}c.updateSize()
};
this.setComponentState=function(B){this.setComponentValue(B)
};
this.setComponentEnabled=function(C){j=C;
if(C){for(var B in s){if(s[B]){s[B].addClass("enabled")
}}}else{for(var B in s){if(s[B]){s[B].removeClass("enabled")
}}}};
this.isComponentChanged=function(){if(g){return true
}for(var B in x){if(x[B]&&!x[B].isVirtual&&x[B].isChanged()){return true
}}return false
};
this.performUpdateState=function(){g=false
};
this.setComponentLoading=function(B){};
this.updateSize=function(B,D){k=B;
d=D;
for(var C in x){if(x[C]){x[C].updateSize(B-b,D)
}}if(A){h.width(B-b-20);
A.updateSize(B-b-30,D)
}};
function i(G,C){C=C?C:false;
var B=m.clone();
v(B,G);
var J=$("<div>").addClass("awesomeListLine").attr("id",a+"_line_"+G);
var F=$("<div>").addClass("awesomeListFormContainer");
F.append(B);
J.append(F);
if(z){var E=$("<div>").addClass("awesomeListButtons");
s.push(I);
var I=$("<a>").addClass("awesomeListButton").addClass("awesomeListMinusButton").addClass("enabled").attr("id",a+"_line_"+G+"_removeButton");
I.css("display","none");
I.click(function(M){var L=$(M.target);
if(L.hasClass("enabled")){var K=L.attr("id").substring(a.length+6,L.attr("id").length-13);
t(K)
}});
E.append(I);
var D=$("<a>").addClass("awesomeListButton").addClass("awesomeListPlusButton").addClass("enabled").attr("id",a+"_line_"+G+"_addButton");
D.click(function(M){var L=$(M.target);
if(L.hasClass("enabled")){var K=L.attr("id").substring(a.length+6,L.attr("id").length-10);
l(K)
}});
D.css("display","none");
s.push(D);
E.append(D);
J.append(E)
}f.append(J);
var H=QCDPageConstructor.getChildrenComponents(B,c)["innerForm_"+G];
H.isVirtual=C;
H.updateSize(k-b,d);
H.setEnabled(true,true);
return H
}function l(B){var C=i(y);
x[y]=C;
q[C.elementName]=C;
g=true;
y++;
p();
c.updateSize()
}function t(C){var B=$("#"+n+"_line_"+C);
B.remove();
g=true;
x[C]=null;
p();
c.updateSize()
}function p(){if(!z){return
}var H=0;
var I=0;
for(var D in x){if(x[D]){H++;
I=D
}}if(H>0){if(o){o.hide();
o=null
}for(var D in x){if(!x[D]){continue
}var B=$("#"+n+"_line_"+D);
var G=$("#"+n+"_line_"+D+"_removeButton");
var C=$("#"+n+"_line_"+D+"_addButton");
if(!(e&&H<=1)){G.show();
G.css("display","inline-block")
}else{G.hide()
}if(D==I){C.show();
C.css("display","inline-block");
B.addClass("lastLine")
}else{C.hide();
B.removeClass("lastLine")
}}}else{o=$("<div>").addClass("awesomeListLine").addClass("lastLine").attr("id",a+"_line_0");
var F=$("<div>").addClass("awesomeListButtons");
var E=$("<a>").addClass("awesomeListButton").addClass("awesomeListPlusButton").attr("id",a+"_line_0_addButton");
E.click(function(L){var K=$(L.target);
if(K.hasClass("enabled")){var J=K.attr("id").substring(a.length+6,K.attr("id").length-10);
l(J)
}});
F.append(E);
s.push(E);
if(j){E.addClass("enabled")
}o.append(F);
f.append(o)
}}function v(B,C){var D=B.attr("id");
if(D){B.attr("id",D.replace("@innerFormId",C))
}B.children().each(function(F,G){var E=$(G);
v(E,C)
})
}w(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.Calendar=function(n,p){$.extend(this,new QCD.components.elements.FormComponent(n,p));
var e=200;
var c=n;
var m=$("#"+this.elementSearchName+"_calendar");
var l=this.input;
var i;
var f;
var d=this.element;
var a=this.elementPath;
var t=false;
var g=false;
var r=false;
var k=(this.options.listeners.length>0)?true:false;
var o=this.fireOnChangeListeners;
var q=this.addMessage;
var h=false;
if(this.options.referenceName){p.registerReferenceName(this.options.referenceName,this)
}var s=function(u){options=$.datepicker.regional[locale];
if(!options){options=$.datepicker.regional[""]
}options.changeMonth=true;
options.changeYear=true;
options.showOn="button";
options.dateFormat="yy-mm-dd";
options.showAnim="show";
options.altField=l;
options.onClose=function(w,v){t=false;
if(r){g=true
}};
options.onSelect=function(w,v){f.slideUp(e);
t=false;
b()
};
f=$("<div>").css("position","absolute").css("zIndex",100).css("right","15px");
c.css("position","relative");
f.hide();
$("#ui-datepicker-div").hide();
c.append(f);
f.datepicker(options);
l.val("");
$(document).mousedown(function(v){if(!t){return
}var w=$(v.target);
if(w.attr("id")!=l.attr("id")&&w.attr("id")!=m.attr("id")&&w.parents(".ui-datepicker").length==0){f.slideUp(e);
t=false
}});
m.hover(function(){r=true
},function(){r=false
});
m.click(function(){if(m.hasClass("enabled")){if(g){g=false;
return
}if(!t){if(l.val()){try{$.datepicker.parseDate("yy-mm-dd",l.val());
f.datepicker("setDate",l.val())
}catch(y){}}var x=l.offset().top;
var z=f.outerHeight();
var w=l.outerHeight()+10;
var v=document.documentElement.clientHeight+$(document).scrollTop();
if((x+z+w)>v){f.css("top","");
f.css("bottom",w+"px");
isOnTop=true
}else{f.css("top",w+"px");
f.css("bottom","");
isOnTop=false
}f.slideDown(e).show();
t=true
}else{f.slideUp(e);
t=false
}}});
l.focus(function(){m.addClass("lightHover")
}).blur(function(){m.removeClass("lightHover")
});
l.change(function(){b()
})
};
function b(){var u=j();
if(!h){if(u==null){q({title:"",content:"not ok"});
d.addClass("error")
}else{d.removeClass("error")
}}o("onChange",[u]);
if(k){mainController.callEvent("onChange",a,null,null,null)
}}this.setComponentError=function(u){h=u;
if(u){d.addClass("error")
}else{d.removeClass("error")
}};
this.setFormComponentEnabled=function(u){if(u){m.addClass("enabled");
l.datepicker("enable")
}else{m.removeClass("enabled");
l.datepicker("disable")
}};
this.updateSize=function(v,w){var u=w?w-10:40;
this.input.parent().parent().parent().parent().parent().height(u)
};
function j(){var u=l.val();
if($.trim(u)==""){return 0
}var w=u.split("-");
if(w.length!=3||w[0].length!=4||w[1].length!=2||w[2].length!=2){return null
}try{return $.datepicker.parseDate("yy-mm-dd",u)
}catch(v){return null
}}this.getDate=j;
this.setDate=function(u){var v=$.datepicker.formatDate("yy-mm-dd",u);
l.val(v);
b()
};
s(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.CheckBox=function(a,g){$.extend(this,new QCD.components.elements.FormComponent(a,g));
var f=g;
var e=$("#"+this.elementSearchName+"_text");
var d;
var c=this.element;
var b=this.options.translations;
if(this.options.referenceName){f.registerReferenceName(this.options.referenceName,this)
}this.getComponentData=function(){if(this.input.attr("checked")){return{value:"1"}
}return{value:"0"}
};
this.setComponentData=function(h){if(h!=null&&(h.value==1||h.value=="true")){this.input.attr("checked",true);
e.html(b["true"])
}else{this.input.attr("checked",false);
e.html(b["false"])
}};
this.setFormComponentEnabled=function(h){if(this.options.textRepresentationOnDisabled){if(h){this.input.show();
e.hide()
}else{this.input.hide();
e.show()
}}};
this.setComponentEnabled=function(h){if(h){e.removeClass("disabled");
this.input.removeAttr("disabled");
c.removeClass("disabled")
}else{e.addClass("disabled");
this.input.attr("disabled","true");
c.addClass("disabled")
}if(this.setFormComponentEnabled){this.setFormComponentEnabled(h)
}};
this.setSelected=function(i,h){this.input.attr("checked",h);
if(i){i.performNext()
}};
this.setCurrentValue=function(h){d=this.input.attr("checked")
};
this.updateSize=function(i,j){var h=j?j-10:40;
this.input.parent().parent().height(h)
}
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.DynamicComboBox=function(c,k){$.extend(this,new QCD.components.elements.FormComponent(c,k));
var d=c;
var l=k;
var i=this.elementPath;
var g=this.input;
var h=new Array();
var f=(this.options.listeners.length>0)?true:false;
if(this.options.referenceName){l.registerReferenceName(this.options.referenceName,this)
}function b(m){g.change(function(){a();
e()
})
}function e(){if(f){l.callEvent("onSelectedEntityChange",i,null,null,null)
}}function a(){title=g.find(":selected").text();
value=g.val();
if(title&&value){g.attr("title",title)
}else{g.removeAttr("title")
}}this.getComponentData=function(){var m=this.input.val();
return{value:m,values:h}
};
this.setComponentData=function(m){j(m)
};
function j(o){if(o.values){h=o.values;
g.children().remove();
for(var m in o.values){var n=o.values[m];
g.append("<option value='"+n.key+"'>"+n.value+"</option>")
}}g.val(o.value);
a()
}this.setComponentEnabled=function(m){if(m){d.removeClass("disabled");
this.input.removeAttr("disabled")
}else{d.addClass("disabled");
this.input.attr("disabled","true")
}if(this.setFormComponentEnabled){this.setFormComponentEnabled(m)
}};
this.updateSize=function(n,o){var m=o?o-10:40;
this.input.parent().parent().height(m)
};
b(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.EntityComboBox=function(a,h){$.extend(this,new QCD.components.elements.FormComponent(a,h));
var g=h;
var d=this.options;
var f=this.elementPath;
var c=this.input;
if(this.options.referenceName){g.registerReferenceName(this.options.referenceName,this)
}function e(i){i.input.change(b)
}function b(){if(d.listeners.length>0){g.getUpdate(f,c.val(),d.listeners)
}}this.getComponentData=function(){var i=this.input.val();
if(!i||$.trim(i)==""){i=null
}return{value:i}
};
this.setComponentData=function(n){var m=this.input.val();
if(n.values!=null){this.input.children().remove();
var j=g.getPluginIdentifier()+"."+g.getViewName()+"."+f.replace(/-/g,".")+".blankValue";
this.input.append("<option value=''>"+g.getTranslation(j)+"</option>");
for(var k in n.values){var l=n.values[k];
this.input.append("<option value='"+k+"'>"+l+"</option>")
}}selected=n.value;
if(selected!=null){this.input.val(selected)
}else{this.input.val(m)
}};
e(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.FormComponent=function(c,j){$.extend(this,new QCD.components.Component(c,j));
var k=j;
var e=c;
var d=$("#"+this.elementSearchName+"_error_icon");
var l=$("#"+this.elementSearchName+"_error_messages");
var f=$("#"+this.elementSearchName+"_description_icon");
var i=$("#"+this.elementSearchName+"_description_message");
var a;
this.input=$("#"+this.elementSearchName+"_input");
var h=this.input;
function b(m){m.registerCallbacks()
}this.registerCallbacks=function(){f.hover(function(){i.show()
},function(){i.hide()
});
d.hover(function(){l.show()
},function(){l.hide()
})
};
this.getComponentData=function(){return{value:this.input.val(),}
};
this.setComponentData=function(m){if(m.value){this.input.val(m.value)
}else{this.input.val("")
}};
this.getComponentValue=function(){var m=this.getComponentData();
m.required=e.hasClass("required");
m.baseValue=a;
return m
};
this.setComponentValue=function(m){this.setComponentData(m);
g(m.required)
};
this.setComponentState=function(m){this.setComponentData(m);
g(m.required);
this.setComponentBaseValue(m)
};
this.setComponentBaseValue=function(m){if(m.baseValue!=undefined){a=m.baseValue
}};
this.performUpdateState=function(){a=this.getComponentData().value
};
this.isComponentChanged=function(){if(!(a==this.getComponentData().value)){a
}return !(a==this.getComponentData().value)
};
this.setComponentEnabled=function(m){if(m){e.removeClass("disabled");
this.input.removeAttr("readonly")
}else{e.addClass("disabled");
this.input.attr("readonly","readonly")
}if(this.setFormComponentEnabled){this.setFormComponentEnabled(m)
}};
function g(m){if(m){e.addClass("required")
}else{e.removeClass("required")
}}this.setMessages=function(n){l.html("");
for(var m in n){this.addMessage(n[m])
}if(n){this.setComponentError(n.length!=0)
}};
this.addMessage=function(p){messageDiv=$("<div>");
messageDiv.append("<span>"+p.title+"</span>");
messageDiv.append("<p>"+p.content+"</p>");
l.append(messageDiv);
var q=h.offset().top;
var n=l.height();
var o=h.outerHeight()-1;
var m=document.documentElement.clientHeight+$(document).scrollTop();
if((q+n+o)>m){l.css("top","");
l.css("bottom",d.outerHeight()+"px")
}else{l.css("top",d.outerHeight()+"px");
l.css("bottom","")
}};
this.setComponentError=function(m){if(m){e.addClass("error")
}else{e.removeClass("error")
}};
this.setComponentLoading=function(m){};
this.updateSize=function(n,o){var m=o?o-10:40;
this.input.parent().parent().parent().height(m)
};
b(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.Grid=function(K,r){$.extend(this,new QCD.components.Component(K,r));
var n=r;
var j=K;
var u=this.options;
var Y;
var c=this.elementPath;
var z=this.elementName;
var m=this.elementSearchName;
var w;
var M;
var g;
var h;
var a;
var U=false;
var o;
var R;
var v;
var t={selectedEntityId:null,selectedEntities:new Object(),filtersEnabled:false,newButtonClickedBefore:false,multiselectMode:true,isEditable:true};
var f=true;
var k=new Object();
var s=new Object();
var P={paging:true,fullScreen:false,shrinkToFit:false};
var T={};
var J;
var e;
var W=200;
var Z=null;
var F=this.fireOnChangeListeners;
var E;
if(this.options.referenceName){n.registerReferenceName(this.options.referenceName,this)
}function H(ao){w=new Object();
var an=new Array();
var ak=new Array();
var aj=false;
for(var ae in ao.columns){var ad=ao.columns[ae];
k[ad.name]=ad;
var aa=false;
var al=false;
for(var ac in ao.orderableColumns){if(ao.orderableColumns[ac]==ad.name){aa=true;
break
}}for(var ac in ao.searchableColumns){if(ao.searchableColumns[ac]==ad.name){al=true;
aj=true;
break
}}ad.isSerchable=al;
if(!ad.hidden){if(aa){an.push(ad.label+"<div class='sortArrow' id='"+c+"_sortArrow_"+ad.name+"'></div>")
}else{an.push(ad.label)
}var ah="text";
var am={};
var ag=new Object();
if(ad.filterValues){ag[""]="";
var af=":";
for(var ae in ad.filterValues){ag[ae]=ad.filterValues[ae];
af+=";"+ae+":"+ad.filterValues[ae]
}ah="select";
am.value=af;
am.defaultValue=""
}var ab={name:ad.name,index:ad.name,width:ad.width,sortable:aa,resizable:true,align:ad.align,stype:ah,searchoptions:am};
if(am.value){T[ad.name]=ag;
ab.formatter=function(ap,aq,ar){return T[aq.colModel.name][ap]
}
}ak.push(ab)
}else{s[ad.name]=new Object()
}}w.hasPredefinedFilters=ao.hasPredefinedFilters;
w.predefinedFilters=ao.predefinedFilters;
w.sortColumns=ao.orderableColumns;
w.colNames=an;
w.colModel=ak;
w.datatype=function(ap){};
w.multiselect=true;
w.shrinkToFit=true;
w.listeners=ao.listeners;
w.canNew=ao.creatable;
w.canDelete=ao.deletable;
w.paging=ao.paginable;
w.filter=aj;
w.orderable=ao.prioritizable;
w.allowMultiselect=ao.multiselect;
w.fullScreen=ao.fullscreen;
if(ao.height){w.height=parseInt(ao.height);
if(w.height<=0){w.height=null
}}if(ao.width){w.width=parseInt(ao.width)
}if(!w.width&&!w.fullScreen){w.width=300
}w.correspondingViewName=ao.correspondingView;
w.correspondingComponent=ao.correspondingComponent;
w.correspondingViewInModal=ao.correspondingViewInModal;
for(var ai in P){if(w[ai]==undefined){w[ai]=P[ai]
}}}function G(ac,aa){if(!U||!t.isEditable){M.setSelection(ac,false);
return
}if(t.selectedEntities[ac]){if(aa==0&&t.multiselectMode){t.selectedEntities[ac]=null
}else{if(t.multiselectMode){for(var ab in t.selectedEntities){if(t.selectedEntities[ab]){M.setSelection(ab,false);
t.selectedEntities[ab]=null
}}t.selectedEntities[ac]=true
}else{t.selectedEntities[ac]=null
}}}else{if(aa==0&&w.allowMultiselect){}else{for(var ab in t.selectedEntities){if(t.selectedEntities[ab]){M.setSelection(ab,false);
t.selectedEntities[ab]=null
}}}t.selectedEntities[ac]=true
}x();
if(w.listeners.length>0){S()
}}function x(){var ag=0;
var ab=null;
var ad=new Array();
var aa=new Array();
for(var ac in t.selectedEntities){if(t.selectedEntities[ac]){ag++;
ab=ac;
ad.push(ac);
aa.push(J[ac])
}}if(ag==0){t.multiselectMode=false;
t.selectedEntityId=null
}else{if(ag==1){t.multiselectMode=false;
t.selectedEntityId=ab
}else{t.multiselectMode=true;
t.selectedEntityId=null
}}if(t.multiselectMode){j.addClass("multiselectMode")
}else{j.removeClass("multiselectMode")
}if(v){var ae=true;
var af=true;
for(var ac in J){af=false;
if(t.selectedEntities[ac]!=true){ae=false;
break
}}if(af){ae=false
}if(ae){v.attr("checked",true);
v.attr("title",a.diselectAll)
}else{v.attr("checked",false);
v.attr("title",a.selectAll)
}}if(t.multiselectMode){Y.onSelectionChange(true)
}else{var ah=null;
if(t.selectedEntityId){ah=M.jqGrid("getInd",t.selectedEntityId);
if(ah==false){ah=null
}}Y.onSelectionChange(false,ah)
}F("onChange",[aa])
}this.setLinkListener=function(aa){R=aa
};
function D(aa){if(!U||!t.isEditable){return
}if(R){R.onGridLinkClicked(aa)
}else{var ab={};
ab[w.correspondingComponent+".id"]=aa;
X(ab)
}}function X(ab){if(w.correspondingViewName&&w.correspondingViewName!=""){ab[w.correspondingComponent+"."+g]=t.belongsToEntityId;
var aa=w.correspondingViewName+".html?context="+JSON.stringify(ab);
if(w.correspondingViewInModal){n.openModal(c+"_editWindow",aa)
}else{n.goToPage(aa)
}}}this.getComponentValue=function(){return t
};
this.setComponentState=function(ad){t.selectedEntityId=ad.selectedEntityId;
t.selectedEntities=ad.selectedEntities;
t.multiselectMode=ad.multiselectMode;
if(ad.belongsToEntityId){t.belongsToEntityId=ad.belongsToEntityId
}else{t.belongsToEntityId=null
}if(ad.firstEntity){t.firstEntity=ad.firstEntity
}if(ad.maxEntities){t.maxEntities=ad.maxEntities
}if(ad.filtersEnabled){t.filtersEnabled=ad.filtersEnabled;
Y.setFilterActive();
M[0].toggleToolbar();
p();
if(t.filtersEnabled){o-=21
}else{o+=21
}M.setGridHeight(o)
}if(ad.order){N(ad.order)
}if(ad.filters){t.filters=ad.filters;
for(var ac in t.filters){$("#gs_"+ac).val(t.filters[ac])
}I()
}if(ad.newButtonClickedBefore){var ab=n.getLastPageController();
if(ab&&ab.getViewName()==w.correspondingViewName){var aa=ab.getComponentByReferenceName(w.correspondingComponent);
E=aa.getComponentValue().entityId
}}};
this.setComponentValue=function(af){t.selectedEntityId=af.selectedEntityId;
if(af.belongsToEntityId){t.belongsToEntityId=af.belongsToEntityId
}else{t.belongsToEntityId=null
}if(af.firstEntity){t.firstEntity=af.firstEntity
}if(af.maxEntities){t.maxEntities=af.maxEntities
}if(af.entities==null){return
}M.jqGrid("clearGridData");
var ad=1;
J=new Object();
for(var ac in af.entities){var ab=af.entities[ac];
J[ab.id]=ab;
var aa=new Object();
for(var ah in k){if(s[ah]){s[ah][ab.id]=ab.fields[ah]
}else{if(k[ah].link&&ab.fields[ah]&&ab.fields[ah]!=""){aa[ah]="<a href=# id='"+c+"_"+ah+"_"+ab.id+"' class='"+c+"_link gridLink'>"+ab.fields[ah]+"</a>"
}else{if(ab.fields[ah]&&ab.fields[ah]!=""){aa[ah]=ab.fields[ah]
}else{aa[ah]=""
}}}}M.jqGrid("addRowData",ab.id,aa);
if(ad%2==0){M.jqGrid("setRowData",ab.id,false,"darkRow")
}else{M.jqGrid("setRowData",ab.id,false,"lightRow")
}ad++
}if(ad==1){e.show()
}else{e.hide()
}$("."+m+"_link").click(function(ak){var aj=ak.target.id.split("_");
var ai=aj[aj.length-1];
D(ai)
});
Y.updatePagingParameters(t.firstEntity,t.maxEntities,af.totalEntities);
t.selectedEntities=af.selectedEntities;
for(var ae in t.selectedEntities){if(t.selectedEntities[ae]){M.setSelection(ae,false)
}}x();
if(af.order){N(af.order)
}if(af.entitiesToMarkAsNew){for(var ae in af.entitiesToMarkAsNew){var ag=$("#"+m+" #"+ae);
if(ag){ag.addClass("lastAdded")
}}}if(E){var ag=$("#"+m+" #"+E);
if(ag){ag.addClass("lastAdded");
E=null
}}if(af.isEditable!=undefined&&af.isEditable!=null){this.setComponentEditable(af.isEditable)
}Q()
};
this.setComponentEnabled=function(aa){U=aa;
Y.setEnabled(t.isEditable&&aa)
};
this.setComponentLoading=function(aa){if(aa){V()
}else{Q()
}};
this.setComponentEditable=function(aa){t.isEditable=aa;
if(t.isEditable){M.removeClass("componentNotEditable")
}else{M.addClass("componentNotEditable")
}Y.setEnabled(t.isEditable&&U)
};
function V(){QCD.components.elements.utils.LoadingIndicator.blockElement(j)
}function Q(){QCD.components.elements.utils.LoadingIndicator.unblockElement(j)
}function d(ab){H(ab.options,ab);
w.modifiedPath=c.replace(/\./g,"_");
w.element=w.modifiedPath+"_grid";
$("#"+m+"_grid").attr("id",w.element);
a=ab.options.translations;
g=ab.options.belongsToFieldName;
Y=new QCD.components.elements.grid.GridHeaderController(ab,n,w,ab.options.translations);
$("#"+m+"_gridHeader").append(Y.getHeaderElement());
$("#"+m+"_gridFooter").append(Y.getFooterElement());
t.firstEntity=Y.getPagingParameters()[0];
t.maxEntities=Y.getPagingParameters()[1];
w.onCellSelect=function(af,ac,ad,ae){G(af,ac)
};
w.ondblClickRow=function(ac){D(ac)
};
w.onSortCol=C;
M=$("#"+w.element).jqGrid(w);
$("#cb_"+w.element).hide();
if(w.allowMultiselect){v=$("<input type='checkbox'>");
$("#"+m+" #jqgh_cb").append(v);
v.change(function(){O()
})
}for(var aa in w.sortColumns){$("#"+w.modifiedPath+"_grid_"+w.sortColumns[aa]).addClass("sortableColumn")
}j.width("100%");
M.jqGrid("filterToolbar",{stringResult:true});
if(w.isLookup){Y.setFilterActive();
t.filtersEnabled=true;
$("#gs_"+u.columns[0].name).focus()
}else{M[0].toggleToolbar();
t.filtersEnabled=false
}e=$("<div>").html(a.noResults).addClass("noRecordsBox");
e.hide();
$("#"+w.element).parent().append(e)
}this.onPagingParametersChange=function(){V();
t.firstEntity=Y.getPagingParameters()[0];
t.maxEntities=Y.getPagingParameters()[1];
L()
};
function N(aa){if(h&&h.column==aa.column){if(aa.direction=="asc"){$("#"+m+"_sortArrow_"+aa.column).removeClass("downArrow");
$("#"+m+"_sortArrow_"+aa.column).addClass("upArrow");
t.order.direction="asc"
}else{$("#"+m+"_sortArrow_"+aa.column).removeClass("upArrow");
$("#"+m+"_sortArrow_"+aa.column).addClass("downArrow");
t.order.direction="desc"
}}else{if(h){$("#"+w.modifiedPath+"_grid_"+h.column).removeClass("sortColumn")
}$("#"+w.modifiedPath+"_grid_"+aa.column).addClass("sortColumn");
t.order={column:aa.column};
if(aa.direction=="asc"){$("#"+m+"_sortArrow_"+aa.column).addClass("upArrow");
t.order.direction="asc"
}else{$("#"+m+"_sortArrow_"+aa.column).addClass("downArrow");
t.order.direction="desc"
}}h={column:aa.column,direction:aa.direction}
}function O(){if(v.is(":checked")){for(var aa in J){if(t.selectedEntities[aa]!=true){M.setSelection(aa,false);
t.selectedEntities[aa]=true
}}}else{for(var aa in t.selectedEntities){if(t.selectedEntities[aa]){M.setSelection(aa,false);
t.selectedEntities[aa]=null
}}}x();
if(w.listeners.length>0){S()
}}function C(ac,ab,aa){V();
t.order.column=ac;
if(t.order.direction=="asc"){t.order.direction="desc"
}else{t.order.direction="asc"
}L();
return"stop"
}function i(){if(Z){window.clearTimeout(Z);
Z=null
}Z=window.setTimeout(function(){Z=null;
y()
},W)
}function y(){V();
if(t.filtersEnabled){t.filters=new Object();
for(var aa in k){var ab=k[aa];
if(ab.isSerchable){var ac=$("#gs_"+ab.name).val();
ac=$.trim(ac);
if(ac&&ac!=""){t.filters[ab.name]=ac
}}}}else{t.filters=null
}L();
l()
}this.onFilterButtonClicked=function(){M[0].toggleToolbar();
t.filtersEnabled=!t.filtersEnabled;
if(t.filtersEnabled){o-=23;
p();
$("#gs_"+u.columns[0].name).focus()
}else{o+=23
}M.setGridHeight(o);
L();
l()
};
this.onClearFilterClicked=function(){t.filters=new Object();
for(var aa in k){var ab=k[aa];
$("#gs_"+ab.name).val("")
}l();
L()
};
function p(){for(var ab in k){var ac=k[ab];
if(ac.isSerchable){var aa=$("#gs_"+ac.name);
aa.unbind("change keyup");
if(ac.filterValues){aa.change(i)
}else{aa.keyup(function(af){var ag=$(this).val();
var ad=$(this).attr("id").substring(3);
var ae="";
if(t.filters&&t.filters[ad]){ae=t.filters[ad]
}if(t.filters&&ag==ae){return
}i()
})
}}else{$("#gs_"+ac.name).hide()
}}}this.setFilterState=function(aa,ab){if(!t.filtersEnabled){M[0].toggleToolbar();
t.filtersEnabled=true;
Y.setFilterActive();
o-=21;
M.setGridHeight(o)
}t.filters=new Object();
t.filters[aa]=ab;
$("#gs_"+aa).val(ab);
$("#gs_"+aa).focus();
p();
l()
};
this.setFilterObject=function(ae){V();
var af=ae.filter;
for(var ac in k){var ad=k[ac];
$("#gs_"+ad.name).val("")
}var ab=0;
for(var aa in af){af[aa]=Encoder.htmlDecode(af[aa]);
$("#gs_"+aa).val(af[aa]);
ab++
}t.filters=af;
if(ab==0){if(t.filtersEnabled){o+=23;
M.setGridHeight(o);
M[0].toggleToolbar()
}Y.setFilterNotActive();
t.filtersEnabled=false
}else{if(!t.filtersEnabled){o-=23;
M.setGridHeight(o);
M[0].toggleToolbar();
$("#gs_"+u.columns[0].name).focus()
}Y.setFilterActive();
t.filtersEnabled=true
}N({column:ae.orderColumn,direction:ae.orderDirection});
p();
l();
L(true)
};
this.onNewButtonClicked=function(){b()
};
this.onDeleteButtonClicked=function(){B()
};
this.onUpButtonClicked=function(){V();
n.callEvent("moveUp",c,function(){Q()
})
};
this.onDownButtonClicked=function(){V();
n.callEvent("moveDown",c,function(){Q()
})
};
this.updateSize=function(aa,ab){if(!aa){aa=300
}if(!ab){ab=300
}j.css("height",ab+"px");
var ac=120;
o=ab-ac;
if(t.filtersEnabled){o-=21
}if(!w.paging){o+=35
}M.setGridHeight(o);
M.setGridWidth(aa-24,f)
};
function l(){var aa=false;
for(var ab in t.filters){if(t.filters[ab]&&t.filters[ab]!=""){aa=true;
break
}}if(aa){Y.setFiltersValuesNotEmpty()
}else{Y.setFiltersValuesEmpty()
}}function L(aa){t.selectedEntities=null;
t.multiselectMode=false;
t.selectedEntityId=null;
if(!aa){I()
}if(U){n.callEvent("refresh",c,function(){Q()
})
}}function I(){var ae={};
if(t.filtersEnabled&&t.filters){ae=t.filters
}var af=true;
for(var ab in w.predefinedFilters){var ad=w.predefinedFilters[ab].filter;
af=true;
if(w.predefinedFilters[ab].orderColumn){if(t.order.column!=w.predefinedFilters[ab].orderColumn){af=false;
continue
}if(t.order.direction!=w.predefinedFilters[ab].orderDirection){af=false;
continue
}}for(var aa in k){var ac=k[aa];
if(ad[ac.name]!=ae[ac.name]){af=false;
break
}}if(af){Y.setPredefinedFilter(ab);
break
}}if(!af){Y.setPredefinedFilter(null)
}}function S(){if(U){n.callEvent("select",c,null)
}}this.performNew=function(aa){t.newButtonClickedBefore=true;
t.selectedEntities=null;
t.multiselectMode=false;
t.selectedEntityId=null;
X({});
if(aa){aa.performNext()
}};
var b=this.performNew;
function q(){var ab=0;
for(var aa in t.selectedEntities){if(t.selectedEntities[aa]){ab++
}}return ab
}this.performDelete=function(aa){if(t.selectedEntityId||q()>0){if(window.confirm(a.confirmDeleteMessage)){V();
n.callEvent("remove",c,function(){Q()
},null,aa)
}}else{n.showMessage({type:"error",content:a.noRowSelectedError})
}};
var B=this.performDelete;
this.performCopy=function(aa){if(t.selectedEntityId||q()>0){V();
n.callEvent("copy",c,function(){Q()
},null,aa)
}else{n.showMessage({type:"error",content:a.noRowSelectedError})
}};
var A=this.performCopy;
this.performEvent=function(aa,ab){this.fireEvent(null,aa,ab)
};
this.fireEvent=function(ac,aa,ab){V();
n.callEvent(aa,c,function(){Q()
},ab,ac)
};
this.performLinkClicked=function(aa){if(t.selectedEntityId){D(t.selectedEntityId);
if(aa){aa.performNext()
}}else{n.showMessage({type:"error",content:a.noRowSelectedError})
}};
this.getLookupData=function(ab){var aa=Object();
aa.entityId=ab;
aa.lookupValue=s.lookupValue[ab];
var ac=M.getRowData(ab).lookupCode;
ac=ac.replace(/^<a[^>]*>/,"");
ac=ac.replace(/<\/a>$/,"");
aa.lookupCode=ac;
return aa
};
d(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.grid=QCD.components.elements.grid||{};
QCD.components.elements.grid.GridHeaderController=function(j,r,c,p){var h=j;
var d=r;
var o=c;
var g=p;
var n=new Object();
n.first=null;
n.max=null;
n.totalNumberOfEntities=null;
var s;
var q;
var u=new Object();
u.filterButton=null;
u.predefiniedFiltersCombo=null;
u.predefiniedFiltersCustomOption_line1=$("<option>").attr("value",-1).html("--------------").css("display","none");
u.predefiniedFiltersCustomOption_line2=$("<option>").attr("value",-1).html(g.customPredefinedFilter).css("display","none");
u.newButton=null;
u.deleteButton=null;
u.upButton=null;
u.downButton=null;
var y=null;
var b=null;
var x;
var k=false;
var m=null;
var i=false;
function v(z){n.first=0;
n.max=30;
n.totalNumberOfEntities=0
}function a(){if(o.paging&&k){var A=Math.ceil(n.totalNumberOfEntities/n.max);
if(A==0){A=1
}var z=Math.ceil(n.first/n.max);
if(n.first%n.max==0){z+=1
}y.setPageData(z,A,n.max);
b.setPageData(z,A,n.max);
if(z>1){y.enablePreviousButtons();
b.enablePreviousButtons()
}else{y.disablePreviousButtons();
b.disablePreviousButtons()
}if(n.first+n.max<n.totalNumberOfEntities){y.enableNextButtons();
b.enableNextButtons()
}else{y.disableNextButtons();
b.disableNextButtons()
}y.enableRecordsNoSelect();
b.enableRecordsNoSelect();
y.enableInput();
b.enableInput()
}}this.paging_prev=function(){n.first-=n.max;
if(n.first<0){n.first=0
}w()
};
this.paging_next=function(){n.first+=n.max;
w()
};
this.paging_first=function(){n.first=0;
w()
};
this.paging_last=function(){if(n.totalNumberOfEntities%n.max>0){n.first=n.totalNumberOfEntities-n.totalNumberOfEntities%n.max
}else{n.first=n.totalNumberOfEntities-n.max
}w()
};
this.paging_onRecordsNoSelectChange=function(z){var A=z.val();
n.max=parseInt(A);
n.first=0;
w()
};
this.paging_setPageNo=function(B){var A=B.val();
if(!A||$.trim(A)==""){B.addClass("inputError");
return
}if(!/^\d*$/.test(A)){B.addClass("inputError");
return
}var z=parseInt(A);
if(z<=0){B.addClass("inputError");
return
}if(z>Math.ceil(n.totalNumberOfEntities/n.max)){B.addClass("inputError");
return
}n.first=n.max*(z-1);
w()
};
function w(){y.hideInputError();
b.hideInputError();
h.onPagingParametersChange()
}this.getPagingParameters=function(){return[n.first,n.max]
};
this.updatePagingParameters=function(B,z,A){if(B>A){n.first=0;
h.onPagingParametersChange()
}else{n.first=B
}n.max=z;
n.totalNumberOfEntities=A;
x.html("("+n.totalNumberOfEntities+")");
a()
};
this.getHeaderElement=function(){s=$("<div>").addClass("grid_header").addClass("elementHeader").addClass("elementHeaderDisabled");
s.append($("<span>").html(g.header).addClass("grid_header_gridName").addClass("elementHeaderTitle"));
x=$("<span>").html("(0)").addClass("grid_header_totalNumberOfEntities").addClass("elementHeaderTitle");
s.append(x);
if(o.hasPredefinedFilters){var z=new Array();
for(var A in o.predefinedFilters){z[A]={value:A,label:g["filter."+o.predefinedFilters[A].label]}
}u.predefiniedFiltersCombo=QCD.components.elements.utils.HeaderUtils.createHeaderComboBox(z,function(B){if(B<0){return
}u.predefiniedFiltersCustomOption_line1.css("display","none");
u.predefiniedFiltersCustomOption_line2.css("display","none");
var C=o.predefinedFilters[B];
h.setFilterObject(C)
});
u.predefiniedFiltersCombo.append(u.predefiniedFiltersCustomOption_line1);
u.predefiniedFiltersCombo.append(u.predefiniedFiltersCustomOption_line2);
s.append(u.predefiniedFiltersCombo)
}if(o.filter){u.filterButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton(g.addFilterButton,function(B){if(u.filterButton.hasClass("headerButtonEnabled")){f()
}},"filterIcon16_dis.png");
u.clearFilterButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(B){if(u.clearFilterButton.hasClass("headerButtonEnabled")){t()
}},"clearIcon16_dis.png");
u.clearFilterButton.attr("title",g.clearFilterButton);
s.append(u.filterButton);
s.append(u.clearFilterButton);
e(u.filterButton,false);
u.clearFilterButton.hide()
}if(o.canNew){u.newButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton(g.newButton,function(B){if(u.newButton.hasClass("headerButtonEnabled")){h.onNewButtonClicked()
}},"newIcon16_dis.png");
s.append(u.newButton);
e(u.newButton,false)
}if(o.canDelete){u.deleteButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton(g.deleteButton,function(B){if(u.deleteButton.hasClass("headerButtonEnabled")){h.onDeleteButtonClicked()
}},"deleteIcon16_dis.png");
s.append(u.deleteButton);
e(u.deleteButton,false)
}if(o.orderable){u.upButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton(g.upButton,function(B){if(u.upButton.hasClass("headerButtonEnabled")){h.onUpButtonClicked()
}},"upIcon16_dis.png");
s.append(u.upButton);
e(u.upButton,false);
u.downButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton(g.downButton,function(B){if(u.downButton.hasClass("headerButtonEnabled")){h.onDownButtonClicked()
}},"downIcon16_dis.png");
s.append(u.downButton);
e(u.downButton,false)
}if(o.paging){y=new QCD.components.elements.grid.GridPagingElement(this,d,g);
s.append(y.getPagingElement(n))
}return s
};
this.getFooterElement=function(){if(!o.paging){return null
}b=new QCD.components.elements.grid.GridPagingElement(this,d,g);
q=$("<div>").addClass("grid_footer").append(b.getPagingElement(n));
return q
};
this.setEnabled=function(z){k=z;
if(k){s.removeClass("elementHeaderDisabled");
if(q){q.removeClass("elementHeaderDisabled")
}}else{s.addClass("elementHeaderDisabled");
if(q){q.addClass("elementHeaderDisabled")
}}l()
};
this.onSelectionChange=function(A,z){i=A;
m=z;
l()
};
function l(){if(!k){if(u.filterButton!=null){e(u.filterButton,false)
}if(u.predefiniedFiltersCombo!=null){u.predefiniedFiltersCombo.disable()
}if(u.newButton!=null){e(u.newButton,false)
}if(u.deleteButton!=null){e(u.deleteButton,false)
}if(u.upButton!=null){e(u.upButton,false)
}if(u.downButton!=null){e(u.downButton,false)
}if(o.paging){y.disablePreviousButtons();
b.disablePreviousButtons();
y.disableNextButtons();
b.disableNextButtons();
y.disableRecordsNoSelect();
b.disableRecordsNoSelect();
y.disableInput();
b.disableInput()
}}else{if(u.filterButton!=null){e(u.filterButton,true)
}if(u.predefiniedFiltersCombo!=null){u.predefiniedFiltersCombo.enable()
}if(u.newButton!=null){e(u.newButton,true)
}if(u.deleteButton!=null){if(i||m!=null){e(u.deleteButton,true)
}else{e(u.deleteButton,false)
}}if(o.paging){var z=Math.ceil(n.first/n.max)+1;
var A=Math.ceil(n.totalNumberOfEntities/n.max);
if(A==0){A=1
}}if(u.upButton!=null){if(i||m==1||m==null){e(u.upButton,false)
}else{e(u.upButton,true)
}}if(u.downButton!=null){if(i||m==n.totalNumberOfEntities||m==null){e(u.downButton,false)
}else{e(u.downButton,true)
}}}}function f(){if(u.filterButton.hasClass("headerButtonActive")){u.filterButton.removeClass("headerButtonActive");
u.filterButton.label.html(g.addFilterButton);
u.clearFilterButton.hide()
}else{u.filterButton.addClass("headerButtonActive");
u.filterButton.label.html(g.removeFilterButton);
u.clearFilterButton.css("display","inline-block")
}h.onFilterButtonClicked()
}function t(){h.onClearFilterClicked()
}this.setFiltersValuesEmpty=function(){e(u.clearFilterButton,false)
};
this.setFiltersValuesNotEmpty=function(){e(u.clearFilterButton,true)
};
this.setFilterActive=function(){u.filterButton.addClass("headerButtonActive");
u.filterButton.label.html(g.removeFilterButton);
u.clearFilterButton.css("display","inline-block")
};
this.setFilterNotActive=function(){u.filterButton.removeClass("headerButtonActive");
u.filterButton.label.html(g.addFilterButton);
u.clearFilterButton.hide()
};
this.setPredefinedFilter=function(z){if(z==null){u.predefiniedFiltersCustomOption_line1.css("display","");
u.predefiniedFiltersCustomOption_line2.css("display","");
u.predefiniedFiltersCombo.val(-1)
}else{u.predefiniedFiltersCustomOption_line1.css("display","none");
u.predefiniedFiltersCustomOption_line2.css("display","none");
u.predefiniedFiltersCombo.val(z)
}};
this.setEnabledButton=function(A,z){if(z){A.addClass("headerButtonEnabled")
}else{A.removeClass("headerButtonEnabled")
}};
var e=this.setEnabledButton;
v(this)
};
QCD.components.elements.grid.GridPagingElement=function(b,h,d){var a=b;
var g=h;
var c=d;
var e=new Object();
e.prevButton=null;
e.nextButton=null;
e.firstButton=null;
e.lastButton=null;
e.recordsNoSelect=null;
e.pageNo=null;
e.allPagesNoSpan=null;
function f(){}this.getPagingElement=function(k){var p=$("<div>").addClass("grid_paging");
var m=$("<span>").html(c.perPage).addClass("onPageSpan");
p.append(m);
e.recordsNoSelect=$("<select>").addClass("recordsNoSelect");
e.recordsNoSelect.append("<option value=10>10</option>");
e.recordsNoSelect.append("<option value=20>20</option>");
e.recordsNoSelect.append("<option value=30>30</option>");
e.recordsNoSelect.append("<option value=50>50</option>");
e.recordsNoSelect.append("<option value=100>100</option>");
e.recordsNoSelect.val(k.max);
p.append(e.recordsNoSelect);
e.firstButton=$("<div>").addClass("headerPagingButton").addClass("headerButton_first");
p.append(e.firstButton);
e.prevButton=$("<div>").addClass("headerPagingButton").addClass("headerButton_left");
p.append(e.prevButton);
var j=Math.ceil(k.totalNumberOfEntities/k.max);
if(j==0){j=1
}var q=Math.ceil(k.first/k.max)+1;
var l=$("<span>").addClass("grid_paging_pageInfo");
e.pageNo=$("<input type='text'></input>").addClass("pageInput");
var n=$("<div>").addClass("component_container_form_inner");
n.append('<div class="component_container_form_x"></div>');
n.append('<div class="component_container_form_y"></div>');
n.append(e.pageNo.val(q));
var o=$("<div>").addClass("component_container_form_w").append(n);
l.append(o);
var i=$("<span>").addClass("ofPagesSpan");
i.append("<span>").html(" "+c.outOfPages+" ");
e.allPagesNoSpan=$("<span>");
i.append(e.allPagesNoSpan.html(j));
l.append(i);
p.append(l);
e.nextButton=$("<div>").addClass("headerPagingButton").addClass("headerButton_right");
p.append(e.nextButton);
e.lastButton=$("<div>").addClass("headerPagingButton").addClass("headerButton_last");
p.append(e.lastButton);
e.firstButton.click(function(r){if($(r.target).hasClass("headerButtonEnabled")){a.paging_first()
}});
e.prevButton.click(function(r){if($(r.target).hasClass("headerButtonEnabled")){a.paging_prev()
}});
e.recordsNoSelect.change(function(r){a.paging_onRecordsNoSelectChange($(this))
});
e.pageNo.change(function(r){a.paging_setPageNo($(this))
});
e.nextButton.click(function(r){if($(r.target).hasClass("headerButtonEnabled")){a.paging_next()
}});
e.lastButton.click(function(r){if($(r.target).hasClass("headerButtonEnabled")){a.paging_last()
}});
a.setEnabledButton(e.prevButton,false);
a.setEnabledButton(e.firstButton,false);
a.setEnabledButton(e.nextButton,false);
a.setEnabledButton(e.lastButton,false);
return p
};
this.setPageData=function(j,k,i){e.allPagesNoSpan.html(k);
e.pageNo.val(j);
e.recordsNoSelect.val(i)
};
this.enablePreviousButtons=function(){a.setEnabledButton(e.prevButton,true);
a.setEnabledButton(e.firstButton,true)
};
this.disablePreviousButtons=function(){a.setEnabledButton(e.prevButton,false);
a.setEnabledButton(e.firstButton,false)
};
this.enableNextButtons=function(){a.setEnabledButton(e.nextButton,true);
a.setEnabledButton(e.lastButton,true)
};
this.disableNextButtons=function(){a.setEnabledButton(e.nextButton,false);
a.setEnabledButton(e.lastButton,false)
};
this.enableRecordsNoSelect=function(){e.recordsNoSelect.attr("disabled",false)
};
this.disableRecordsNoSelect=function(){e.recordsNoSelect.attr("disabled",true)
};
this.enableInput=function(){e.pageNo.attr("disabled",false)
};
this.disableInput=function(){e.pageNo.attr("disabled",true)
};
this.showInputError=function(){e.pageNo.addClass("inputError")
};
this.hideInputError=function(){e.pageNo.removeClass("inputError")
};
f()
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.containers=QCD.components.containers||{};
QCD.components.containers.layout=QCD.components.containers.layout||{};
QCD.components.elements.Label=function(a,b){$.extend(this,new QCD.components.Component(a,b));
if(this.options.referenceName){b.registerReferenceName(this.options.referenceName,this)
}this.setComponentState=function(c){};
this.getComponentValue=function(){return{}
};
this.setComponentValue=function(c){};
this.setComponentEnabled=function(c){};
this.setComponentLoading=function(c){}
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.LinkButton=function(d,k){$.extend(this,new QCD.components.Component(d,k));
var l=k;
var f=d;
var j=this.elementPath;
var m=this.elementName;
var c;
var i;
var g=$("#"+this.elementSearchName+"_buttonDiv");
var h=$("#"+this.elementSearchName+"_buttonLink");
if(this.options.referenceName){l.registerReferenceName(this.options.referenceName,this)
}this.getComponentValue=function(){return{value:{}}
};
this.setComponentValue=function(n){b(n)
};
this.setComponentState=function(n){b(n)
};
function b(n){c=n.value;
i=n.openInModal
}this.setComponentEnabled=function(n){if(n){g.addClass("activeButton")
}else{g.removeClass("activeButton")
}};
this.setComponentLoading=function(n){};
function e(n){h.blur();
if(g.hasClass("activeButton")){if(i){l.openModal(j,c)
}else{l.goToPage(c)
}}}function a(n){h.click(e)
}a(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.Lookup=function(q,s){$.extend(this,new QCD.components.elements.FormComponent(q,s));
var c=q;
var b=this.elementPath;
var i=this.options.translations;
var r=this.fireOnChangeListeners;
var A=100;
var d={UP:38,DOWN:40,ENTER:13,ESCAPE:27};
var p={input:this.input,text:$("#"+this.elementSearchName+"_text"),loading:$("#"+this.elementSearchName+"_loadingDiv"),label:$("#"+this.elementSearchName+"_labelDiv"),openLookupButton:$("#"+this.elementSearchName+"_openLookupButton"),lookupDropdown:$("#"+this.elementSearchName+"_lookupDropdown")};
var x={normal:p.label.html(),focus:"<span class='focusedLabel'>"+this.options.translations.labelOnFocus+"</span>"};
var k={isFocused:false,error:null};
var y={currentCode:null,selectedEntity:{id:null,value:null,code:null},autocomplete:{matches:null,code:null,entitiesNumber:null},contextEntityId:null};
var v=null;
var B=false;
var a=new QCD.components.elements.lookup.Dropdown(p.lookupDropdown,this,i);
var n=(this.options.listeners.length>0)?true:false;
var u=this;
var j;
var z;
if(this.options.referenceName){s.registerReferenceName(this.options.referenceName,this)
}function w(C){p.openLookupButton.click(m);
p.input.focus(function(){k.isFocused=true;
l()
}).blur(function(){k.isFocused=false;
l()
});
p.input.keyup(function(G){var F=o(G);
if(F==d.UP){if(!a.isOpen()){g(true)
}a.selectPrevious()
}else{if(F==d.DOWN){if(!a.isOpen()){g(true)
}a.selectNext()
}else{if(F==d.ENTER){if(!a.isOpen()){return
}var D=a.getSelected();
if(D==null){return
}f(D);
y.currentCode=y.selectedEntity.code;
p.input.val(y.currentCode);
a.hide()
}else{if(F==d.ESCAPE){t(G);
p.input.val(y.currentCode);
a.hide()
}else{var E=p.input.val();
if(y.currentCode!=E){y.currentCode=E;
f(null);
g()
}}}}}});
p.input.keydown(function(E){var D=o(E);
if(D==d.UP||D==d.ESCAPE){t(E);
return false
}}).keypress(function(E){var D=o(E);
if(D==d.UP||D==d.ESCAPE){t(E);
return false
}})
}this.getComponentData=function(){return{value:y.selectedEntity.id,selectedEntityValue:y.selectedEntity.value,selectedEntityCode:y.selectedEntity.code,currentCode:y.currentCode,autocompleteCode:y.autocomplete.code,contextEntityId:y.contextEntityId}
};
this.setComponentData=function(C){if(C.clearCurrentCodeCode){y.currentCode=""
}else{y.currentCode=C.currentCode?C.currentCode:y.currentCode
}y.selectedEntity.id=C.value?C.value:null;
y.selectedEntity.value=C.selectedEntityValue;
y.selectedEntity.code=C.selectedEntityCode;
y.autocomplete.matches=C.autocompleteMatches?C.autocompleteMatches:[];
y.autocomplete.code=C.autocompleteCode?C.autocompleteCode:"";
y.autocomplete.entitiesNumber=C.autocompleteEntitiesNumber;
if(y.contextEntityId!=C.contextEntityId){y.contextEntityId=C.contextEntityId;
y.currentCode=""
}if(!y.currentCode){y.currentCode=y.selectedEntity.id?y.selectedEntity.code:""
}h()
};
this.setComponentBaseValue=function(C){if(C.currentCode!=undefined){z={currentCode:C.currentCode}
}};
this.performUpdateState=function(){z={currentCode:y.currentCode}
};
this.isComponentChanged=function(){return !(y.currentCode==z.currentCode)
};
function l(){if(k.isFocused&&!p.input.attr("readonly")){p.openLookupButton.addClass("lightHover");
p.label.html(x.focus);
p.input.val(y.currentCode)
}else{p.openLookupButton.removeClass("lightHover");
a.hide();
if(v||p.loading.is(":visible")){B=true;
return
}k.error=null;
if(!y.selectedEntity.id&&!a.getSelected()&&!a.getMouseSelected()&&y.autocomplete.matches&&y.currentCode!=""){if(y.autocomplete.matches.length==0){k.error=i.noMatchError
}else{if(y.autocomplete.matches.length>1){k.error=i.moreTahnOneMatchError
}else{f(y.autocomplete.matches[0])
}}}if(k.error==null){p.label.html(x.normal);
if(y.selectedEntity.id){}else{if(a.getMouseSelected()){f(a.getMouseSelected());
y.currentCode=a.getMouseSelected().code
}else{if(a.getSelected()){f(a.getSelected());
y.currentCode=a.getSelected().code
}}}p.input.val(e(y.selectedEntity.value));
p.text.html(y.selectedEntity.value)
}else{u.addMessage({title:"",content:k.error});
c.addClass("error")
}}}function h(){if(y.autocomplete.code==y.currentCode){p.loading.hide()
}if(B){B=false;
k.isFocused=false;
a.updateAutocomplete(y.autocomplete.matches,y.autocomplete.entitiesNumber);
l();
return
}if(k.isFocused){a.updateAutocomplete(y.autocomplete.matches,y.autocomplete.entitiesNumber);
a.show()
}else{if(y.selectedEntity.value){p.input.val(e(y.selectedEntity.value))
}else{p.input.val(y.currentCode)
}p.text.html(y.selectedEntity.value)
}}function g(C){if(v){window.clearTimeout(v);
v=null
}if(C){p.loading.show();
mainController.callEvent("autompleteSearch",b,null,null,null)
}else{v=window.setTimeout(function(){v=null;
p.loading.show();
mainController.callEvent("autompleteSearch",b,null,null,null)
},A)
}}function f(C,D){if(D==undefined){D=true
}r("onChange",[C]);
if(C){y.selectedEntity.id=C.id;
y.selectedEntity.code=C.code;
y.selectedEntity.value=C.value
}else{y.selectedEntity.id=null;
y.selectedEntity.code=null;
y.selectedEntity.value=null
}if(n&&D){mainController.callEvent("onSelectedEntityChange",b,null,null,null)
}}function e(D){if(!D||D==""){return""
}var C=/<\S[^><]*>/g;
return D.replace(C,"")
}this.updateSize=function(D,E){var C=E?E-10:40;
this.input.parent().parent().parent().parent().parent().height(C)
};
function t(C){C.preventDefault();
C.stopImmediatePropagation();
C.stopPropagation();
C.keyCode=0;
C.which=0;
C.returnValue=false
}function o(C){return C.keyCode||C.which
}this.setFormComponentEnabled=function(C){if(C){p.openLookupButton.addClass("enabled")
}else{p.openLookupButton.removeClass("enabled")
}};
function m(){if(!p.openLookupButton.hasClass("enabled")){return
}var C=u.options.viewName+".html";
if(y.contextEntityId){var D=new Object();
D["window.grid.belongsToEntityId"]=y.contextEntityId;
C+="?context="+JSON.stringify(D)
}j=mainController.openPopup(C,u,"lookup")
}this.onPopupInit=function(){var C=j.getComponent("window.grid");
C.setLinkListener(this);
if(y.currentCode){C.setFilterState("lookupCode",y.currentCode)
}j.init()
};
this.onPopupClose=function(){j=null
};
this.onGridLinkClicked=function(C){var D=j.getComponent("window.grid");
var E=D.getLookupData(C);
f({id:E.entityId,code:E.lookupCode,value:E.lookupValue});
y.currentCode=E.lookupCode;
h();
l();
if(n){mainController.callEvent("onSelectedEntityChange",b,null,null,null)
}mainController.closePopup()
};
w(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.lookup=QCD.components.elements.lookup||{};
QCD.components.elements.lookup.Dropdown=function(j,g,e){var h=25;
var d=4;
var l=25;
var b=j;
var i=g;
var c=e;
var f;
var m;
var n;
var k;
function a(){b.css("top","21px")
}this.updateAutocomplete=function(t,s){n=t;
f=null;
b.children().remove();
b.scrollTop(0);
if(s>h){var q=$("<div>").addClass("lookupMatch_noRecords").html(c.tooManyResultsInfo+" ("+s+")");
b.append(q);
b.css("height",(l-1)+"px")
}else{if(n.length==0){var q=$("<div>").addClass("lookupMatch_noRecords").html(c.noResultsInfo);
b.append(q);
b.css("height",(l-1)+"px")
}else{if(n.length>d){b.css("height",(l*d-1)+"px");
b.css("overflow","auto")
}else{b.css("height",(n.length*l-1)+"px");
b.css("overflow","hidden")
}for(var r in n){var p=n[r];
var o=$("<div>").addClass("lookupMatch").html(p.value).attr("id",i.elementPath+"_autocompleteOption_"+r);
o.mouseover(function(){$(this).addClass("lookupMatchHover");
m=$(this)
});
o.mouseout(function(){$(this).removeClass("lookupMatchHover");
m=null
});
o.click(function(){});
b.append(o)
}}}};
this.selectNext=function(){if(!f){o=$(b.children()[0])
}else{var o=f.next()
}if(!o||o.length==0){return
}if(f){f.removeClass("lookupMatchHover")
}f=o;
f.addClass("lookupMatchHover");
if(f.position().top<0){b.scrollTop(b.scrollTop()+f.position().top)
}if(f.position().top>=100){b.scrollTop(b.scrollTop()+f.position().top-75)
}};
this.selectPrevious=function(){if(!f){o=$(b.children()[b.children().length-1])
}else{var o=f.prev()
}if(!o||o.length==0){return
}if(f){f.removeClass("lookupMatchHover")
}f=o;
f.addClass("lookupMatchHover");
if(f.position().top<0){b.scrollTop(b.scrollTop()+f.position().top)
}if(f.position().top>=100){b.scrollTop(b.scrollTop()+f.position().top-75)
}};
this.getSelected=function(){if(f){var o=f.attr("id").substring((i.elementPath+"_autocompleteOption_").length);
return n[o]
}return null
};
this.getMouseSelected=function(){if(m){var o=m.attr("id").substring((i.elementPath+"_autocompleteOption_").length);
return n[o]
}return null
};
this.hide=function(){b.slideUp(400)
};
this.show=function(){b.slideDown(400)
};
this.isOpen=function(){return b.is(":visible")
};
a()
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.PasswordInput=function(a,b){$.extend(this,new QCD.components.elements.FormComponent(a,b));
if(this.options.referenceName){b.registerReferenceName(this.options.referenceName,this)
}this.setComponentData=function(c){this.input.val("")
}
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.containers.layout.SeperatorLine=function(a,b){$.extend(this,new QCD.components.containers.layout.Layout(a,b));
if(this.options.referenceName){b.registerReferenceName(this.options.referenceName,this)
}};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.StaticComponent=function(a,b){$.extend(this,new QCD.components.Component(a,b));
if(this.options.referenceName){b.registerReferenceName(this.options.referenceName,this)
}this.setComponentState=function(c){};
this.getComponentValue=function(){return null
};
this.setComponentValue=function(c){};
this.setComponentEnabled=function(c){};
this.setComponentLoading=function(c){}
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.TextArea=function(a,b){$.extend(this,new QCD.components.elements.FormComponent(a,b));
if(this.options.referenceName){b.registerReferenceName(this.options.referenceName,this)
}this.updateSize=function(d,e){var c=e?e-10:90;
if(c<50){this.input.height(22)
}else{this.input.height(c-23)
}this.input.parent().parent().parent().height(c)
}
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.TextInput=function(b,h){$.extend(this,new QCD.components.elements.FormComponent(b,h));
var g=$("#"+this.elementSearchName+"_text");
var d=this.input;
var f=this.elementPath;
var c=(this.options.listeners.length>0)?true:false;
function e(i){d.change(function(){a()
})
}function a(){if(c){mainController.callEvent("onInputChange",f,null,null,null)
}}if(this.options.referenceName){h.registerReferenceName(this.options.referenceName,this)
}this.getComponentData=function(){return{value:d.val()}
};
this.setComponentData=function(i){if(i.value){this.input.val(i.value);
g.html(i.value)
}else{this.input.val("");
g.html("-")
}};
this.setFormComponentEnabled=function(i){if(this.options.textRepresentationOnDisabled){if(i){d.show();
g.hide()
}else{d.hide();
g.show()
}}};
this.updateSize=function(j,k){var i=k?k-10:40;
this.input.parent().parent().parent().height(i);
this.input.parent().parent().height(i)
};
e(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.Tree=function(N,A){$.extend(this,new QCD.components.Component(N,A));
var u=A;
var q=N;
var x;
var g;
var E;
var D=new Object();
var d;
var y;
var j=this.options.belongsToFieldName;
var b=this.elementPath;
var t=this.elementSearchName;
var B;
var P=false;
var O=this.options.listeners;
var F;
var z;
var r=true;
var a=this.options.translations;
var h=false;
var L;
var R=false;
var o=new Object();
var k=new Object();
var J=new Array();
var s=false;
var I;
if(this.options.referenceName){u.registerReferenceName(this.options.referenceName,this)
}var K=this.fireOnChangeListeners;
function c(ag){g=$("<div>").addClass("tree_header").addClass("elementHeader").addClass("elementHeaderDisabled");
L=$("<div>").addClass("moveModeIconElement").hide();
var aa=$("<div>").addClass("description_message").css("display","none");
var ae=$("<span>").html(a.moveModeInfoHeader);
var af=$("<p>").html(a.moveModeInfoContent);
aa.append(ae);
aa.append(af);
L.append(aa);
L.hover(function(){aa.show()
},function(){aa.hide()
});
g.append(L);
E=$("<div>").addClass("tree_title").addClass("elementHeaderTitle").html(a.header);
g.append(E);
k=ag.options.dataTypes;
for(var ad in k){var ab=k[ad];
var ac=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){if($(this).hasClass("headerButtonEnabled")){G(ah)
}},ab.newIcon,ab);
ac.attr("title",a["newButton_"+ab.name]);
J.push(ac)
}D.editButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){f()
},"editIcon16_dis.png");
D.editButton.attr("title",a.editButton);
D.editButton.css("marginLeft","20px");
D.deleteButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){w()
},"deleteIcon16_dis.png");
D.deleteButton.attr("title",a.deleteButton);
D.moveUpButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){e()
},"upIcon16_dis.png").css("marginLeft","20px");
D.moveDownButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){m()
},"downIcon16_dis.png");
D.moveLeftButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){Q()
},"leftIcon16_dis.png");
D.moveRightButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){M()
},"rightIcon16_dis.png");
D.saveButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){U()
},"saveIcon16.png").attr("title",a.moveModeSaveButton);
D.cancelButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){i()
},"cancelIcon16.png").attr("title",a.moveModeCancelButton);
D.moveButton=QCD.components.elements.utils.HeaderUtils.createHeaderButton("",function(ah){if(!$(this).hasClass("headerButtonEnabled")){return
}if(D.moveButton.hasClass("headerButtonActive")){X()
}else{H()
}},"moveIcon16_dis.png").css("marginLeft","20px").attr("title",a.moveModeButton);
D.moveUpButton.hide();
D.moveDownButton.hide();
D.moveLeftButton.hide();
D.moveRightButton.hide();
D.saveButton.hide();
D.cancelButton.hide();
for(var ad in J){g.append(J[ad])
}g.append(D.editButton);
g.append(D.deleteButton);
g.append(D.moveButton);
D.moveButton.addClass("headerButtonEnabled");
g.append(D.saveButton);
D.saveButton.addClass("headerButtonEnabled");
g.append(D.cancelButton);
D.cancelButton.addClass("headerButtonEnabled");
g.append(D.moveUpButton);
g.append(D.moveDownButton);
g.append(D.moveLeftButton);
g.append(D.moveRightButton);
d=$("<div>").addClass("tree_content");
var Z=$("<div>").addClass("tree_wrapper");
Z.append(g);
Z.append(d);
q.append(Z);
q.css("padding","10px");
x=d.jstree({plugins:["json_data","themes","crrm","ui","dnd"],themes:{theme:"classic",dots:true,icons:true},json_data:{data:[]},ui:{select_limit:1},crrm:{move:{check_move:function(ah){if(h){var aj=ah.r.attr("id");
if(!aj||ah.p!="inside"){return true
}var ai=k[o[S(aj)]];
if(ai.canHaveChildren){return true
}else{return false
}}return false
}}},dnd:{drag_finish:function(ah){R=true
},drop_target:false,drag_target:false,},core:{html_titles:true,animation:100},cookies:false}).bind("before.jstree",function(ai,ah){if(!P&&(ah.func=="select_node"||ah.func=="hover_node")){ai.stopImmediatePropagation();
return false
}}).bind("select_node.jstree",function(ai,ah){if(r){W();
if(O.length>0){T()
}}})
}this.setComponentState=function(ab){F=ab.openedNodes;
z=ab.selectedEntityId;
y=ab.belongsToEntityId;
if(ab.newButtonClickedBefore){var aa=u.getLastPageController();
if(aa){for(var ac in k){if(k[ac].correspondingView==aa.getViewName()){var Z=aa.getComponentByReferenceName(k[ac].correspondingComponent);
I=Z.getComponentValue().entityId;
break
}}}}};
this.getComponentValue=function(){var Z=new Object();
var aa;
if(F){aa=F;
F=null
}else{aa=new Array();
x.find(".jstree-open").each(function(){aa.push(S(this.id))
})
}Z.openedNodes=aa;
var ab;
if(z){ab=z;
z=null
}else{ab=v()
}Z.selectedEntityId=ab;
Z.belongsToEntityId=y;
if(h){Z.treeStructure=l()
}Z.newButtonClickedBefore=s;
return Z
};
this.setComponentValue=function(ab){if(ab.belongsToEntityId){y=ab.belongsToEntityId
}if(B){var ac=x.jstree("get_json",-1);
for(var aa in ac){x.jstree("delete_node",$("#"+t+"_node_"+S(ac[aa].attr.id)))
}}if(ab.root){B=V(ab.root,-1)
}else{B=null
}x.jstree("close_all",B,true);
for(var aa in ab.openedNodes){x.jstree("open_node",$("#"+t+"_node_"+ab.openedNodes[aa]),false,true)
}if(ab.selectedEntityId!=null){r=false;
x.jstree("select_node",$("#"+t+"_node_"+ab.selectedEntityId),false);
r=true
}if(I){lastAddedNode=$("#"+t+"_node_"+I);
lastAddedNode.addClass("lastAdded");
if(x.jstree("get_json",-1)[0]&&x.jstree("get_json",-1)[0].children){var Z=$.jstree._focused()._get_parent(lastAddedNode);
x.jstree("open_node",Z,false,true);
x.animate({scrollTop:lastAddedNode.offset().top},{duration:"slow",easing:"swing"})
}else{x.jstree("select_node",lastAddedNode,false)
}}W();
p()
};
function H(){$.jstree._focused()._get_settings().dnd.dnd_enabled=true;
D.moveButton.hide();
for(var Z in J){J[Z].hide()
}D.editButton.hide();
D.deleteButton.hide();
L.show();
L.css("display","inline-block");
D.moveUpButton.show();
D.moveUpButton.css("display","inline-block");
D.moveDownButton.show();
D.moveDownButton.css("display","inline-block");
D.moveLeftButton.show();
D.moveLeftButton.css("display","inline-block");
D.moveRightButton.show();
D.moveRightButton.css("display","inline-block");
D.saveButton.show();
D.saveButton.css("display","inline-block");
D.cancelButton.show();
D.cancelButton.css("display","inline-block");
h=true;
W();
if(O.length>0){for(var Z in O){var aa=u.getComponent(O[Z]);
if(aa.elementPath==b){continue
}aa.setEditable(false)
}}K("onMoveModeChange",[true])
}function X(){$.jstree._focused()._get_settings().dnd.dnd_enabled=false;
D.moveButton.addClass("headerButtonEnabled");
D.moveButton.setInfo();
D.moveButton.label.html("");
D.moveButton.show();
for(var Z in J){J[Z].show()
}L.hide();
D.editButton.show();
D.deleteButton.show();
D.moveUpButton.hide();
D.moveDownButton.hide();
D.moveLeftButton.hide();
D.moveRightButton.hide();
D.saveButton.hide();
D.cancelButton.hide();
h=false;
W();
if(O.length>0){for(var Z in O){var aa=u.getComponent(O[Z]);
if(aa.elementPath==b){continue
}aa.setEditable(true)
}}K("onMoveModeChange",[false])
}this.performUpdateState=function(){if(h){X()
}};
function l(ab){if(!ab){var ab=x.jstree("get_json",-1)
}var aa=new Array();
for(var Z in ab){var ac={id:S(ab[Z].attr.id)};
if(ab[Z].children){ac.children=l(ab[Z].children)
}aa.push(ac)
}return aa
}function V(ac,ab){var ad=ac.id?ac.id:"0";
o[ad]=ac.dataType.name;
var aa=x.jstree("create",ab,"last",{data:{title:ac.label,icon:ac.dataType.nodeIcon},attr:{id:b+"_node_"+ad}},false,true);
for(var Z in ac.children){V(ac.children[Z],aa,false)
}x.jstree("close_node",aa,true);
return aa
}function W(){var ab=v();
if(P){D.moveButton.addClass("headerButtonEnabled")
}else{D.moveButton.removeClass("headerButtonEnabled")
}if(!ab){var ah=x.jstree("get_json",-1);
if(ah.length==0&&P){for(var ac in J){J[ac].addClass("headerButtonEnabled")
}D.moveButton.removeClass("headerButtonEnabled")
}else{for(var ac in J){J[ac].removeClass("headerButtonEnabled")
}}D.editButton.removeClass("headerButtonEnabled");
D.deleteButton.removeClass("headerButtonEnabled");
if(h){D.moveUpButton.removeClass("headerButtonEnabled");
D.moveDownButton.removeClass("headerButtonEnabled");
D.moveLeftButton.removeClass("headerButtonEnabled");
D.moveRightButton.removeClass("headerButtonEnabled")
}}else{var ag=k[o[ab]];
if(ag.canHaveChildren){for(var ac in J){J[ac].addClass("headerButtonEnabled")
}}else{for(var ac in J){J[ac].removeClass("headerButtonEnabled")
}}if(ab!="0"){D.editButton.addClass("headerButtonEnabled");
D.deleteButton.addClass("headerButtonEnabled")
}else{D.editButton.removeClass("headerButtonEnabled");
D.deleteButton.removeClass("headerButtonEnabled")
}if(h){var ae=x.jstree("get_selected");
var ad=$.jstree._focused()._get_parent(ae);
if(ad&&ad!=-1){D.moveLeftButton.addClass("headerButtonEnabled")
}else{D.moveLeftButton.removeClass("headerButtonEnabled")
}var aa=$.jstree._focused()._get_next(ae,true);
if(aa){D.moveDownButton.addClass("headerButtonEnabled")
}else{D.moveDownButton.removeClass("headerButtonEnabled")
}var af=$.jstree._focused()._get_prev(ae,true);
if(af){D.moveUpButton.addClass("headerButtonEnabled");
var Z=k[o[S(af.attr("id"))]];
if(Z.canHaveChildren){D.moveRightButton.addClass("headerButtonEnabled")
}else{D.moveRightButton.removeClass("headerButtonEnabled")
}}else{D.moveUpButton.removeClass("headerButtonEnabled");
D.moveRightButton.removeClass("headerButtonEnabled")
}}}}this.setComponentEnabled=function(Z){P=Z;
if(P){x.removeClass("treeDisabled");
g.removeClass("elementHeaderDisabled")
}else{x.addClass("treeDisabled");
g.addClass("elementHeaderDisabled")
}W()
};
function G(Z){s=true;
var ab=new Object();
if(j){ab[Z.correspondingComponent+"."+j]=y
}ab[Z.correspondingComponent+".entityType"]=Z.name;
var aa=v();
aa=aa=="0"?null:aa;
ab[Z.correspondingComponent+".parent"]=aa;
Y(ab,Z)
}function f(){if(D.editButton.hasClass("headerButtonEnabled")){s=false;
var aa=new Object();
var Z=v();
dataType=k[o[Z]];
aa[dataType.correspondingComponent+".id"]=Z;
Y(aa,dataType)
}}function w(){var Z=a.confirmDeleteMessage;
if(D.deleteButton.hasClass("headerButtonEnabled")){if(window.confirm(Z)){n();
s=false;
u.callEvent("remove",b,function(){p()
},null,null)
}}}function e(){if(D.moveUpButton.hasClass("headerButtonEnabled")){var aa=x.jstree("get_selected");
var Z=$.jstree._focused()._get_prev(aa,true);
C(aa,Z,"before")
}}function m(){if(D.moveDownButton.hasClass("headerButtonEnabled")){var aa=x.jstree("get_selected");
var Z=$.jstree._focused()._get_next(aa,true);
C(aa,Z,"after")
}}function Q(){if(D.moveLeftButton.hasClass("headerButtonEnabled")){var aa=x.jstree("get_selected");
var Z=$.jstree._focused()._get_parent(aa);
C(aa,Z,"after")
}}function M(){if(D.moveRightButton.hasClass("headerButtonEnabled")){var aa=x.jstree("get_selected");
var Z=$.jstree._focused()._get_prev(aa,true);
C(aa,Z,"last")
}}function U(){if(D.saveButton.hasClass("headerButtonEnabled")){n();
u.callEvent("save",b,null)
}}function i(){if(D.cancelButton.hasClass("headerButtonEnabled")){n();
u.callEvent("clear",b,null)
}}function C(aa,ab,Z){x.jstree("move_node",aa,ab,Z);
W();
R=true
}function T(){if(P){u.callEvent("select",b,null)
}}function v(){var Z=x.jstree("get_selected");
if(Z&&Z.length>0){return S(Z.attr("id"))
}return null
}function S(Z){return Z.substring(b.length+6)
}function Y(ac,aa){var Z=aa.correspondingView;
if(Z&&Z!=""){var ab=Z+".html";
if(ac){ab+="?context="+JSON.stringify(ac)
}if(aa.correspondingViewInModal){u.openModal(b+"_editWindow",ab)
}else{u.goToPage(ab)
}}}this.isComponentChanged=function(){return h
};
this.setComponentLoading=function(Z){if(Z){n()
}else{p()
}};
this.updateSize=function(Z,aa){if(!Z){Z=300
}if(!aa){aa=300
}d.height(aa-52);
d.width(Z-40)
};
function n(){P=false;
QCD.components.elements.utils.LoadingIndicator.blockElement(q)
}function p(){QCD.components.elements.utils.LoadingIndicator.unblockElement(q);
P=true
}c(this)
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.utils=QCD.components.elements.utils||{};
QCD.components.elements.utils.HeaderUtils={};
QCD.components.elements.utils.HeaderUtils.createHeaderButton=function(k,g,i,a){var f=(i&&$.trim(i)!="")?$.trim(i):null;
if(f.indexOf("/")==-1){f="/qcadooView/public/img/core/icons/"+f
}var l=$("<div>");
l.html(k);
var d=$("<span>");
var c=$("<a>").attr("href","#").append(d);
if(i&&$.trim(i)!=""){l.addClass("hasIcon");
d.append($("<div>").addClass("icon").css("backgroundImage","url('"+f+"')"))
}d.append(l);
if(k==""){l.css("paddingLeft","0px");
l.css("paddingRight","3px")
}if(a){c.bind("click",a,function(m){c.blur();
g.call($(this).parent()[0],m.data)
})
}else{c.bind("click",function(m){c.blur();
g.call($(this).parent(),m.data)
})
}var e=$("<div>").addClass("headerActionButton").append(c);
e.label=l;
var h=$("<div>").addClass("ribbon_description_icon").css("display","none");
var j=$("<div>").addClass("description_message").css("display","none");
var b=$("<p>").html("");
j.append(b);
e.append(h);
e.append(j);
h.hover(function(){j.show()
},function(){j.hide()
});
e.setInfo=function(m){if(m&&m!=""){this.find(".ribbon_description_icon").show();
this.find(".description_message").html(m)
}else{this.find(".ribbon_description_icon").hide()
}};
return e
};
QCD.components.elements.utils.HeaderUtils.createHeaderComboBox=function(c,b){var a=$("<select>").addClass("headerSelect");
a.change(function(){b(a.val())
});
for(var d in c){a.append($("<option>").attr("value",c[d].value).html(c[d].label))
}a.enable=function(){$(this).attr("disabled","")
};
a.disable=function(){$(this).attr("disabled","true")
};
a.disable();
return a
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.elements=QCD.components.elements||{};
QCD.components.elements.utils=QCD.components.elements.utils||{};
QCD.components.elements.utils.LoadingIndicator={};
QCD.components.elements.utils.LoadingIndicator.blockElement=function(a){a.block({message:'<div class="loading_div"></div>',showOverlay:true,fadeOut:0,fadeIn:0,css:{border:"none",padding:"15px",backgroundColor:"#000","-webkit-border-radius":"10px","-moz-border-radius":"10px",opacity:0.5,color:"#fff",width:"50px"},overlayCSS:{backgroundColor:"#000",opacity:0.1},})
};
QCD.components.elements.utils.LoadingIndicator.unblockElement=function(a){a.unblock()
};var QCD=QCD||{};
QCD.components=QCD.components||{};
QCD.components.Ribbon=function(q,g,n,m){var o=q;
var b=n;
var j=g;
var e=m;
var c;
var l=new Object();
this.constructElement=function(){c=$("<div>");
var v=$("<div>").attr("id","q_row3_out");
c.append(v);
c.append($("<div>").attr("id","q_row4_out"));
var w=$("<div>").attr("id","q_menu_row3");
w.append(this.constructElementContent());
v.append(w);
return c
};
this.constructElementContent=function(){var D=$("<div>");
if(o.groups){for(var C in o.groups){var F=o.groups[C];
var E=$("<div>").addClass("ribbon_content");
var w=$("<div>").addClass("ribbon_title").html(F.label);
var G=$("<div>").addClass("ribbonMenu_right").append(w).append(E);
var A=$("<div>").addClass("ribbonMenu_left").append(G);
var B=$("<div>").addClass("ribbonMenu").append(A);
var x=null;
for(var v in F.items){var H=F.items[v];
var y=null;
var z=false;
H.group=F.name;
if(H.type=="BIG_BUTTON"){if(H.items){y=p(F.name,H)
}else{y=d(F.name,H)
}}else{if(H.type=="SMALL_BUTTON"){if(H.items){y=a(F.name,H)
}else{y=h(F.name,H)
}z=true
}else{if(H.type=="COMBOBOX"){y=t(F.name,H);
z=true
}else{if(H.type=="SMALL_EMPTY_SPACE"){y=$("<div>");
z=true
}}}}if(y){if(z){if(x){x.append(y);
x=null
}else{x=$("<ul>").addClass("ribbon_list");
x.append(y);
E.append(x)
}}else{E.append(y);
x=null
}}H.element=y;
if(H.enabled){y.addClass("enabled")
}if(H.message){if(H.tooltipElement){if(H.message&&H.message!=""){H.tooltipElement.show();
H.tooltipMessageElementContent.html(H.message)
}else{H.tooltipElement.hide()
}}}else{if(H.tooltipElement){H.tooltipElement.hide()
}}}D.append(B)
}}return D
};
function d(z,w){var y=$("<a>").attr("href","#").html("<span><div"+i(w)+"></div><label class='ribbonLabel'>"+w.label+"</label></div></div></span>");
var A=$("<li>").append(y);
var x=$("<ul>").addClass("ribbonListElement").append(A);
var v=$("<div>").addClass("ribbonBigElement").append(x);
r(w,y);
y.bind("click",{itemElement:v,itemName:w.name,clickAction:w.clickAction},s);
return v
}function p(K,J){var G=(J.icon&&$.trim(J.icon)!="")?$.trim(J.icon):null;
var v="";
var E="";
if(G){v=" style=\"background-image:url('/qcadooView/public/img/core/icons/"+G+"')\"";
E=" hasIcon"
}var y=$("<a>").attr("href","#").html("<span><div class='"+E+" bigDropdownButtonDiv' "+v+"><label class='ribbonLabel'>"+J.label+"</label><div></div></div></span>");
var A=$("<li>").append(y);
var D=$("<a>").attr("href","#").html("<span><div class='icon_btn_addB'></div></span>");
var H=$("<li>").addClass("addB").append(D);
var z=$("<ul>").append(A).append(H);
var C=$("<div>").append(z);
var x=$("<span>").append(C);
var I=$("<li>").append(x);
var F=$("<ul>").addClass("ribbonAddElement").append(I);
var w=$("<div>").addClass("ribbonBigElement").addClass("ribbonDropdownContainer").append(F);
var B=k(K+"."+(J.label?J.label:J.name),J).addClass("bigButtonDropdownMenu");
f(D);
w.append(B);
y.bind("click",{itemName:J.name,clickAction:J.clickAction},s);
return w
}function h(y,x){var w=$("<a>").attr("href","#").html("<span><div"+i(x)+"></div><div class='btnOneLabel ribbonLabel'>"+x.label+"</div></span>");
r(x,w);
var v=$("<li>").addClass("btnOne").append(w);
w.bind("click",{itemElement:v,itemName:x.name,clickAction:x.clickAction},s);
return v
}function a(F,E){var x=$("<a>").attr("href","#").html("<span><div "+i(E)+"></div><div class='btnOneLabel'>"+E.label+"</div></span>");
var z=$("<li>").append(x);
var B=$("<a>").attr("href","#").addClass("twoB_down");
var D=$("<li>").append(B);
var y=$("<ul>").append(z).append(D);
var C=$("<div>").append(y);
var w=$("<span>").append(C);
var v=$("<li>").addClass("twoB").addClass("ribbonDropdownContainer").append(w);
x.bind("click",{itemElement:v,itemName:E.name,clickAction:E.clickAction},s);
var A=k(F+"."+(E.label?E.label:E.name),E).addClass("smallButtonDropdownMenu");
f(B,v);
v.append(A);
return v
}function k(F,E){var z=$("<ul>");
for(var D in E.items){var x=E.items[D];
var C=x.name;
var B=(x.icon&&$.trim(x.icon)!="")?$.trim(x.icon):null;
var w="";
if(B){w=" style=\"background-image:url('/qcadooView/public/img/core/icons/"+B+"')\""
}var A=$("<a>").attr("href","#").html("<span class='dropdownItemIcon' "+w+"></span><span class='dropdownItemLabel'>"+x.label+"</span>").addClass("icon");
var v=$("<li>").append(A);
A.bind("click",{itemElement:v,itemName:E.name+"."+C,clickAction:x.clickAction},s);
x.element=v;
x.group=E.group;
z.append(v)
}var y=$("<div>").addClass("dropdownMenu").addClass("m_module").append(z);
return y
}function f(w,v){w.addClass("dropdownTrigger");
w.click(function(){var x=$(this);
x.blur();
if(!v.hasClass("enabled")){return
}while(!x.hasClass("ribbonDropdownContainer")){x=x.parent()
}if(x.find(".dropdownMenu").is(":visible")){x.find(".dropdownMenu").slideUp(100)
}else{x.find(".dropdownMenu").slideDown(100).show();
x.hover(function(){},function(){x.find(".dropdownMenu").slideUp(100)
})
}})
}function t(E,D){var x=$("<a>").attr("href","#").html("<span><div "+i(D)+">"+D.label+"</div></span>");
var z=$("<li>").append(x);
var A=$("<a>").attr("href","#").addClass("twoB_down");
var C=$("<li>").append(A);
var y=$("<ul>").append(z).append(C);
var B=$("<div>").append(y);
var w=$("<span>").append(B);
var v=$("<li>").addClass("twoB").addClass("ribbonDropdownContainer").append(w);
return v
}function r(y,w){var x=$("<div>").addClass("ribbon_description_icon").css("display","none");
var v=$("<div>").addClass("description_message").css("display","none");
var z=$("<p>").html("");
v.append(z);
w.append(x);
w.append(v);
x.hover(function(){v.show()
},function(){v.hide()
});
y.tooltipElement=x;
y.tooltipMessageElementContent=z
}function i(x){var w=(x.icon&&$.trim(x.icon)!="")?$.trim(x.icon):null;
var v="";
if(w){v=" class='iconElement hasIcon' style=\"background-image:url('/qcadooView/public/img/core/icons/"+w+"')\""
}else{v="class='iconElement' "
}return v
}function s(y){$(this).blur();
if(y.data.itemElement){if(!y.data.itemElement.hasClass("enabled")){return
}}var x=y.data.clickAction;
var v=y.data.itemName;
var z=y.data.itemElement.onChangeListeners;
if(z){for(var w in z){z[w].onClick()
}}if(x){b.getActionEvaluator().performRibbonAction(x)
}}function u(w){if(l[w.group+"-"+w.name]){return l[w.group+"-"+w.name]
}var v={element:w.element,tooltipElement:w.tooltipElement,tooltipMessageElementContent:w.tooltipMessageElementContent,disable:function(x){this.element.removeClass("enabled");
if(this.tooltipElement){if(x&&x!=""){this.tooltipElement.show();
this.tooltipMessageElementContent.html(x)
}else{this.tooltipElement.hide()
}}},enable:function(x){this.element.addClass("enabled");
if(this.tooltipElement){if(x&&x!=""){this.tooltipElement.show();
this.tooltipMessageElementContent.html(x)
}else{this.tooltipElement.hide()
}}},isEnabled:function(){return this.element.hasClass("enabled")
},setLabel:function(x){this.element.find(".ribbonLabel").html(x)
},setIcon:function(y){var x=this.element.find(".iconElement");
if(y){x.addClass("hasIcon");
x.css("backgroundImage","url('/qcadooView/public/img/core/icons/"+y+"')")
}else{x.removeClass("hasIcon")
}},addOnChangeListener:function(x){if(!this.element.onChangeListeners){this.element.onChangeListeners=new Array()
}this.element.onChangeListeners.push(x)
},removeOnChangeListeners:function(){this.element.onChangeListeners=new Array()
}};
l[w.group+"-"+w.name]=v;
return v
}this.performScripts=function(){for(var z in o.groups){var y=o.groups[z];
for(var w in y.items){var x=y.items[w];
if(x.script){var v=u(x);
b.getActionEvaluator().performJsAction(x.script,v)
}}}};
this.updateRibbonState=function(A){for(var y in A.groups){var z=A.groups[y];
for(var w in z.items){var x=z.items[w];
var v=this.getRibbonItem(z.name+"."+x.name);
if(x.label){v.setLabel(x.label)
}if(x.enabled!=undefined){if(x.enabled){v.enable()
}else{v.disable()
}}if(x.message){if(v.isEnabled()){v.enable(x.message)
}else{v.disable(x.message)
}}}}};
this.getRibbonItem=function(v){var x=v.split(".");
if(x.length!=2&&x.length!=3){QCD.error("wrong path: '"+v+"'");
return null
}var A=null;
for(var z in o.groups){if(o.groups[z].name==x[0]){A=o.groups[z];
break
}}if(!A){return null
}var y=null;
for(var w in A.items){if(A.items[w].name==x[1]){y=A.items[w];
break
}}if(!y){return null
}if(x.length==3){if(!y.items){return null
}var C=null;
for(var B in y.items){if(y.items[B].name==x[2]){C=y.items[B];
break
}}if(!C){return null
}return u(C)
}return u(y)
};
this.updateSize=function(w,v){$("#q_menu_row3").css("margin-left",(w)+"px");
$("#q_row4_out").width(v)
}
};var QCD=QCD||{};
QCD.ActionEvaluator=function(_pageController){var pageController=_pageController;
this.performJsAction=function(jsBody,scope){jsBody=" "+Encoder.htmlDecode(jsBody)+" ";
var referenceObject={};
var thisObject=scope;
if(/\WreferenceObject\W/.test(jsBody)){printError("script contains forbidden keyword 'referenceObject'");
return
}if(/\WthisObject\W/.test(jsBody)){printError("script contains forbidden keyword 'thisObject'");
return
}if(!scope){if(/\Wthis\W/.test(jsBody)){printError("script contains keyword 'this', but scope is not defined");
return
}}var referencePatternRegexp=/#\{[^\}]+\}/g;
var referencePatternMatches=jsBody.match(referencePatternRegexp);
if(referencePatternMatches){for(var i=0;
i<referencePatternMatches.length;
i++){var referencePattern=referencePatternMatches[i];
referenceName=referencePattern.substring(2,referencePattern.length-1);
if(!referenceObject[referenceName]){var referenceValue=pageController.getComponentByReferenceName(referenceName);
if(referenceValue==null){printError("no component with referenceName '"+referenceName+"'");
return
}referenceObject[referenceName]=referenceValue
}}}for(var referenceName in referenceObject){var referenveRegexp=new RegExp("#{"+referenceName+"}","g");
jsBody=jsBody.replace(referenveRegexp,"referenceObject."+referenceName)
}var thisPatternRegexp=/\Wthis\W/g;
var thisPatternMatches=jsBody.match(thisPatternRegexp);
if(thisPatternMatches){for(var i=0;
i<thisPatternMatches.length;
i++){var thisPattern=thisPatternMatches[i];
jsBody=jsBody.replace(thisPattern,thisPattern[0]+"thisObject"+thisPattern[thisPattern.length-1])
}}try{eval(jsBody)
}catch(e){printError(e)
}};
this.performRibbonAction=function(ribbonAction){var actionParts=ribbonAction.split(";");
var actions=new Array();
for(var actionIter in actionParts){var action=$.trim(actionParts[actionIter]);
if(action){var elementBegin=action.search("{");
var elementEnd=action.search("}");
if(elementBegin<0||elementEnd<0||elementEnd<elementBegin){QCD.error("action parse error in: "+action);
return
}var elementPath=action.substring(elementBegin+1,elementEnd);
var component=pageController.getComponent(elementPath);
var elementAction=action.substring(elementEnd+1);
if(elementAction[0]!="."){QCD.error("action parse error in: "+action);
return
}elementAction=elementAction.substring(1);
var argumentsBegin=elementAction.indexOf("(");
var argumentsEnd=elementAction.indexOf(")");
var argumentsList=new Array();
if(argumentsBegin>0&&argumentsEnd>0&&argumentsBegin<argumentsEnd-1){var args=elementAction.substring(argumentsBegin+1,argumentsEnd);
argumentsList=args.split(",");
elementAction=elementAction.substring(0,argumentsBegin)
}else{if(argumentsBegin==argumentsEnd-1){elementAction=elementAction.substring(0,argumentsBegin)
}}var actionObject={component:component,action:elementAction,arguments:argumentsList};
actions.push(actionObject)
}}var actionsPerformer={actions:actions,actionIter:0,performNext:function(){var actionObject=this.actions[this.actionIter];
if(actionObject){var func=actionObject.component[actionObject.action];
if(!func){QCD.error("no function in "+actionObject.component.elementPath+": "+actionObject.action);
return
}this.actionIter++;
var fullArgumentList=new Array(this);
fullArgumentList=fullArgumentList.concat(actionObject.arguments[0]);
fullArgumentList.push(actionObject.arguments.slice(1));
func.apply(actionObject.component,fullArgumentList)
}}};
actionsPerformer.performNext()
};
function printError(msg){QCD.error("cannot evaluate script: "+msg)
}};var QCD=QCD||{};
QCD.PageController=function(A,g,v,d){var s=A;
var f=g;
var b=v;
var e=d;
var o;
var i=null;
var C;
var l;
var c;
var p=new QCD.ActionEvaluator(this);
var m={};
var u=new QCD.TabController();
var q=window.location.href;
var w;
function y(I){QCDConnector.windowName="/page/"+f+"/"+s;
QCDConnector.mainController=I;
var G=$("#pageOptions");
C=JSON.parse($.trim(G.html()));
G.remove();
if(e&&window.parent.changeModalSize){var E=C.windowWidth?C.windowWidth:600;
var F=C.windowHeight?C.windowHeight:400;
window.parent.changeModalSize(E,F)
}var H=$("body");
o=QCDPageConstructor.getChildrenComponents(H.children(),I);
QCD.debug(o);
u.updateTabObjects();
$(window).bind("resize",z);
z();
if(window.parent){$(window.parent).focus(r)
}else{$(window).focus(r)
}QCD.components.elements.utils.LoadingIndicator.blockElement($("body"))
}this.init=function(F){QCD.components.elements.utils.LoadingIndicator.blockElement($("body"));
for(var E in o){o[E].performScript()
}if(F){n(F);
if(b){this.callEvent("initializeAfterBack",null,function(){QCD.components.elements.utils.LoadingIndicator.unblockElement($("body"))
})
}else{QCD.components.elements.utils.LoadingIndicator.unblockElement($("body"))
}}else{if(b){this.callEvent("initialize",null,function(){QCD.components.elements.utils.LoadingIndicator.unblockElement($("body"))
})
}else{QCD.components.elements.utils.LoadingIndicator.unblockElement($("body"))
}}};
this.setContext=function(J){var G=JSON.parse(J);
for(var F in G){var I=F.lastIndexOf(".");
var E=F.substring(0,I);
var K=F.substring(I+1);
var H=this.getComponent(E);
H.addContext(K,G[F])
}};
this.callEvent=function(I,L,G,J,M){var K=new Object();
var E=G;
K.event={name:I};
if(L){K.event.component=L;
var F=t(L);
var N=F.options.listeners;
if(N){for(var H=0;
H<N.length;
H++){var O=t(N[H]);
O.setComponentLoading(true)
}E=function(){if(G){G()
}for(var P=0;
P<N.length;
P++){var Q=t(N[P]);
Q.setComponentLoading(false)
}}
}}if(J){K.event.args=J
}K.components=B();
x(K,E,M)
};
function x(F,G,H){var E=JSON.stringify(F);
QCDConnector.sendPost(E,function(I){if(G){G()
}if(I.redirect){if(I.redirect.openInNewWindow){window.open(I.redirect.url)
}else{if(I.redirect.openInModalWindow){j(I.redirect.url,I.redirect.url)
}else{k(I.redirect.url,false,I.redirect.shouldSerializeWindow);
return
}}}else{h(I)
}if(H&&!(I.content&&I.content.status&&I.content.status!="ok")){QCD.info(H);
H.performNext()
}},function(){if(G){G()
}})
}this.getActionEvaluator=function(){return p
};
function B(){var E=new Object();
for(var F in o){var G=o[F].getValue();
if(G){E[F]=G
}}return E
}function n(G){for(var F in G.components){var E=o[F];
E.setState(G.components[F])
}}this.showMessage=function(E){if(window.parent&&window.parent.addMessage){window.parent.addMessage(E)
}else{if(!l){l=new QCD.MessagesController()
}l.addMessage(E)
}};
this.setWindowHeaderComponent=function(E){i=E
};
this.setWindowHeader=function(E){if(i){i.setHeader(E)
}};
this.getViewName=function(){return f+"/"+s
};
function h(H){QCD.debug(H);
if(H.messages){for(var F in H.messages){var G=H.messages[F];
window.parent.addMessage(G.type,G.content)
}}for(var F in H.components){var E=o[F];
E.setValue(H.components[F])
}}this.getComponent=function(H){var G=H.split(".");
var E=o[G[0]];
if(!E){return null
}for(var F=1;
F<G.length;
F++){if(!E.components){return null
}E=E.components[G[F]];
if(!E){return null
}}return E
};
var t=this.getComponent;
this.registerReferenceName=function(F,E){m[F]=E
};
this.getComponentByReferenceName=function(E){return m[E]
};
this.getTabController=function(){return u
};
function r(){if(c){c.parentComponent.onPopupClose();
c.window.close();
c=null
}}this.closePopup=function(){if(c){c.parentComponent.onPopupClose();
try{c.window.close()
}catch(E){}c=null
}};
this.openPopup=function(E,I,H){if(c){}if(E.indexOf("?")!=-1){E+="&"
}else{E+="?"
}E+="popup=true";
c=new Object();
c.pageController=this;
c.parentComponent=I;
var G=(screen.width/2)-(400);
var F=(screen.height/2)-(350);
c.window=window.open(E,H,"status=0,toolbar=0,width=800,height=700,left="+G+",top="+F);
return c.window
};
this.onPopupInit=function(){c.parentComponent.onPopupInit()
};
this.isPopup=function(){return e
};
this.goToPage=function(E,H,G){if(H==undefined||H==null){H=true
}var F=null;
if(G==true||G==undefined||G==null){F=D()
}window.parent.goToPage(E,F,H)
};
var k=this.goToPage;
function j(F,E){window.parent.openModal(F,E,D())
}this.openModal=j;
this.goBack=function(){if(a()){window.parent.goBack(this)
}};
function D(){return{url:q,components:B()}
}this.getLastPageController=function(){return window.parent.getLastPageController()
};
function a(){changed=false;
for(var E in o){if(o[E].isChanged()){changed=true
}}if(changed){return window.confirm(C.translations.backWithChangesConfirmation)
}else{return true
}}this.canClose=a;
this.closeWindow=function(){window.close()
};
this.onSessionExpired=function(){if(!e){window.parent.onSessionExpired(D())
}else{if(window.parent.onSessionExpired){window.parent.onSessionExpired(D(),true)
}else{window.location="/login.html?popup=true&targetUrl="+escape(q)
}}};
this.getCurrentUserLogin=function(){return window.parent.getCurrentUserLogin()
};
function z(){var G=$(document).width();
var E=$(document).height();
for(var F in o){o[F].updateSize(G,E)
}}this.updateSize=z;
y(this)
};var QCD=QCD||{};
QCD.TabController=function(){this.updateTabObjects=function(){$("input, select, a").live("keydown",function(b){if(b.keyCode==9){var a=$(this);
if(a.hasClass("customTabIndex")){}}})
}
};