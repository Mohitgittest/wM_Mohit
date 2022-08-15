package amsUMPing;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Enumeration;
import java.util.ArrayList;
import com.wm.app.b2b.server.dispatcher.trigger.TriggerFacade;
import com.wm.app.b2b.server.dispatcher.trigger.TriggerFacade.TriggerRunTimeStatus;
import com.pcbsys.nirvana.client.*;
import com.pcbsys.nirvana.nAdminAPI.*;
import com.pcbsys.nirvana.nAdmin.*;
import java.util.Iterator;
// --- <<IS-END-IMPORTS>> ---

public final class util

{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void getTopicAlerts (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getTopicAlerts)>> ---
		// @sigtype java 3.5
		// [i] field:0:required realmUrl
		// [i] field:0:required topic_threshold
		// [o] field:0:optional channelNames
		// [o] field:0:optional exception
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	realmUrl = IDataUtil.getString( pipelineCursor, "realmUrl" );
			String	topic_threshold = IDataUtil.getString( pipelineCursor, "topic_threshold" );
		pipelineCursor.destroy();
		
		String[] RNAME = {realmUrl};
		nSessionAttributes nsa = null;
		nSession lSession = null;
		nRealmNode rNode = null;		
		String[] channelNames = null;
		int TOPIC_THRESHOLD=Integer.parseInt(topic_threshold);
		
		StringBuilder finalTopic=new StringBuilder("");
		
		nRealmNode admin=null;
		
		IDataCursor idcin = pipeline.getCursor();
		try {
			
			admin = new nRealmNode(new nSessionAttributes(RNAME));
		    
		
		    // There is a 10 second update interval for the stats.
		    
		    Thread.sleep(10000);
		
		    //get all the channels
		    
		    nChannelAttributes[] channels = admin.getSession().getChannels();
		    
		
		    //loop over channels and call the walkTopicNode method
		    for (nChannelAttributes channel : channels) {
		      nLeafNode leaf = (nLeafNode) admin.findNode(channel.getName());
		      if (leaf instanceof nTopicNode) {
		        
		        String s=walkTopicNode((nTopicNode)leaf,TOPIC_THRESHOLD); 
		        finalTopic.append(s);
		        
		      }
		    }
		    
		    
		    if((finalTopic.toString()).equals("") || (finalTopic.toString().equals(null)))
		   	{
			//do nothing
			
		   	}
		   	else
		   	{
		   		StringBuilder finalOutput=new StringBuilder("Document Name**Trigger Name**Client ID-->Pending Event Count");
		   		finalOutput.append("\n");
		   		finalOutput.append("\n");
		   		finalOutput.append(finalTopic.toString());
		   		IDataUtil.put(idcin, "channelNames", finalOutput.toString());
		   	}
			
			idcin.destroy();
		
			admin.close();
			
		} catch (Exception e) {
			IDataCursor idcatch = pipeline.getCursor();
			IDataUtil.put(idcatch, "exception", e.toString());
			idcatch.destroy();
			admin.close();
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void listTopics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listTopics)>> ---
		// @sigtype java 3.5
		// [i] field:0:required realmUrl
		// [o] field:1:required topics
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	realmUrl = IDataUtil.getString( pipelineCursor, "realmUrl" );
		pipelineCursor.destroy();
		
		String topics[]={""};
		String[] RNAME={realmUrl};
		StringBuilder leafData = new StringBuilder("");
		nRealmNode realm = null;
		IDataCursor pipelineCursor_1 = null;
		try {
			nSessionAttributes nsa=new nSessionAttributes(RNAME);
			realm = new nRealmNode(nsa);
			leafData.append(searchNodes((nContainer)realm));
			topics=leafData.toString().split(" ");
			
			pipelineCursor_1 = pipeline.getCursor();
				IDataUtil.put( pipelineCursor_1, "topics", topics );
		} catch(Exception e) {
				throw new ServiceException(e.toString()); 
		}finally {
			realm.close();
			pipelineCursor_1.destroy();
		}
		   
		
		
