<%-- 
    Document   : footer
    Created on : 14-10-2019, 15:59:40
    Author     : adrian
--%>
</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="<%= request.getContextPath()%>"></c:set>
<script src="${path}/assets/js/jquery.js"></script>
<script src="${path}/assets/js/bootstrap.js"></script>
<script src="${path}/assets/js/script.min.js"></script>
<script>


    function load_count_shopping() {
        var count = "0";
        var orders_id = $("#orders_id").val();

        if (orders_id != undefined) {
            $.ajax({
                url: "${path}/order_items",
                data: {
                    "type": "load_counter",
                    "orders_id": orders_id
                },
                type: "GET",
                dataType: "html"
            }).done(function (res) {
                count = res;
                //alert(count)
                //$(".count-shopping-cart").html(count)
                count === '' ? "0" : $(".count-shopping-cart").html(count); 
            }).fail(function (jqXHR, textStatus, error) {
                alert(textStatus);
            });
        } else {
            $(".count-shopping-cart").html(count);
        }
    }

</script>
</body>
</html>