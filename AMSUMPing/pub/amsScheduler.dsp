<!-- AMSDSPUtils v1.0.0 -->

<HTML>
  %scope param(package='AMSUMPing')%
  <HEAD>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <META HTTP-EQUIV="Expires" CONTENT="-1">
    <LINK REL="stylesheet" TYPE="text/css" HREF="webMethods.css">
    <SCRIPT SRC="webMethods.js.txt"></SCRIPT>
  </HEAD>

  <BODY>
    <TABLE width="100%">
      <TR><TD class="menusection-Server" colspan="3">AMSUMPing &gt; Package Scheduled Tasks</TD></TR>

      %ifvar action%
        %invoke amsScheduler.dsp:setSchedule%
          %ifvar message%
      <tr><td colspan="2">&nbsp;</td></tr>
      <TR><TD class="message" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%value message%</TD></TR>
          %endif%
        %endinvoke%
      %endif%

      <TR>
        <TD colspan="2">
          <UL>
		<LI><A HREF=amsScheduler.dsp>Refresh Page</A></LI>
            <LI><A HREF=amsScheduler.dsp?action=suspend>Suspend</A></LI>
            <LI><A HREF=amsScheduler.dsp?action=resume>Resume</A></LI>
          </UL>
        </TD>
      </TR>

      <TR>
        <TD>
          <TABLE class="tableView">
            %invoke amsScheduler.util:getCurrentDateString%
            <TR><TH class="heading">Scheduled Tasks (Current Time: %value date%)</TH></TR>
            %endinvoke%
            <TR>
              <TD>
                <!-- <P>%loop -struct% %value $key% %value%<BR>%endloop%</P> -->
                <TABLE class="tableView">
                  <TR>
			  <TD class="oddcol">ID</TD>
                    <TD class="oddcol">Service</TD>
                    <TD class="oddcol">Last Error</TD>
                    <TD class="oddcol">Run As User</TD>
                    <TD class="oddcol">Type</TD>
                    <TD class="oddcol">Target</TD>
			  <TD class="oddcol">Interval</TD>
                    <TD class="oddcol">Next Run</TD>
                    <TD class="oddcol">State</TD>
                  </TR>
                  %invoke amsScheduler.dsp:getScheduleStatus%
                  %loop Tasks%
			<!-- <TR><P>%loop -struct% %value $key% %value%<BR>%endloop%</P></TR> -->
                  <TR>
        		  <script>writeTD("rowdata-l");</script>%value taskID%</TD>
                    <script>writeTD("rowdata-l");</script>%value service%</TD>
                    <script>writeTD("rowdata-l");</script>%value lastError%</TD>
                    <script>writeTD("rowdata-l");</script>%value runAsUser%</TD>
                    <script>writeTD("rowdata-l");</script>%value type%</TD>
                    <script>writeTD("rowdata-l");</script>%value target%</TD>                    
			  %ifvar type equals('repeat')%
			  <script>writeTD("rowdata-l");</script><center>%value repeatingTaskInfo/interval% secs</center></TD>
			  %endif%
			  %ifvar type equals('complex')%
			  <TD class="rowdata" colspan="1" style="padding: 0px;">
			    <table width="100%" class="tableInline" cellspacing="1" style="background-color: #ffffff">
				<tr>
			        <script>writeTD("row");</script>Months</td>
				  <script>writeTD("rowdata-l");</script>%value complexTaskInfo/months%</td>
			      </tr>
			      <tr>
			        <script>writeTD("row");</script>Days</td>
			        <script>writeTD("rowdata-l");</script>%value complexTaskInfo/daysOfMonth%</td>
			      </tr>
			      <tr>
			        <script>writeTD("row");</script>Days of Week</td>
			        <script>writeTD("rowdata-l");</script>%value complexTaskInfo/daysOfWeek%</td>
			      </tr>
			      <tr>
			        <script>writeTD("row");</script>Hours</td>
			        <script>writeTD("rowdata-l");</script>%value complexTaskInfo/hours%</td>
			      </tr>
			      <tr>
			        <script>writeTD("row");</script>Minutes</td>
			        <script>writeTD("rowdata-l");</script>%value complexTaskInfo/minutes%</td>
			      </tr>
			    </table>
			  </TD>
			  %endif%
			  <script>writeTD("rowdata-l");</script>%value nextRun%</TD>
                    <script>writeTD("rowdata-l");</script>%value execState%</TD>
                  </TR>
                  <script>swapRows();</script>
                  %endloop%
                  %endinvoke%
                </table>
              </TD>
            </TR>
          </TABLE>
        </TD>
      </TR>
    </TABLE>
  </BODY>
  %endscope%
</HTML>