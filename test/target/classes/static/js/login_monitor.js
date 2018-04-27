;
(function() {
	if (typeof window.CustomEvent === "function")
		return false;

	function CustomEvent(event, params) {
		params = params || {
			bubbles : false,
			cancelable : false,
			detail : undefined
		};
		var evt = document.createEvent('CustomEvent');
		evt.initCustomEvent(event, params.bubbles, params.cancelable,
				params.detail);
		return evt;
	}

	CustomEvent.prototype = window.Event.prototype;

	window.CustomEvent = CustomEvent;
})();
;
(function() {
	function ajaxEventTrigger(event) {
		var ajaxEvent = new CustomEvent(event, {
			detail : this
		});
		window.dispatchEvent(ajaxEvent);
	}

	var oldXHR = window.XMLHttpRequest;

	function newXHR() {
		var realXHR = new oldXHR();

		realXHR.addEventListener('abort', function() {
			ajaxEventTrigger.call(this, 'ajaxAbort');
		}, false);

		realXHR.addEventListener('error', function() {
			ajaxEventTrigger.call(this, 'ajaxError');
		}, false);

		realXHR.addEventListener('load', function() {
			ajaxEventTrigger.call(this, 'ajaxLoad');
		}, false);

		realXHR.addEventListener('loadstart', function() {
			ajaxEventTrigger.call(this, 'ajaxLoadStart');
		}, false);

		realXHR.addEventListener('progress', function() {
			ajaxEventTrigger.call(this, 'ajaxProgress');
		}, false);

		realXHR.addEventListener('timeout', function() {
			ajaxEventTrigger.call(this, 'ajaxTimeout');
		}, false);

		realXHR.addEventListener('loadend', function() {
			ajaxEventTrigger.call(this, 'ajaxLoadEnd');
		}, false);

		realXHR.addEventListener('readystatechange', function() {
			ajaxEventTrigger.call(this, 'ajaxReadyStateChange');
		}, false);

		return realXHR;
	}

	window.XMLHttpRequest = newXHR;
})();

var logonInvalid = true;
 window.addEventListener('ajaxReadyStateChange', function(e) {
	// console.info(window.location.href.indexOf("login"));
	if (window.location.href.indexOf("login") == -1) {
		try {
			$.parseJSON(e.detail.responseText);
		} catch (e1) {
			if (e.detail.responseText
					.indexOf('E43613CAC7609E554782BEC467A97839') != -1
					&& logonInvalid) {
				$.messager.alert("提示", "当前登录信息已失效，请重新登录！", "info", function() {
					window.location.href = 'login';
				});
				logonInvalid = false;
			}
		}
	}
});
//window.addEventListener('ajaxReadyStateChange', function(e) {
//	console.log(e.detail.responseText);
//});
window.addEventListener('ajaxAbort', function(e) {
	// XHR 返回的内容
	// console.log(e.detail.responseText);
});
