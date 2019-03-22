/**
 * 获取项目的根
 * @returns
 */
function getContextPath() {

    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
//注册表单验证
function validateRegForm(){
    var nickName = $("#registerForm input[name='nickName']").val(); 
	var password = $("#registerForm input[name='password']").val();
	var rePassword = $("#registerForm input[name='rePassword']").val();
	if(nickName == ''){
		$("#regErrorMessage span").text("用户名不能为空");
		$("#regErrorMessage").css("display","block");
		return false;		
		
	}else if(password == ''){
		$("#regErrorMessage span").text("密码不能为空");
		$("#regErrorMessage").css("display","block");
		return false;		
		
	}else if(password != rePassword){
		$("#regErrorMessage span").text("两次密码输入不一致");
		$("#regErrorMessage").css("display","block");
		return false;
	}
	//action="${pageContext.request.contextPath }/admin/doLogin.do"
	$("#regErrorMessage").css("display","none");
	return true;
}
//登录表单验证
function validateLogForm(){
    var nickName = $("#loginForm input[name='nickName']").val(); 
	var password = $("#loginForm input[name='password']").val();
	
	if(nickName == ''){
		$("#errorMessage span").text("用户名不能为空");
		$("#errorMessage").css("display","block");
		return false;		
		
	}else if(password == ''){
		$("#errorMessage span").text("密码不能为空");
		$("#errorMessage").css("display","block");
		return false;		
		
	}
	//action="${pageContext.request.contextPath }/admin/doLogin.do"
	$("#errorMessage").css("display","none");
	return true;
}
 //注册
 function register(){
	 if(validateRegForm()){
		 $("#registerForm").form('submit',{
			 success:function(data){
				 var data = $.parseJSON(data);
				 if(data.status == 200){
					 alert("注册成功");
					 $('#registerModal').modal('hide');
					 window.location.reload(); 
				 }
			 }   
		 });	 
	 }
 }
/**
 * 搜索
 */
var contextPath = getContextPath();
var moviesBySearch = [];
function search(){
				$("input[name='sign']").val('search');
				var searchText = $("#searchText").val();
				$.ajax({
					type:"POST",
					cache:false,
					data:"searchText="+searchText,
					url:contextPath+"/movie/search.do",
					success:function(data){
						moviesBySearch = data.rows;
						data.page = 1;
						$("input[name='page']").val(data.page);
						$("#total_records").text("共有" + data.total + "条记录,");
						console.log(data.total % data.perPageRows);
						var totalPages = data.total % data.perPageRows == 0 ?  data.total / data.perPageRows : (data.total / data.perPageRows < 1 ? 1 : Math.ceil(data.total / data.perPageRows));
						$("input[name='totalPages']").val(totalPages);
						$("#total_pages").text("共有" + totalPages + "页," + "当前是第" + data.page + "页");
						var html= "";
						if(moviesBySearch.length == 0){
							html += "<span style=\"font-family: fantasy;font-weight: bold;font-size: 20px;color:red\">\n" +
			                "\t\t\t\t\t暂无影片\n" +
			                "\t\t\t\t</span>";
						}else{
							for(var i = (data.page - 1) * data.perPageRows;i < (data.page * data.perPageRows) - 1;i++){
								if(i >= moviesBySearch.length){
									break;
								}
								console.log(moviesBySearch[i].id);
								html += "<div style=\"border: 1px solid #ddd;margin-top:5px;overflow: hidden;\">\n" +
				                "\t\t\t\t\n" +
				                "\t\t\t\t<div style=\"float: left;width: 30%;\">\n" +
				                "\t\t\t\t\t<img src=\""+"showImg.do?pathName="+moviesBySearch[i].img_path+"\"/>\n" +
				                "\t\t\t\t</div>\n" +
				                "\t\t\t\t<div style=\"float: left;width: 70%;\">\n" +
				                "\t\t\t\t\t<div ><a href=\"movie/detail.do?id="+moviesBySearch[i].id+"\" target=\"_blank\" style=\"text-decoration:none;\">"+moviesBySearch[i].title+"</a></div>\n" +
				                "\t\t\t\t\t<div>\n" +
				                "\t\t\t\t\t\t主演:"+moviesBySearch[i].actors+"\n" +
				                "\t\t\t\t\t</div>\n" +
				                "\t\t\t\t\t<div>\n" +
				                "\t\t\t\t\t\t<span>类型："+moviesBySearch[i].name+"</span><span style=\"margin-left: 50px;\">更新时间："+moviesBySearch[i].time+"</span>\n" +
				                "\t\t\t\t\t</div>\n" +
				                "\t\t\t\t\t<div>\n" +
				                "\t\t\t\t\t\t<p>\n" +
				                "\t\t\t\t\t\t\t剧情："+moviesBySearch[i].plot+"\n" +
				                "\t\t\t\t\t\t</p>\n" +
				                "\t\t\t\t\t</div>\n" +
				                "\t\t\t\t</div>\n" +
				                "\t\t\t\t\n" +
				                "\t\t\t</div>";
							}
						}
						$("#dynamicDiv").empty();
						$("#dynamicDiv").append(html);
					}
				});
}
//播放电影
function play(){
	var moviePath = $("input[name='moviePath']").val();
	$.post(contextPath+'/playRecords/add.do',$("#idForm").serialize(),function(data){
		if(data.status != 200){
			openLoginModal();
		}else{
			window.open(moviePath,'','fullscreen=yes,scrollbars=yes,status =yes,resizable=true');
		}
	});
}
// 刷新图片
function changeImg() {
	$("#imgObj").attr("src",getContextPath() + "/validateCode.do?_t="+new Date().valueOf());
}
/**
 * 登录
 * @returns
 */
function login(){
	if(!validateLogForm()){
		return ;
	}
	$.ajax({
		type:"POST",
		url:contextPath+"/user/login.do",
		data:$("#loginForm").serialize(),
		success:function(data){
			if(data.status == 200){
				$('#loginModal').modal('hide');
				window.location.reload();
			}else{
				$("#errorMessage").css("display","block");
				$("#imgObj").attr("src","validateCode.do?_t="+new Date().valueOf());
				$("#msgSpan").text(data.msg);
			}
		}
	});
}
/**
 * 收藏
 * @param id
 * @returns
 */
function favorite(id){
	$.ajax({
		type:"POST",
		url:contextPath+"/movie/favorite.do",
		data:"movieId="+id,
		success:function(data){
			if(data.status == 201){
				//201:用户未登录  202:用户已收藏  200:收藏成功
				openLoginModal();

			}else if(data.status == 202){
				alert("您已收藏");
			}else{
				alert("收藏成功");
				$("#favButton").text('已收藏');
				$("#favButton").attr('disabled','disabled');
			}
		}
	});
}
/**
 * 根据类型查询
 * @param type
 * @returns
 */
function queryByType(type){
	if(type != ''){
		$("input[name='type']").val(type);
	}
	$("input[name='page']").val(1);
	$("input[name='rows']").val(3);
	$.ajax({
		type:"POST",
		url:"movie/listByCon.do",
		data:$("#conditionForm").serialize(),
		success:function(data){
			console.log(data);
			var condition = data[0].condition;
			console.log(condition);
			var movies = new Array();
			for (var i = 1; i < data.length; i++) {
				movies.push(data[i]);
			}
			$("input[name='page']").val(condition.page);
			$("input[name='totalPages']").val(condition.totalPages);
			$("#total_records").text("共有"+condition.total+"条记录,");
			$("#total_pages").text("共有"+condition.totalPages+"页,"+"当前是第"+condition.page+"页");
			var html= "";
			for(var i = 0;i< movies.length;i++){
				html += "<div style=\"border: 1px solid #ddd;margin-top:5px;overflow: hidden;height: 220px;\">\n" +
					"\t\t\t\t\n" +
					"\t\t\t\t<div style=\"float: left;width: 30%;\">\n" +
					"\t\t\t\t\t<img src=\""+"showImg.do?pathName="+movies[i].img_path+"\"/>\n" +
					"\t\t\t\t</div>\n" +
					"\t\t\t\t<div style=\"float: left;width: 70%;\">\n" +
					"\t\t\t\t\t<div ><a href=\"movie/detail.do?id="+movies[i].id+"\" target=\"_blank\" style=\"text-decoration:none;\">"+movies[i].title+"</a></div>\n" +
					"\t\t\t\t\t<div>\n" +
					"\t\t\t\t\t\t主演:"+movies[i].actors+"\n" +
					"\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t<div>\n" +
					"\t\t\t\t\t\t<span>类型："+movies[i].type+"</span><span style=\"margin-left: 50px;\">更新时间："+movies[i].time+"</span>\n" +
					"\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t<div>\n" +
					"\t\t\t\t\t\t<p style=\"height:60px;overflow:hidden\">\n" +
					"\t\t\t\t\t\t\t剧情："+movies[i].plot+"\n" +
					"\t\t\t\t\t\t</p>\n" +
					"\t\t\t\t\t</div>\n" +
					"<div>" +
					"<span><img src='showImg.do?pathName=F:/temp/upload/play.png'/>"+movies[i].play_times+"</span>" +"&nbsp&nbsp&nbsp&nbsp"+
					"<span><img src='showImg.do?pathName=F:/temp/upload/favorite.png'/>"+movies[i].favorite_times+"</span>"
					+"</div>"+
					"\t\t\t\t</div>\n" +
					"\t\t\t\t\n" +
					"\t\t\t</div>"
			}
			$("#dynamicDiv").empty();
			$("#dynamicDiv").append(html);
			if(condition.type != ''){
				$("input[name='type']").val(condition.type);
			}
			if(condition.area != ''){
				$("input[name='area']").val(condition.area);
			}
			if(condition.year != ''){
				$("input[name='year']").val(condition.year);
			}

		}
	});
}

/***
 * 查询观看记录
 */
 function showPlayRecords(page,rows){
 	$("input[name='sign']").val('playRecords');
 	var params = {
 	    page:page,
        rows:rows
    };
 	$.post(contextPath + '/playRecords/list.do',params,function(data){
 		console.log(data);
 		var html = '';
 		if(data.status == 200){
 			var records = data.rows;
			if(records.length == 0){
				html += '<span style="font-family: fantasy;font-weight: bold;font-size: 20px;color:red">\n' +
					'\t\t\t\t\t暂无观看记录\n' +
					'\t\t\t\t</span>';
				$('#dynamicDiv').empty();
				$('#dynamicDiv').append(html);
				return ;
			}
			$("input[name='page']").val(data.page);
			$("input[name='totalPages']").val(data.totalPages);
			$("#total_records").text("共有" + data.total + "条记录,");
			$("#total_pages").text("共有" + data.totalPages + "页," + "当前是第" + data.page + "页");
 			for(var i = 0;i < records.length; i++){
 				html += '<div style="overflow: hidden;border: 1px solid #ddd;">\n' +
					'\t\t\t\t\t\t<div style="width: 20%;height:130px ;float: left;">\n' +
					'\t\t\t\t\t\t\t<img src="showImg.do?pathName='+records[i].img_path+'"/>\n' +
					'\t\t\t\t\t\t</div>\n' +
					'\t\t\t\t\t\t<div style="width:70%;float: left;margin-left: 50px;">\n' +
					'\t\t\t\t\t\t\t<p id="title">\n' +
					'\t\t\t\t\t\t\t\t'+records[i].title+'\n' +
					'\t\t\t\t\t\t\t</p>\n' +
					'\t\t\t\t\t\t\t<p >\n' +
					'\t\t\t\t\t\t\t\t观看到50%\n' +
					'\t\t\t\t\t\t\t</p><p >\n' +
					'\t\t\t\t\t\t\t\t最后观看时间:'+records[i].play_time+'\n' +
					'\t\t\t\t\t\t\t</p>\n' +
					'\t\t\t\t\t\t</div>\n' +
					'\t\t\t\t\t</div>';
			}
			$('#dynamicDiv').empty();
 			$('#dynamicDiv').append(html);
		}else if(data.status == -1){
			$("#loginModal").modal({backdrop: 'static', keyboard: false});
		}
	},'json');
}

/*
* 收藏记录
* */
function showFavorites(page,rows) {
    $("input[name='sign']").val('favRecords');
	var params = {
	    page:page,
        rows:rows
    };
	$.post(contextPath + '/favorite/list.do',params,function(data){
		console.log(data);
		var html = '';
		if(data.status == 200){
			var records = data.rows;
			if(records.length == 0){
				html += '<span style="font-family: fantasy;font-weight: bold;font-size: 20px;color:red">\n' +
					'\t\t\t\t\t暂无收藏记录\n' +
					'\t\t\t\t</span>';
				$('#dynamicDiv').empty();
				$('#dynamicDiv').append(html);
				return ;
			}
            $("input[name='page']").val(data.page);
            $("input[name='totalPages']").val(data.totalPages);
            $("#total_records").text("共有" + data.total + "条记录,");
            $("#total_pages").text("共有" + data.totalPages + "页," + "当前是第" + data.page + "页");
			for(var i = 0;i < records.length; i++){
				html += '<div style="overflow: hidden;border: 1px solid #ddd;">\n' +
					'\t\t\t\t\t\t<input type="hidden" name="id" value="'+records[i].movieId+'"/>\n' +
					'\t\t\t\t\t\t<div style="width: 20%;height:130px ;float: left;">\n' +
					'\t\t\t\t\t\t\t<img src="showImg.do?pathName='+records[i].img_path+'"/>\n' +
					'\t\t\t\t\t\t</div>\n' +
					'\t\t\t\t\t\t<div style="width:70%;float: left;margin-left: 50px;">\n' +
					'\t\t\t\t\t\t\t<p id="title">\n' +
					'\t\t\t\t\t\t\t\t'+records[i].title+'\n' +
					'\t\t\t\t\t\t\t</p>\n' +
					'\t\t\t\t\t\t\t<p >\n' +
					'\t\t\t\t\t\t\t\t收藏时间:'+records[i].collection_time+'\n' +
					'\t\t\t\t\t\t\t</p>\n' +
					'\t\t\t\t\t\t\t<p >\n' +
					'\t\t\t\t\t\t\t\t<a class="btn  btn-info"  href="javascript:cancelFav('+records[i].movieId+')" target="mainFrame">取消收藏</a>\t\n' +
					'\t\t\t\t\t\t\t</p>\n' +
					'\t\t\t\t\t\t</div>\n' +
					'\t\t\t\t\t</div>';
			}
			$('#dynamicDiv').empty();
			$('#dynamicDiv').append(html);
		}else if(data.status == -1){
			$("#loginModal").modal({backdrop: 'static', keyboard: false});
		}
	},'json');
}
function cancelFav(id) {
	var params = {
		id:id
	};
	$.post(contextPath+'/favorite/cancle.do',params,function (data) {
		if(data.status == 200){
			alert('取消成功');
			showFavorites(1,5);
		}
	},'json');
	
}
function openLoginModal() {
	console.log(new Date().valueOf());
	$("#imgObj").attr("src",getContextPath() + '/validateCode.do?_t='+new Date().valueOf());
	$("#loginModal").modal('show');
}