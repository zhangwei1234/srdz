/**
 * 公共JS 对象
 * 
 * @author cdzhangwei3
 * @time   2014-01-17
 */	

//定义JS命名空间
var Namespace = Namespace||new Object();
Namespace.register = function(path){var arr = path.split("."),ns = "";for(var i=0;i<arr.length;i++){if(i>0){ns += ".";}ns += arr[i];eval("if(typeof(" + ns + ")=='undefined'){" + ns + " = new Object();}");}};

//定义JS 公共组件（该组件中包含了基本打方法,ajax请求）
Namespace.register("Fmk");
(function(){
	Fmk.rootUrl = "";
	Fmk.staticUrl = "";
	
	//开启遮罩效果
	Fmk.mask = function(info){
		if($("#winModal,#loadInfo").length == 0) {var msg = (info != null && info.trim() != "") ? info:"系统正在为您处理数据,请稍候...";$("body").append("<div id='winModal'></div><div id='loadInfo'>"+msg+"</div>");}
	};
	
	//关闭遮罩效果
	Fmk.unmask = function(){
		$("#winModal,#loadInfo").remove();
	};
	
	//定义普通的ajax请求
	//url,参数,是否开启遮罩,请求方法(POST/GET/),成功回调函数,失败回调函数
	Fmk.Ajax = function(url,params,ismask,method,suc,err){
		if(method !="POST" && method !="GET" && method !="DELETE" && method !="PUT"){alert("执行失败,method不合法");return;}
		if(ismask){Fmk.mask();}
		$.ajax({
			url:Fmk.rootUrl+url,type:method,data:params,dataType:"json",
			beforeSend:function(xhr){xhr.setRequestHeader('response-type', 'ajax');},//标识为ajax请求
			success:function(response){
				Fmk.unmask();
				if(response["RES_STATUS"]=='fail'){//处理失败
					if(typeof(err)=="function"){err(response["RES_MSG"],response);}else{alert(response["RES_MSG"]);}
				}else{//处理成功
					if(typeof(suc)=="function"){suc(response["RES_MSG"],response);}else{alert(response["RES_MSG"]);}
				}
			},
			error:function(jqXHR,textStatus,errorThrown){
				Fmk.unmask();
				if(typeof(err)=="function"){err("请求失败,httpStatus:"+textStatus);}else{alert("执行失败,"+textStatus);}
			}
		});
	};
	
	Fmk.PostAjax=function(url,params,ismask,suc,err){
		Fmk.Ajax(url,params,ismask,"POST",suc,err);
	};
	
	Fmk.GetAjax=function(url,params,ismask,suc,err){
		Fmk.Ajax(url,params,ismask,"GET",suc,err);
	};
	
	Fmk.DeleteAjax=function(url,params,ismask,suc,err){
		Fmk.Ajax(url,params,ismask,"DELETE",suc,err);
	};
	
	Fmk.PutAjax=function(url,params,ismask,suc,err){
		Fmk.Ajax(url,params,ismask,"PUT",suc,err);
	};
	
	//加载内容到容器中,默认是Post请求
	//容器ID,是否开启遮罩,请求地址,请求参数,请求方法,回调函数
	Fmk.loadContainer = function(containerId,ismark,url,params,callback){
		if(ismark){Fmk.mask();}
		jQuery.post(Fmk.rootUrl+url,params,function(data){
			try{callback();}catch(e){}
			try{$("#"+containerId).html(data);}catch(e){}
			if(ismark){Fmk.unmask();}
			Fmk.initPage();
		});
	};
	
	//Post方式提交表单
	//表单name,成功回调函数,失败回调函数
	Fmk.PostAjaxForm = function(formName,suc,err){
		var form = $('form[name='+formName+']');
		var url = $(form).attr("action");
		var data = $(form).serialize();
		var method = $(form).attr("method");
		Fmk.Ajax(url,data,true,method,suc,err);
	};
	
	//初始化页面
	Fmk.initPage = function(){
		try{$("SPAN.script").each(function(i,o){eval($(o).remove().attr("page-load"));});}catch(e){}
	};
	
	//点击菜单
	Fmk.clickMenu = function(menu){
		var url = $(menu).attr("data-url");
		$(".menu").removeClass("active");
		$(menu).parent().addClass("active");
		Fmk.loadContainer("console_container",true,url,{},null,null);
	};
})();

