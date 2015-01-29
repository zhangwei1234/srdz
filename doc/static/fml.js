(function(a,b){
	a.fml = {version:"1.0",top:{},index:1,colWidth:0,cols:0,container:"",args:{marginTop:0,marginLeft:0,maxWidth:0,minCols:0},data:{},template:"",isInit:false};
})(this);

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
	if(this.data["tInfo"].length == 0 ){return;}
	if(!this.isInit){this.init();}
	$.each(this.data["tInfo"],function(k,v){
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
		this.top["index_"+this.index]= top+v.pic_height + 70;
		if (this.index % this.cols == 0){this.index =1;}else{this.index +=1;}
	}catch(e){}
};