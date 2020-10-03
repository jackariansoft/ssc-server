<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aggiorna Info Ordini</title>
    </head>

    <body>
        <div class="container">
            <div class="col-md-8 col-md-offset-2">
                <h3>Aggiorna Info Ordini</h3>
                <form id="sampleUploadFrm" method="POST" action="<%= request.getContextPath()%>/import-ordini" enctype="multipart/form-data">
                    <!-- COMPONENT START -->
                    <div class="form-group">
                        <div class="input-group input-file" name="file">
                            <span class="input-group-btn">
                                <button class="btn btn-default btn-choose" type="button">Scegli File</button>
                            </span> 
                            <input type="text" class="form-control"
                                   placeholder='Choose a file...' /> <span class="input-group-btn">
                                <button class="btn btn-warning btn-reset" type="button">Reset</button>
                            </span>
                        </div>
                    </div>
                    <!-- COMPONENT END -->
                    <div class="form-group">
                        <button type="button" class="btn btn-primary pull-right" id="uploadBtn">Invia</button>
                        <button type="reset" class="btn btn-danger">Reset</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                function bs_input_file() {
                    $(".input-file").before(
                            function () {
                                if (!$(this).prev().hasClass('input-ghost')) {
                                    var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                                    element.attr("name", $(this).attr("name"));
                                    element.change(function () {
                                        element.next(element).find('input').val((element.val()).split('\\').pop());
                                    });
                                    $(this).find("button.btn-choose").click(function () {
                                        element.click();
                                    });
                                    $(this).find("button.btn-reset").click(function () {
                                        element.val(null);
                                        $(this).parents(".input-file").find('input').val('');
                                    });
                                    $(this).find('input').css("cursor", "pointer");
                                    $(this).find('input').mousedown(function () {
                                        $(this).parents('.input-file').prev().click();
                                        return false;
                                    });
                                    return element;
                                }
                            }
                    );
                }

                bs_input_file();

                $("#uploadBtn").on("click", function () {
                    var url = $('#sampleUploadFrm').attr('action');
                    var form = $("#sampleUploadFrm")[0];
                    var data = new FormData(form);
                    $.ajax({
                        type: "POST",
                        encType: "multipart/form-data",
                        url: url,
                        cache: false,
                        processData: false,
                        contentType: false,
                        data: data,
                        beforeSend: function () {
                            $.loader({
                                className: "blue-with-image",
                                content: ''
                            });
                        },
                        success: function (resp) {
                            var respObj = JSON.parse(resp.Response);
                            $.messager.alert('Ordini Aggiornati', 'File: ' + respObj.file_name, 'info');
                        },
                        error: function (xhr, status, er) {
                                    $.loader('close');
                                    $.messager.alert('Invio Ordini per Task', 'Errore inaspettato Ajax request: ' + xhr.responseText, 'error');
                                    //SendEmail(String(resp.responseText));
                                    //           
                                },
                        complete: function (rest, status) {
                            $.loader('close');

                        }
                    });
                });
            });
        </script>
    </body>
</html>