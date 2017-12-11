$(document).ready(function() {
        $('#searchbox').keyup(function() {
                $.ajax({
                			url : '/search',
                			data : {
                				searchbox : $('#searchbox').val()
                			},
                			success : function(responseText) {
                				$('#ajaxSearchResponse').html(responseText);
                			}
                		});
        });
})