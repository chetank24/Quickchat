

<form action="/test" method="post">
<input name="message">
<input type="submit">

</form>



<#if me??>
  ${me}
</#if>
<br>
<#if you??>
  ${you}
</#if>