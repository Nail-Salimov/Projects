$('.message a').click(function(){
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});

function validate() {
    var $result = $("#result");
    var email = $("#email").val();
    $result.text("");

    alert("Hi")
    if (validate(email)) {
        $result.text(email + " подтвержден.");
    } else {
        $result.text(email + " не подтвержден.");
    }
    return false;
}
$("#validate").bind("click", validate);