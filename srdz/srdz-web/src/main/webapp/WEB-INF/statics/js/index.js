Namespace.register("Topic.index");
(function(){
	//注册page创建事件
	$(document).on("pagecreate","#group-icon",function(){
		fmlList.clear();
		fmlList.count = 0;
		fmlList.container = "container";
		Topic.index.init(true);
		//自动加载
		$(document).scroll(function() {  
		  	  var scrollTop = $(this).scrollTop();
		      var scrollHeight = $(document).height();
		      var windowHeight = $(window).height();
		      if (scrollTop + windowHeight == scrollHeight){
		    	 if($(".menu-type-active").attr("data-display") == "2"){
		    		 Topic.index.toPageList();
		    	 }else{
		    		 Topic.index.toPage();
		    	 }
			  }
		  });
	});
	
	$(document).on("pagecreate","#group-list",function(){
		fmlList.clear();
		fmlList.count = 0;
		fmlList.container = "container";
		Topic.index.init(false);
		//自动加载
		$(document).scroll(function() {  
		  	  var scrollTop = $(this).scrollTop();
		      var scrollHeight = $(document).height();
		      var windowHeight = $(window).height();
		      if (scrollTop + windowHeight == scrollHeight){
		    	 if($(".menu-type-active").attr("data-display") == "2"){
			    		 Topic.index.toPageList();
		    	 }else{
		    		 Topic.index.toPage();
		    	 }
			  }
		  });
	});
	Topic.index.init = function(flag){
		fml.clear();
		fml.args.marginTop = 30;
		fml.container="container";
		fml.template="template";
		fml.args.maxWidth= 200;
		if(flag){fml.load();}
	};
	
	Topic.index.toPage = function(){//icon显示的分页
		 var start = fml.count;
		 var obj = $(".menu-type-active");
	   	 var type = obj.attr("data-type");
	   	 var display = obj.attr("data-display");
	   	 var order = obj.attr("data-order");
	   	 if(typeof(start) == "undefined" || typeof(type) == "undefined" || typeof(display) == "undefined" ||typeof(order) == "undefined" ){return;}
		Topic.PostAjax("type/list/"+type+"/"+display+"/"+start+"/"+order,{},true,function(msg,response){
  		  if(response["isFinish"] == "true"){//数据全部加载完
  			 return;
  		  }
  		  fml.data = response["products"];
  		  fml.load();
  	  },function(msg){alert("数据加载失败");});
	};
	
	Topic.index.toPageList = function(){
		 var start = fmlList.count;
		 var obj = $(".menu-type-active");
	   	 var type = obj.attr("data-type");
	   	 var display = obj.attr("data-display");
	   	 var order = obj.attr("data-order");
	   	 if(typeof(start) == "undefined" || typeof(type) == "undefined" || typeof(display) == "undefined" ||typeof(order) == "undefined" ){return;}
		Topic.appendContainer(fmlList.container,false,"type/list/"+type+"/"+display+"/"+start+"/"+order,{},function(){fmlList.count +=20;});
	};
	//首页菜单点击
	Topic.index.click = function(obj,container,type){
		$(".menu-type").removeClass("menu-type-active");
		$(obj).addClass("menu-type-active");
		$("#"+container).empty();
		if(type == 1){//icon显示
			fml.clear();
			Topic.index.toPage();
		}else if(type == 2){//list显示
			fmlList.clear();
			Topic.index.toPageList();
		}
	};
	//商品点击
	Topic.index.clickProduct = function(id){
		if(id == "" || typeof(id) == "undefined"){return;}
		Topic.PutAjax("product/click/"+id,{},false,function(){},function(){});
	};
	Topic.index.pariseProduct = function(id){
		if(id == "" || typeof(id) == "undefined"){return;}
		Topic.PutAjax("product/parise/"+id,{},false,function(){},function(){});
		try{
			var o = $("#heart-"+id);
			o.html(parseInt(o.html())+1);
		}catch(e){}
	};
})();

//定义列表组件
(function(a,b){
	a.fmlList = {version:"1.0",count:0,container:"",isInit:false};
})(this);

fmlList.clear = function(){
	fmlList.count = 0;
	fmlList.isInit = false;
	$("#"+this.container).css("height",'');
};
//定义图片墙加载框架
(function(a,b){
	a.fml = {version:"1.0",top:{},index:1,count:0,colWidth:0,cols:0,container:"",args:{marginTop:0,marginLeft:0,maxWidth:0,minCols:0},data:{},template:"",isInit:false};
})(this);

fml.clear = function(){
	fml.index = 1;
	fml.isInit = false;
	fml.count = 0;
	this.top = {};
	$("#"+this.container).css("height",'');
};
fml.init = function(){
	var width = parseInt($(window).width());
	var cols = parseInt(width/this.args.maxWidth);
	if(cols < this.minCols){
		cols = this.minCols;
	}
	var colWidth = width/ cols;
	colWidth = colWidth+((width % cols)- ((cols-1)* this.args.marginLeft))/cols;
	this.colWidth = colWidth;
	this.cols = cols;
	this.isinit = true;
};

fml.load = function(){
	if(this.container == ""){return;}
	if(this.template == ""){return;}
	if(this.data.length == 0 ){return;}
	if(!this.isInit){this.init();}
	$.each(this.data,function(k,v){
		fml.pushItem(v);
	});
};

fml.pushItem= function(v){
	try{
		var tmplate = $("#"+this.template).html();
		var funs = tmplate.match(/<\?\=\s*([^\>]+)\s*\?\>/g);
		for(var i=0;i<funs.length;i++){
			var fun_str = funs[i];
			var fun = fun_str.substr(3,fun_str.length-5);
			var val = eval(fun);
			tmplate = tmplate.replace(fun_str,val);
		}
		$item = $(tmplate);
		var left = (this.index-1) * this.colWidth;
		var top = this.top["index_"+this.index];
		if (top == null){top = this.args.marginTop;}
		$item.css("top",top+"px").css("left",left+"px").attr("col",this.index);
		$("#"+this.container).append($item);
		this.top["index_"+this.index]= top+v.imageHeight + 30;
		$("#"+this.container).css("height",this.top["index_"+this.index]+"px");
		this.count ++;
		if (this.index % this.cols == 0){this.index =1;}else{this.index +=1;}
	}catch(e){}
};