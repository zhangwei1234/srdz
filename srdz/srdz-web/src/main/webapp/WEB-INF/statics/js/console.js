//注册table组件
Namespace.register("Topic.Table");
(function(){
	//分页功能
	Topic.Table.Page = function(container,url,currentPage,form,pageSize){
		if(container == null || container == ""){alert("参数不合法,container不能为空");return;}
		if(url == null || url == ""){alert("参数不合法,url不能为空");return;}
		if(currentPage == null || currentPage == ""){alert("参数不合法,currentPage不能为空");return;}
		if(pageSize == null || pageSize == ""){alert("参数不合法,pageSize不能为空");return;}
		var param = {};
		if(form !=null && form !=""){
			param = $('form[name='+form+']').serialize();
			param+="&currentPage="+currentPage;
			param+="&pageSize="+pageSize;
		}else{
			param["currentPage"] = currentPage;
			param["pageSize"] = pageSize;
		}
		
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
	Topic.Console.getCheckAll = function(cls){
		var str = "";
		var array = $("."+cls+":checked");
		for(var i=0;i<array.length; i++){
			str += $(array[i]).attr("data-id")+",";
		}
		return str;
	};
	
	Topic.Console.loadNewGroup = function(){
		Topic.toPage(true,"console/group/loadNew",{},null);
	};
	Topic.Console.loadUpdateGroup = function(id){
		Topic.toPage(true,"console/group/loadUpdate",{groupId:id},null);
	};
	Topic.Console.saveGroup = function(){
		Topic.PostAjaxForm("GROUP_ADD",function(msg){
			alert(msg);
			Topic.goBack('console/group/load');
		},function(msg){
			alert(msg);
		});
	};
	Topic.Console.updateGroup = function(){
		Topic.PostAjaxForm("GROUP_UPDATE",function(msg){
			alert(msg);
			Topic.goBack('console/group/load');
		},function(msg){
			alert(msg);
		});
	};
	Topic.Console.removeGroup = function(id){
		Topic.PostAjax("console/group/remove",{groupId:id},true,function(msg){
			alert(msg);
			Topic.goBack('console/group/load');
		},null);
	};
	Topic.Console.ascTdesc= function(index,flag){
		var desc = "";
		var asc = "";
		if(flag == 1){//升序
			asc = $("#index-"+index).attr("gid");
			desc = $("#index-"+(index-1)).attr("gid");
		}else{//降序
			asc = $("#index-"+(index+1)).attr("gid");
			desc = $("#index-"+index).attr("gid");
		}
		if(desc == "" || typeof(desc) == "undefined"){alert("已经最高了,不能再升了");return;}
		if(asc == "" || typeof(asc) == "undefined"){alert("已经最低了,不能再降了");return;}
		
		Topic.PostAjax("console/group/ascTdesc", {ascId:asc,descId:desc}, true, function(msg){
			alert(msg);
			Topic.goBack('console/group/load');
		},null);
	};
	
	Topic.Console.loadType = function (){
		var groupId = $("#group").val();
		if(groupId == "" || typeof(groupId) == "undefined"){
			Topic.goBack('console/type/');
		}else{
			Topic.goBack('console/type/'+groupId);
		}
	};
	
	Topic.Console.loadNewType = function(){
		Topic.toPage(true,"console/type/loadNew",{},null);
	};
	
	Topic.Console.loadTypeUpdate = function(typeId){
		Topic.toPage(true,"console/type/"+typeId+"/loadUpdate",{},null);
	};
	
	Topic.Console.removeType = function(id){
		Topic.DeleteAjax("console/type/"+id,{},true,function(msg){
			alert(msg);
			Topic.Console.loadType();
		},null);
	};
	
	Topic.Console.ascTdescType = function(index,flag){
		var desc = "";
		var asc = "";
		if(flag == 1){//升序
			asc = $("#index-"+index).attr("gid");
			desc = $("#index-"+(index-1)).attr("gid");
		}else{//降序
			asc = $("#index-"+(index+1)).attr("gid");
			desc = $("#index-"+index).attr("gid");
		}
		if(desc == "" || typeof(desc) == "undefined"){alert("已经最高了,不能再升了");return;}
		if(asc == "" || typeof(asc) == "undefined"){alert("已经最低了,不能再降了");return;}
		
		Topic.PutAjax("console/type/"+asc+"/"+desc, {}, true, function(msg){
			alert(msg);
			Topic.Console.loadType();
		},null);
	};
	
	Topic.Console.saveType = function(){
		Topic.PostAjaxForm("TYPE_ADD",function(msg){
			alert(msg);
			Topic.Console.loadType();
		},function(msg){
			alert(msg);
		});
	};
	Topic.Console.updateType = function(){
		Topic.PostAjaxForm("TYPE_UPDATE",function(msg){
			alert(msg);
			Topic.Console.loadType();
		},function(msg){
			alert(msg);
		});
	};
	
	Topic.Console.reloadType = function(){
		$("#type").empty();
		var groupId = $("#group").val();
		Topic.GetAjax("console/type/"+groupId,{},false,function(msg,response){
			var types = response["types"];
			for(var i=0;i<types.length;i++){
				var type = types[i];
				$("#type").append("<option value='"+type.id+"'>"+type.name+"("+type.remark+")</option>");
			}
			//刷新数据列表
			Topic.Table.reload("product_grid_container");
		},null);
	};
	
	Topic.Console.loadProduct = function(){
		Topic.goBack('console/product/');
	};
	
	Topic.Console.loadNewProduct = function(){
		Topic.toPage(true,"console/product/loadNew",{},null);
	};
	
	//获取图片的真是size
	Topic.Console.imageSize = function (){
		try{
			$("<img/>").attr("src", $("#product_image").val()).load(function() {
				var height = parseInt(this.height);
				if(height > 230){
					height = 230+Math.round(Math.random()*50);
				}
				$("#product_image_height").val(height);
				$("#product_image_width").val(this.width);
			});
		}catch(e){
			alert("获取图片尺寸失败");
		}
	};
	
	Topic.Console.saveProduct = function(){
		//获取所有属性信息
		var types = Topic.Console.getCheckAll("type_checked");
		if (types == ""){alert("请选择商品所属的分类");return;}
		if($("#product_image_height").val() == ""){alert("请先获取图片尺寸");return;};
		if($("#product_image_width").val() == ""){alert("请先获取图片尺寸");return;};
		$("#type_ids").val(types);
		Topic.PostAjaxForm("PRODUCT_ADD",function(msg){
			alert(msg);
			Topic.Console.loadProduct();
		},function(msg){
			alert(msg);
		});
	};
	
	Topic.Console.loadUpdateProduct = function(id){
		Topic.toPage(true,"console/product/"+id+"/loadUpdate",{},null);
	};
	
	Topic.Console.updateProduct = function(){
		//获取所有属性信息
		var types = Topic.Console.getCheckAll("type_checked");
		if (types == ""){alert("请选择商品所属的分类");return;}
		if($("#product_image_height").val() == ""){alert("请先获取图片尺寸");return;};
		if($("#product_image_width").val() == ""){alert("请先获取图片尺寸");return;};
		$("#type_ids").val(types);
		Topic.PostAjaxForm("PRODUCT_UPDATE",function(msg){
			alert(msg);
			Topic.Console.loadProduct();
		},function(msg){
			alert(msg);
		});
	};
	
	Topic.Console.batchDelProduct = function(ids){
		if(ids == ""){//批量删除
			ids = Topic.Table.getCheckAll("product_check");
		}
		
		if(ids == ""){alert("请选择要删除的商品数据");return;}
		
		Topic.DeleteAjax("console/product/"+ids,{},true,function(msg){
			alert(msg);
			Topic.Table.reload('product_grid_container');
		},null);
	};
	
	Topic.Console.delProduct = function(id){
		Topic.Console.batchDelProduct(id+",");
	};
	
	Topic.Console.loadAdvertising = function(){
		Topic.goBack('console/advertising');
	};
	
	Topic.Console.loadNewAdvertising = function (){
		Topic.goBack('console/advertising/loadNew');
	};
	
	Topic.Console.saveAdvertising = function (){
		Topic.PostAjaxForm("ADVERTISING_ADD",function(msg){
			alert(msg);
			Topic.Console.loadAdvertising();
		},function(msg){
			alert(msg);
		});
	};
	
	Topic.Console.loadUpdateAdvertising = function (id){
		Topic.goBack('console/advertising/'+id+'/loadUpdate');
	};
	
	Topic.Console.updateAdvertising = function (){
		Topic.PostAjaxForm("ADVERTISING_UPDATE",function(msg){
			alert(msg);
			Topic.Console.loadAdvertising();
		},function(msg){
			alert(msg);
		});
	};
	
	Topic.Console.batchDelAdvertising = function(ids){
		if(ids == ""){//批量删除
			ids = Topic.Table.getCheckAll("advertising");
		}
		
		if(ids == ""){alert("轻选择要删除的广告数据");return;}
		
		Topic.DeleteAjax("console/advertising/"+ids,{},true,function(msg){
			alert(msg);
			Topic.Console.loadAdvertising();
		},null);
	};
	
	Topic.Console.batchActiveAdvertising = function (){
		var ids = Topic.Table.getCheckAll("advertising");
		if(ids == ""){alert("请选择要启用的广告数据");return;}
		Topic.PutAjax("console/advertising/"+ids+"/active",{},true,function(msg){
			alert(msg);
			Topic.Console.loadAdvertising();
		},null);
	};
	
	Topic.Console.batchDisabledAdvertising = function (){
		var ids = Topic.Table.getCheckAll("advertising");
		if(ids == ""){alert("请选择要禁用的广告数据");return;}
		Topic.PutAjax("console/advertising/"+ids+"/disabled",{},true,function(msg){
			alert(msg);
			Topic.Console.loadAdvertising();
		},null);
	};
	
	Topic.Console.initAdvertising = function (){
		$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
	};
})();