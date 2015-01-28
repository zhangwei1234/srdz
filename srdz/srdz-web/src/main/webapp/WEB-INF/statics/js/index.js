Namespace.register("Topic.fml");
(function(){
	
	Topic.fml.topicCount = 0;
	
	//根据数据获取一段html代码,index:当前下标从1开始,cols:每行列数
	Topic.fml.tmp = function(data,index,height,width,cols,paddingTop,top,left,href,container){
		index = Topic.fml.topicCount + index;
		var $div = $("<div></div>").addClass("poster_grid").addClass("poster_wall").attr("id",data.id);
		//计算top 和left
		var $row = (index%cols==0)? parseInt(index/cols)-1 : parseInt((index+cols)/cols)-1;
		var $column = index%cols;if($column ==0){$column=cols;}
		var $top = ($row * height) + paddingTop+($row*top);
		var $left = (($column-1)*width) + ($column * left);
		$div.css("width",width+"px").css("height",height+"px").css("top",$top+"px").css("left",$left+"px").attr("col",$column);
		var $figure = $("<figure></figure>");
		var $a = $("<a></a>").addClass("pic_load").attr("data-transition","slide").attr("href",href+data.id).css("height","221.2875px");
		var $img = $("<img></img>").attr("src",data.homeImage);
		var $figcaption = $("<figcaption></figcaption>");
		var $span =$("<span></span>").addClass("price").html(data.title+"("+data.imgCount+")");
		var $span1 =$("<span id='parise_"+data.id+"'></span>").addClass("like").addClass("icon-heart").attr("count",data.praiseCount).html(data.praiseCount).bind("click",function(){
			Topic.page.addTopicPraise(data.id);
		});
		
		$($figcaption).append($span).append($span1);
		$($a).append($img);
		$($figure).append($a).append($figcaption);
		$($div).append($figure);
		$("#"+container).append($div);
		$("#pullUp").css("top",(parseInt($top)+parseInt(height)+10)+"px");
	};
	
	//图片墙
	Topic.fml.use = function(options){
		//default options
		var DEF_OPTIONS = {height:251,maxWidth:180,minCols:2,paddingTop:1,top:5,left:2,autlod:true,ispage:true,pagesize:20,scrollPage:true,templat:Topic.fml.tmp,container:"",url:"",param:{}};
		//merge options
		options=$.extend(true, {}, DEF_OPTIONS, options || {});
		Topic.fml.width = parseInt($(window).width());
		Topic.fml.itemHref = "share/item/";
		//计算可以显示的列数
		var cols = parseInt(Topic.fml.width/parseInt(options.maxWidth));
		if (cols <2){cols =2;}
		//每列的宽度
		var width = Topic.fml.width/cols;
		width = width+((Topic.fml.width%cols)- ((cols-1)*parseInt(options.left)))/cols;
		
		Topic.fml.use.width = width;
		Topic.fml.use.cols = cols;
		Topic.fml.use.netxpage = 0;
		
		//加载数据
		Topic.fml.use.load = function(param){
			Topic.PostAjax(options.url,param,false,function(msg,response){
				
				if(response.data=="f"){
					$(".pullUpLabel").html("数据已经加载完了");$("#pullUp").unbind("click");return;
				}
				Topic.fml.use.netxpage = parseInt(response.netxtPage);
				var data = response.list;
				//遍历数据
				for(var i=0;i<data.length;i++){
					options.templat(data[i],i+1,options.height,Topic.fml.use.width,Topic.fml.use.cols,options.paddingTop,options.top,options.left,Topic.fml.itemHref,options.container);
				}
				Topic.fml.topicCount += data.length;
			},function(msg,response){
				alert("网络繁忙请稍候重试");
				$("#loading").css("display","none");
			});
		};
		
		//判断是否开启滚动条加载事件
		if(options.scrollPage){
			$("#pullUp").bind("click",function(){
				var param = options.param;
				param["page"] = Topic.fml.use.netxpage;
				param["pagesize"] = 20;
				Topic.fml.use.load(param);
			});
		}
		
		//是否自动加载
		if(options.autlod){
			var param = options.param;
			param["page"] = Topic.fml.use.netxpage;
			param["pagesize"] = 20;
			
			Topic.fml.use.load(param);
		}
	};
	
})();

//页面加载
Namespace.register("Topic.page");
(function(){
	
	//首页菜单点击
	Topic.page.addTopicPraise = function(topicId){
		Topic.PutAjax("topicPraise/"+topicId,{},false,function(msg,response){
			var count = parseInt($("#parise_"+topicId).attr("count"));
			$("#parise_"+topicId).attr("count",count+1).html(count+1);
		},null);
	};
	
	Topic.page.topicItemshow = function(){
	var height = parseInt($(window).height())-10;
	 $(".swiper-container").css("height",height+"px");
	 $(".device").css("height",height+"px");
	 $('.dialogs').slimScroll({
			height: height-30+'px'
	 });
	  var mySwiper = new Swiper('.swiper-container',{
		    pagination: '.pagination',
		    loop:true,
		    grabCursor: true,
		    paginationClickable: true
		  });
		  $('.arrow-left').on('click', function(e){
		    e.preventDefault();
		    mySwiper.swipePrev();
		  });
		  $('.arrow-right').on('click', function(e){
		    e.preventDefault();
		    mySwiper.swipeNext();
		  });
		  
	};
	
	$(document).on("pagecreate","#topic_item",function(){ //当进入topic item 页面时出发
		Topic.page.topicItemshow();
	});
})();