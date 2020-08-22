<div id="tbmov" style="padding:2px 5px;display: none">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="rimuoviSpedizioni()">Elimina Selezionate</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="spostaDeseleziona()">Deseleziona</a>
    <input id="cc1" class="easyui-combobox"  data-options="
           valueField: 'batchId',
           textField: 'batchId'          
          ">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="spostaSpedizioniTaskSelezione()">Conferma e aggungi a task selzionato</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="spostaSpedizioniNuovoTask()">Conferma e crea  nuovo task </a>

</div>
