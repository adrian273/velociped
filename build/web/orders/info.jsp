<%-- 
    Document   : info
    Created on : 04-11-2019, 14:01:31
    Author     : adrian
--%>
<!-- MODAL CheckOut -->
<div class="modal bd-example-modal-xl animated fadeInLeft" style="color:white;background-color: #4492e033;" id="myModalOrder" role="dialog">
    <div class="modal-dialog modal-xl bg-dark" tabindex="-1">
        <button type="button" class="btn btn-outline-info" style="float:right;border-radius: 0px;cursor: pointer !important;" 
                data-dismiss="modal">&times;</button>
                
        <!-- Modal content-->
        <div class="modal-content bg-dark">
            <div class="modal-header">
                <h4 class="modal-title">
                    Informacion de La orden N°<i class="idOrder"></i>
                </h4>
                <p>Total: <span class="monto_total">0</span></p>
            </div>
            <form id="edit-status-form">
                <div class="modal-body" id="info-order-list">
                    
                    
                </div>
            </form>
        </div>
    </div>
</div>