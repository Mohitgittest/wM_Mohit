<!-- AMSDSPUtils v1.0.0 -->

<HTML>
  %scope param(packageName='AMSUMPing')%
  <HEAD>
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta http-equiv="Expires" CONTENT="-1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
  </HEAD>

  <BODY>
    <CENTER><H1>Welcome To The Home Page For The <i>AMSUMPing</i> Package.</H1></CENTER>

    <H2>Requirements</H2>
    <UL><LI>Package Dependencies - Defined in package info.</UL>

    <H2>Version History</H2>
    <UL>
      %invoke amsGeneralServices.dsp:getReleaseNotesInfo%
      %loop releaseNotesInfo%
      <LI><A href=doc/%value fileName%><B>Release Notes %value versionInfo%</B></A>
      %endloop%
      %endinvoke%
    </UL>
  </BODY>
  %endscope%
</HTML>