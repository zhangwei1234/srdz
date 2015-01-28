//注册table组件
Namespace.register("Topic.Table");
(function(){
	
	//分页功能
	Topic.Table.Page = function(container,url,currentPage,form,pageSize){
		if(container == null || container == ""){alert("参数不合法,container不能为空");return;}
		if(url == null || url == ""){alert("参数不合法,url不能为空");return;}
		if(currentPage == null || currentPage == ""){alert("参数不合法,currentPage不能为空");return;}
		if(pageSize == null || pageSize == ""){alert("参数不合法,container不能为空");return;}
		var param = {};
		if(form !=null && form !=""){param = $('form[name='+form+']').serialize();}
		param["currentPage"] = currentPage;
		param["pageSize"] = pageSize;
		Topic.loadContainer(container,true,url,param,null);
	};
	//重新加载当前页
	Topic.Table.reload = function(table){
		var pageBar = $("#"+table).find("> .pagebar");
		var container = pageBar.attr("d-container");
		var url = pageBar.attr("d-url");
		var currentPage = pageBar.attr("d-currentpage");
		var form = pageBar.attr("d-form");
		var pageSize = pageBar.attr("d-pagesize");
		Topic.Table.Page(container,url,currentPage,form,pageSize);
	};
	//全选/全不选
	Topic.Table.checkbox = function(check){
		if($("."+check).is(":checked") == true){//全选
			$("."+check+"_item").attr("checked",true);
		}else{//取消全选
			$("."+check+"_item").attr("checked",false);
		}
	};
	
	//获取表格中所有选中的数据ID(aa,bb,)
	Topic.Table.getCheckAll = function(check){
		var str = "";
		var array = $("."+check+"_item"+":checked");
		for(var i=0;i<array.length; i++){
			str += $(array[i]).attr("data-id")+",";
		}
		return str;
	};
	
	
})();

