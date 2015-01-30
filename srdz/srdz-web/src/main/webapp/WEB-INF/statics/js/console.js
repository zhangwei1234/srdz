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
})();