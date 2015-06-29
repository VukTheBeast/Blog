$(document).ready(function (){

  $('.submit').click(function(){
    if($('#name').val() != "" &&  $('#message').val())
    {

      //addComment();
    }
  });
});

function addComment(){
  $.post("/comment", {id: $('.id').val(), name : $('#name').val(), message: $('#message').val()});
}
