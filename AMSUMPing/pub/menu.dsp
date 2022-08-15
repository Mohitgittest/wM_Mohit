<!-- AMSDSPUtils v1.0.0 -->

<html>
  %scope param(packageName='AMSUMPing')%
  <head>
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta http-equiv="Expires" CONTENT="-1">
    <link rel="stylesheet" type="text/css" href="webMethods.css">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <script src="menu.js.txt"></script>
    <style>body { border-top: 1px solid #97A6CB; }</style>
  </head>
  
  <body class="menu" onload="initMenu('GeneralInfo');">
    <P>
    <table WIDTH="100%" cellspacing=0 cellpadding=1 border=0>
    
      <!-- Subtitle -->
      <tr><td class="menusection-Server"><img src="images/blank.gif" width="4" height="1" border="0">AMSUMPing</td></tr>
      
      <!-- General Info -->
      %scope param(itemName='GeneralInfo')%
      <TR>
        <TD id="%value itemName%" class="menuitem" onmouseover="menuMouseOver(this, '%value itemName%');" onmouseout="menuMouseOut(this);" onclick="document.all['a%value itemName%'].click();">
          <nobr>
            <img valign="middle" src="images/blank.gif" width="4" height="1" border="0">
            <img valign="middle" id="%value itemName%" name="%value itemName%" src="images/blank.gif" height="8" width="8" border="0">
            <A id="a%value itemName%" TARGET="body" HREF="info.dsp?packageName=AMSUMPing" onclick="menuMove('%value itemName%', 'body'); return true;">
            <span class="menuitemspanie">General Info</span></a>
          </nobr>
        </TD>
      </TR>
      %endscope%
      
      <!-- Config -->
      %invoke amsGeneralServices.dsp:hasLoadConfigService%
      %ifvar result equals('true')%
      %scope param(itemName='Config')%
      <tr>
        <td id="%value itemName%" class="menuitem" onmouseover="menuMouseOver(this, '%value itemName%');" onmouseout="menuMouseOut(this);" onclick="document.all['a%value itemName%'].click();">
          <nobr>
          <img valign="middle" src="images/blank.gif" width="4" height="1" border="0">
          <img valign="middle" id="%value itemName%" name="%value itemName%" src="images/blank.gif" height="8" width="8" border="0">
          <a id="a%value itemName%" target="body" href="/invoke/amsUMPing.config:loadConfig" onclick="menuMove('%value itemName%', 'body'); return true;">
          <span class="menuitemspan">Config. Settings</span></a>
          </nobr>
        </td>
      </tr>
      %endscope%
      %endif%
      %endinvoke%
      
      <!-- Scheduler -->
      %invoke amsGeneralServices.dsp:hasSched%
      %ifvar result equals('true')%
      %scope param(itemName='Scheduler')%
      <tr>
        <td id="%value itemName%" class="menuitem" onmouseover="menuMouseOver(this, '%value itemName%');" onmouseout="menuMouseOut(this);" onclick="document.all['a%value itemName%'].click();">
          <nobr>
            <img valign="middle" src="images/blank.gif" width="4" height="1" border="0">
            <img valign="middle" id="%value itemName%" name="%value itemName%" src="images/blank.gif" height="8" width="8" border="0">
            <a id="a%value itemName%" target="body" href="amsScheduler.dsp" onclick="menuMove('%value itemName%', 'body'); return true;">
            <span class="menuitemspan">Scheduler</span></a>
          </nobr>
        </td>
      </tr>
      %endscope%
      %endif%
      %endinvoke%
      
      <!-- Custom DSPs -->
      %invoke amsGeneralServices.dsp:getCustomDSPs%
      %loop customDSPs%
      <tr>
        <td id="%value filename%" class="menuitem" onmouseover="menuMouseOver(this, '%value filename%');" onmouseout="menuMouseOut(this);" onclick="document.all['a%value filename%'].click();">
          <nobr>
            <img valign="middle" src="images/blank.gif" width="4" height="1" border="0">
            <img valign="middle" id="%value filename%" name="%value filename%" src="images/blank.gif" height="8" width="8" border="0">
            <a id="a%value filename%" target="body" href="%value filename%?packageName=AMSUMPing" onclick="menuMove('%value filename%', 'body'); return true;">
            <span class="menuitemspan">%value description%</span></a>
          </nobr>
        </td>
      </tr>
      %endloop%
      %endinvoke%
    </table>
  </body>
  %endscope%
</html>