		// pipeline
		
		
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void monitorUMEvents (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(monitorUMEvents)>> ---
		// @sigtype java 3.5
		// [i] field:0:required realmUrl
		// [i] field:1:required topics
		// [o] record:1:required Details
		// [o] - field:0:required name
		// [o] - field:0:required subsCount
		// [o] - record:1:required Subscribers
		// [o] -- field:0:required name
		// [o] -- field:0:required eventCount
		// [o] -- field:0:required connectionCount
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	realmUrl = IDataUtil.getString( pipelineCursor, "realmUrl" );
			String[]	topics = IDataUtil.getStringArray( pipelineCursor, "topics" );
			
		pipelineCursor.destroy();
		
		String[] RNAME={realmUrl};
		nSession mySession = null;
		IDataCursor pipelineCursor_1 = null;
		try {
			nSessionAttributes nsa=new nSessionAttributes(RNAME);
			mySession=nSessionFactory.create(nsa);  
		    mySession.init();  
			nChannelAttributes cattrib = new nChannelAttributes();	
			//nDurableAttributes cattrib;
			pipelineCursor_1 = pipeline.getCursor();
			IData[]	Details = new IData[topics.length];
		
			for(int i=0;i<topics.length;i++) {
				cattrib.setName(topics[i]);
				nChannel myChannel=mySession.findChannel(cattrib);  
			  // nNamedObject nno[]=myChannel.getNamedObjects();
				//nDurableManager myManager=mySession.findChannel(cattrib);
				nDurable nno[]=myChannel.getDurableManager().getAll();
				
				Details[i] = IDataFactory.create();
				IDataCursor DetailsCursor = Details[i].getCursor();
				IDataUtil.put( DetailsCursor, "name", topics[i]);
				IDataUtil.put( DetailsCursor, "subsCount", Integer.toString(nno.length));
				IData[]	Subscribers = new IData[nno.length];
				
				for(int s=0;s<nno.length;s++){
		
					// Details.Subscribers
					Subscribers[s] = IDataFactory.create();
					IDataCursor SubscribersCursor = Subscribers[s].getCursor();
					IDataUtil.put( SubscribersCursor, "name", nno[s].getName() );
					IDataUtil.put( SubscribersCursor, "eventCount", Long.toString(nno[s].getOutstandingEvents()) );
			//		IDataUtil.put( SubscribersCursor, "sharedEventCount", Long.toString(nno[s].getSharedNamedObjectOutstandingEvents()) );
					IDataUtil.put( SubscribersCursor, "connectionCount", "-1" );
		
		
					SubscribersCursor.destroy();
					
				}
		
				IDataUtil.put( DetailsCursor, "Subscribers", Subscribers );				
		
				DetailsCursor.destroy();		  
				
			}
		
		
				IDataUtil.put( pipelineCursor_1, "Details", Details );
		} catch(Exception e) {
				throw new ServiceException(e.toString()); 
		}finally {
			pipelineCursor_1.destroy();
			mySession.close();
		}
		   
		
		
		// pipeline
		
		
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void triggerCheck (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(triggerCheck)>> ---
		// @sigtype java 3.5
		// [i] field:0:required triggerName
		// [o] field:0:required thread
		// [o] field:0:required note
		// [o] field:0:required docCount
		// [o] field:0:required volDocCount
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	triggerName = IDataUtil.getString( pipelineCursor, "triggerName" );
			int threadCount = 0; 
		pipelineCursor.destroy();
		TriggerFacade trig = new TriggerFacade(triggerName);
		
		TriggerRunTimeStatus trstat = trig.getTriggerRunTimeStatus();
		threadCount = trstat.getActiveThreadCount();
		int docCount = trstat.getPersistedQueueCount();
		int volDocCount = trstat.getVolatileQueueCount();
		String note = triggerName+":"+trstat.getProcessingState()
		              +" thread: "+threadCount
		              +" docs: "+docCount
		              +" volatileDocs: "+volDocCount;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "thread", threadCount );
		IDataUtil.put( pipelineCursor_1, "note", note );
		IDataUtil.put( pipelineCursor_1, "docCount", docCount );
		IDataUtil.put( pipelineCursor_1, "volDocCount", volDocCount );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	//new code
		public static String walkTopicNode(nTopicNode node,int TOPIC_THRESHOLD) {
			
			
			try
			{
			StringBuilder sbTopic=new StringBuilder("");
			
			
			
			//get the list of durables for the topic
			
		    Iterator<nDurableNode> i0 = node.getDurables();
		    
		    //loop over the durables and getting the details
		    
		    while(i0.hasNext()) 
		    {
		      nDurableNode durable = i0.next();
		      
		      
		
		      //fetching the number of connections per durable
		      
		      Iterator<nDurableConnectionNode> i1 = durable.getConnections();
		      
		      //Long[] connCount={};
		      
		      String[] connections=new String[3];
		      Long[] connCount =  new Long[3];
		      
		      int i=0;
		      nDurableConnectionNode subscription=null;
		      Long transactionDepth=null;
		      
		      //looping over the connections and fetching the details
		      
			      while(i1.hasNext()) 
			      {
			        subscription = i1.next();
			        
			        connections[i]=subscription.getId();
			        transactionDepth = subscription.getTransactionDepth();
			        connCount[i]=transactionDepth;
			        i++;
			        
			      }
		      //compare the connection details
		      
		        
			        if (i==2)
			        {
			        	//that means we have 2 clients for clustered environment
			        	if((connCount[0]==0 && connCount[1]>=TOPIC_THRESHOLD))
			        	{
			        		
			        	//sbTopic.append("PROBLEM**");
			        	sbTopic.append(node.getName()+"**"+durable.getName()+"**"+connections[1]);
			        	sbTopic.append("-->"+connCount[1]);
			        	sbTopic.append("\n");
			        	}
			        	
			        	if((connCount[1]==0 && connCount[0]>=TOPIC_THRESHOLD))
			        	{
			        		
			        	//sbTopic.append("PROBLEM**");
			        	sbTopic.append(node.getName()+"**"+durable.getName()+"**"+connections[0]);
			        	sbTopic.append("-->"+connCount[0]);
			        	sbTopic.append("\n");
			        	}
			        	
		
			        }
			        else if(i==1)
			        {
			        	//this means we have one connection
			        	if(connCount[0]>=TOPIC_THRESHOLD)
			        	{
				        	//sbTopic.append("PROBLEM**");
			        	
			        	sbTopic.append(node.getName()+"**"+durable.getName()+"**"+connections[0]);
			        	sbTopic.append("-->"+connCount[0]);
			        	sbTopic.append("\n");
			        	}
			        }
			        else
			        {
			        	//do nothing
		
			        }
		        
		      }
		    
		    return sbTopic.toString();
			}
			catch(Exception e)
			{
				return e.toString();
			}
		  }
		
		
		
		//end new code
	
	private  static StringBuilder sbQ=new StringBuilder("");
	private static StringBuilder sbT=new StringBuilder("");
	
	public static StringBuilder searchNodes(nContainer container)
	{
		StringBuilder leafData = new StringBuilder("");
		Enumeration children = container.getNodes(); 
		
		while (children.hasMoreElements()) { 
			nNode child = (nNode)children.nextElement(); 
			if (child instanceof nContainer) { 
					leafData.append(searchNodes((nContainer)child)); 
			} else if (child instanceof nLeafNode) { 
					nLeafNode leaf = (nLeafNode)child; 
					if (leaf.isChannel()) { 
						leafData.append(leaf.getAbsolutePath());
						leafData.append(" ");
					}
			}
		} 
		return leafData;
	}
	
	public static StringBuilder searchQueues(nContainer container) 
	{
		Enumeration children = container.getNodes(); 
		
		//String arr[]={""};
		//StringBuilder sb=new StringBuilder("");
		while (children.hasMoreElements()) { 
			nNode child = (nNode)children.nextElement(); 
			if (child instanceof nContainer) { 
					searchQueues((nContainer)child); 
			} else if (child instanceof nLeafNode) { 
					nLeafNode leaf = (nLeafNode)child; 
					if (leaf.isQueue()) { 
							//sbQ.append(leaf.getName());
							sbQ.append(leaf.getAbsolutePath ());
							sbQ.append("\n");
					} 
					} 
			} 
		
		return sbQ;
	} 
	
	
	
	
	public static StringBuilder searchTopics(nContainer container) 
	{
		Enumeration children = container.getNodes(); 
		
		//String arr[]={""};
		//StringBuilder sb=new StringBuilder("");
		while (children.hasMoreElements()) { 
			nNode child = (nNode)children.nextElement(); 
			if (child instanceof nContainer) { 
					searchTopics((nContainer)child); 
			} else if (child instanceof nLeafNode) { 
					nLeafNode leaf = (nLeafNode)child; 
					if (leaf.isChannel()) { 
							//sbT.append(leaf.getName());
							sbT.append(leaf.getAbsolutePath ());
							sbT.append("\n");
					} 
					} 
			} 
		
		return sbT;
	} 
	
		
	// --- <<IS-END-SHARED>> ---
}

