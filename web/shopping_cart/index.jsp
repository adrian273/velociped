<%-- 
    Document   : index
    Created on : 24-10-2019, 15:33:39
    Author     : adrian
--%>
<%@page import="helpers.AppHelpers"%>
<%@page import="java.sql.ResultSet"%>
<%
    HttpSession ses = request.getSession();
    String order_id = "";
    order_id = String.valueOf(ses.getAttribute("order_id"));
    AppHelpers help = new AppHelpers();
%>
<jsp:include page="../layouts/header.jsp"></jsp:include>
    <style>
        .table-hover > tbody > tr:hover {
            color: #007bff;
        }
    </style>
    <div class="row">
        <input type="hidden" id="orders_id" value="<%= order_id%>">
    <div class="col-8">  
        <div class="card bg-dark">
            <div class="card-header text-center text-uppercase">
                Total de productos Agregados: 
                <span class="count-shopping-cart">0</span> 
                <i class="fas fa-shopping-bag"></i>
            </div>
            <div class="card-body">
                <table class="table table-hover table-borderless"> 
                    <thead>
                        <tr>
                            <th colspan="4">
                                <div id="msg-shooping-cart"> </div>
                            </th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <thead class="bg-info">
                        <tr>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody class="text-white">
                        <%
                            ResultSet r = (ResultSet) request.getAttribute("rows_cart");
                            while (r.next()) {
                        %>
                        <tr id="row_order<%= r.getString("oi.id")%>">
                    <input type="hidden" id="stock<%= r.getString("oi.id")%>" value="<%= r.getString("pro.stock")%>">
                    <td>
                        <label for=""> <%= r.getString("pro.name")%> </label>
                        <input type="hidden" name="name" id="name<%= r.getString("oi.id")%>" disabled="" 
                               class="form-control" value="<%= r.getString("pro.name")%>">
                    </td>
                    <td>
                        <%
                            int oi_price = (int) Double.parseDouble((String) r.getString("pro.price"));
                            int quantity = Integer.parseInt(r.getString("oi.quantity"));
                            int sub_total_pro = oi_price * quantity;
                        %>
                        <label for="" id="sub_total_pro<%= r.getString("oi.id")%>label" > 
                            <%= help.priceFormat(String.valueOf(sub_total_pro))%>
                        </label>
                        <input type="hidden" name="price_pro" id="price_pro<%= r.getString("oi.id")%>" 
                               disabled="" class="form-control" value="<%= r.getString("pro.price")%>">
                    </td>
                    <td>
                        <input style="width:100px;" min="1" onchange="updateQuantity(this, <%= r.getString("oi.id")%>)" 
                               type="number" name="quantity" class="form-control" value="<%= quantity%>">
                    </td>
                    <td>
                        <button class="btn btn-outline-danger" onclick="deleteOrder(<%= r.getString("oi.id")%>)"> 
                            <i class="fas fa-trash-alt"></i> 
                        </button>
                    </td>
                    </tr>

                    <% }%>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th colspan="4">
                                <div class="msg-empty-cart">
                                </div> 
                            </th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </tfoot>
                </table>

            </div>
        </div>
    </div>
    <!-- monto total ---------------------------------------------------------->                
    <div class="col-4"> 
        <div class="card bg-dark">
            <div class="card-header text-center text-uppercase">
                <h1> Orden N° <%= order_id%>  </h1>
            </div>
            <div class="card-body">
                <p> Total Productos: <span class="count-shopping-cart"></span> </p>
                <p>Monto Total: <span class="monto_total">0</span></p>
            </div>
            <div class="card-footer">
                <!--<button class="btn btn-outline-info btn-block" id="checkout-shopping-cart"> Realizar pedido </button>-->
                <button class="btn btn-outline-info btn-block" id="checkout-shopping-cart"> Realizar pedido </button>
            </div>
        </div>
    </div>
    <!-- ------------------------------------------------------------ --------->
</div>
<!-- MODAL CheckOut -->
<div class="modal bd-example-modal-xl" style="color:white;background-color: #4492e033;" id="myModal" role="dialog">
    <div class="modal-dialog modal-xl bg-dark" tabindex="-1">
        <button type="button" class="btn btn-outline-info" style="float:right;border-radius: 0px;cursor: pointer !important;" 
                data-dismiss="modal">&times;</button>
                
        <!-- Modal content-->
        <div class="modal-content bg-dark">
            <div class="modal-header">
                <h4 class="modal-title">
                    Datos para el envio y confirmacion de la compra
                </h4>
                <p>Total: <span class="monto_total">0</span></p>
            </div>
            <form id="checkout-cart">
                <input type="hidden" name="type" value="checkout">
                <input type="hidden" name="shipping" value="" id="shipping">
                <input type="hidden" name="orders_id" value="<%= order_id %>">
                <div class="modal-body">
                    <div class="msg-checkout-cart"></div>
                    <h4>Direccion</h4>
                    <hr>
                    <div class="row">
                        <div class="col-6">
                            <div class="form-group">
                                <label for="" class="form-check-label"><b>Region</b></label>
                                <select name="region" onchange="changeTerritory(this.value)" id="region" class="form-control">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-group">
                                <label for="" class="form-check-label"><b>Comuna</b></label>
                                <select name="comuna" id="comuna" class="form-control">
                                    <option value=""></option>
                                    <option value="a">A</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="row">
                                <div class="col-9">
                                    <div class="form-group">
                                        <label for="" class="form-check-label">Direccion</label>
                                        <input type="text" class="form-control" id="address" name="address">
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="form-group">
                                        <label for="" class="form-check-label">Num Casa</label>
                                        <input type="text" class="form-control" name="num">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-group">
                                <label for="" class="form-check-label">Telefono</label>
                                <input type="text" class="form-control" name="phone">
                            </div> 
                        </div>
                    </div>
                    <h4>Metodo de pago</h4>
                    <hr>
                    <div class="col-12">
                        <div class="form-check form-check-inline">
                            <label style="cursor: pointer;">
                                <input class="form-check-input payments_type" type="radio" 
                                       name="payments_type" id="caja-vecina" value="2">
                                <!-- <label class="form-check-label" for="caja-vecina">Caja Vecina</label> -->
                                <img src="https://d31dn7nfpuwjnm.cloudfront.net/images/valoraciones/0028/4494/como-funcionan-depositos-otros-servicios-caja-vecina.png?1508325636" 
                                     width="35%;" class="img-responsive img-fluid img-thumbnail" alt=""></label>
                            <label style="cursor:pointer;">
                                <input class="form-check-input payments_type" type="radio" 
                                       name="payments_type" id="web-pay" value="3">
                                <%--<label class="form-check-label" for="web-pay">Web Pay</label> &nbsp; --%>
                                <img src="https://cdn.shopify.com/s/files/1/0013/9935/7503/files/webpay-logo1_faf9fd07-8683-4715-a39b-e990828e3703_large.png?v=1522242608"
                                     width="35%;" class="img-responsive img-fluid img-thumbnail mb-2" alt=""> </label>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                    <button type="button" id="confirm-order" class="btn btn-outline-success"> Confirmar <i class="fas fa-check"></i> </button>
                </div>
            </form>
        </div>
    </div>
</div>
                                <div id="debug"></div>
<jsp:include page="../layouts/footer.jsp"></jsp:include>