Namespace.register("Topic.Console");
(function(){
	Topic.Console.loadContent = function(url){
		Topic.loadContainer("console_container",true,url,{},null,null);
	};
	
	//批量topic图片上传组件
	Topic.Console.initUpload = function(){
		var filesCount =0;
		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : 'topic_imgs_click',
			container: document.getElementById('topic_imgs_container'),
			url : Topic.rootUrl+'console/topic/savetopicimgs',
			flash_swf_url : Topic.staticUrl+'plugin/plupload/js/Moxie.swf',
			silverlight_xap_url : Topic.staticUrl+'plugin/plupload/js/Moxie.xap',
			filters : {
				max_file_size : '10mb',
				mime_types: [
					{title : "Image Item", extensions : "jpg,gif,png"}
				]
			},
			init: {
				PostInit: function() {
					document.getElementById('topic_item_save').onclick = function() {
						var title = $("#topic_title").val();
						var group = $("#topic_group").val();
						var topicId = $("#topicId").val();
						
						if(topicId != ""){
							//上传图片信息
							uploader.settings.url = uploader.settings.url+"?topicId="+topicId;  
							uploader.start();
							return;
						}
						if(title =="" || group ==""){
							alert("清输入合法的参数");
							return;
						}
						if(title.length >7){
							alert("title 长度不能超过7个字符");
							return;
						}
						//首先保存topic
						Topic.PostAjax("console/topic/savetopic",{title:title,group:group,filesCount:filesCount},true,function(msg,response){
							var topicId = response.topicId;
							//上传图片信息
							uploader.settings.url = uploader.settings.url+"?topicId="+topicId;  
							uploader.start();
						},function(msg,response){
							alert(msg);
						});
						return false;
					};
				},

				FilesAdded: function(up, files) {
					filesCount = files.length;
					plupload.each(files, function(file) {
						$("#filelist").append("<tr><td class='active'>"+file.id+"</td><td class='active'>"+file.name+"</td><td class='active'>"+plupload.formatSize(file.size)+"</td><td id='jd_"+file.id+"' class='active'>精度</td><td id='st_"+file.id+"' class='active'></td><td class='active'><button class='btn btn-xs btn-info'><i class='icon-edit bigger-120' id='"+file.id+"' onclick='Topic.Console.setTopicHome(this)'>设置为封面</i></button></td></tr>");
					});
				},

				UploadProgress: function(up, file) {
					$("#jd_"+file.id).html("<span>" + file.percent + "%</span>");
				},

				Error: function(up, err) {
					alert("上传失败\nError #" + err.code + ": " + err.message);
				},
				
				FileUploaded:function(up, file, res){
					var obj = eval("(" + res.response + ")");
					var src = obj.img.url;
					var id = obj.img.id;
					var topicId = obj.img.topicId;
					$("#"+file.id).attr("itemId",id).attr("topic_id",topicId);
					$("#st_"+file.id).html("<img src='"+src+"' class='topic_img_st'></img>");
				}
			}
		});
		uploader.init();
	};
	
	//批量删除topic
	Topic.Console.removeTopics = function(){
		var ids = Topic.Table.getCheckAll("topic_check");
		if(ids == ""){
			alert("请勾选需要删除的topic");return;
		}
		Topic.DeleteAjax("console/topic/remove/"+ids,{},true,function(msg,response){
			alert(msg);
			//重新加载数据
			Topic.Table.reload("topic_grid_container");
		},null);
	};
	
	//设置topic 首页
	Topic.Console.setTopicHome = function(obj){
		var imgId = $(obj).attr("itemId");
		if(imgId == ""){
			alert("imgId is empty");return;
		}
		Topic.PutAjax("console/topic/settopichome/"+imgId,{},true,function(msg,response){
			alert(msg);
		},null);
	};
	
	//删除topic
	Topic.Console.removeTopic = function(topicId){
		if(topicId == ""){
			alert("删除失败,topicId is empty");return;
		}
		var ids = topicId+",";
		Topic.DeleteAjax("console/topic/remove/"+ids,{},true,function(msg,response){
			alert(msg);
			//重新加载数据
			Topic.Table.reload("topic_grid_container");
		},null);
	};
	
	Topic.Console.setTopicIndex = function(topicId){
		if(topicId == ""){
			alert("req fail,topicId is empty");return;
		}
		Topic.PutAjax("console/topic/settopicindex/"+topicId,{},true,function(msg,response){
			alert(msg);
			//重新加载数据
			Topic.Table.reload("topic_grid_container");
		},null);
	};
	
	/**
	 * 加载topic 编辑页面
	 */
	Topic.Console.loadModify = function(topicId){
		Topic.loadContainer("console_container",true,"console/topic/lodmodify",{topicId:topicId},null,null);
	};
	
	//topic添加商品初始化函数
	Topic.Console.topicCommodityInit = function(){
		$('.dialogsimgs').slimScroll({
			height: '100px'
	    });
		$('.dialogscommoditys').slimScroll({
			height: '240px'
	    });
	};
	
	Topic.Console.loadTopicCommodity = function(topicId){
		Topic.loadContainer("console_container",true,"console/topic/loadcommodity",{topicId:topicId},null,null);
	};
	
	//保存商品信息
	Topic.Console.saveCommodity = function(fname) {
		Topic.PostAjaxForm(fname,function(msg,response){
			alert(msg);
		},null);
	};
	
	//批量删除商品信息
	Topic.Console.delCommoditys = function(){
		var ids = Topic.Table.getCheckAll("commodity_check");
		if(ids == ""){
			alert("请勾选需要删除的商品列表");return;
		}
		Topic.DeleteAjax("console/commodity/"+ids,{},true,function(msg,response){
			alert(msg);
			//重新加载数据
			Topic.Table.reload("commodity_grid_container");
		},null);
	};
	
	//删除商品
	Topic.Console.delCommodity = function(id){
		if(id == ""){
			alert("删除失败,id is empty");return;
		}
		
		var ids = id+",";
		Topic.DeleteAjax("console/commodity/"+ids,{},true,function(msg,response){
			alert(msg);
			//重新加载数据
			Topic.Table.reload("commodity_grid_container");
		},null);
	};
	
	/**
	 * 给topic 添加商品
	 */
	Topic.Console.addTopicCommodity = function(topicId,commodityId){
		if($.trim($("#"+commodityId).attr("exists")) == "exists"){
			alert("该商品已经被添加不需要重复添加");return;
		}
		Topic.PutAjax("/console/topic/commodity/"+topicId+"/"+commodityId,{},true,function(msg,response){
			alert("商品添加成功");
			$("#"+commodityId).css("display","");
			$("#"+commodityId).attr("exists","exists");
		},null);
	};
	
	/**
	 * 给topic 删除商品
	 */
	Topic.Console.delTopicCommodity = function(topicId,commodityId){
		if($.trim($("#"+commodityId).attr("exists")) == "no"){
			alert("该商品还没有添加到topic,不能删除");return;
		}
		Topic.DeleteAjax("/console/topic/commodity/"+topicId+"/"+commodityId,{},true,function(msg,response){
			alert("商品删除成功");
			$("#"+commodityId).css("display","none");
			$("#"+commodityId).attr("exists","no");
		},null);
	};
	
})();