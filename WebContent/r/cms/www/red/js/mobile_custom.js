var selector = {
	layInput : $('.search-box'),
	tjBox : $('.choose'),
	chooseBox : $('.filter-box '),
	listBox: $('.section')
}
/*首页*/
var index = {
	init:function(){
		$.extend(this,selector);
		this.topFocus();
		this.layLoad();
		this.goBack();
	},
	topFocus:function(){
	 	var _this = this;
	 	_this.layInput.find('input').focusin(function(event) {
	 		_this.layInput.find('.txt').hide();
	 	});
	 	_this.layInput.find('input').focusout(function(event) {
	 		if($(this).val() == ''){
	 			_this.layInput.find('.txt').show();
	 		}
	 	});
	 	_this.layInput.find('.txt').click(function(){
	 		$(this).hide();
	 		_this.layInput.find('input').focus();
	 	});
	 },
    layLoad:function(){
		 $('img.lazy').lazyload({
			 placeholder: "http://pic.itrip.com/activity/921a/images/loading2.gif",
			 effect: "fadeIn",
			 threshold: 200,
			 skip_invisible: false
		 });
	},
	goBack:function(){
		$('#backBtn').click(function() {
			history.go(-1);
	    });
	}
	 
}

/*列表*/
var list = {
	init:function(){
		$.extend(this,selector);
		this.qhList();
		this.goBack();
	},
	qhList:function(){
		var _this = this,
			  tjBtn = _this.tjBox.find('.footer-tab');
		tjBtn.click(function(){
			var	_index = $(this).index();
			tjBtn.removeClass('blue');
			$(this).addClass('blue');
			_this.listBox.addClass('hide');
			_this.listBox.eq(_index).removeClass('hide');
			_this.viewMore(_this.listBox.eq(_index));
		})	
	},
	goBack:function(){
		$('#backBtn').click(function() {
			history.go(-1);
	    });
	}
}
function gotop(){
	var height = $(window).height();
	$(window).scroll(function(event) {
		$('.gotop').hide();
		if($(window).scrollTop() > height){
			$('.gotop').show();
		}
	});
	$(".gotop").click(function(){
		$("body,html").animate({
          scrollTop: 0
      },1000);
	});
}