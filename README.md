# dmn-executor
Camunda DMN Executor Wrapper

To prepare JAR with dependencies
`mvnw.cmd clean install assembly:single`

In Pentaho use:


#### Import to User Defined Java class
`import com.luki.dmn.executor.Decider;`

#### Initialize

`Decider decider;`

	public boolean init(StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface) {

		try{
			//Initialize from DMN saved in the compiled jar
			decider = new Decider();
			//Initialize from string saved as parameter of User Defined Java class
			decider.initFromStr(getParameter("dmn"));

		}catch(Exception e){
		}
	return parent.initImpl(stepMetaInterface, stepDataInterface);
	}


#### Use variables in the call

1. Set variables Names


	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
	  
	  Object[] r=getRow();
	  if (r==null)
	  {
		setOutputDone();
		return false;
	  }

	  if (first) {
		 yearIndex = getInputRowMeta().indexOfValue(getParameter("guestCount"));
		 if (yearIndex<0) {
			 throw new KettleException("Year field not found in the input row, check parameter 'guestCount'!");
		 }
		 first=false;
	 }

	  Object[] outputRowData = RowDataUtil.resizeArray(r, data.outputRowMeta.size());
	  int outputIndex = getInputRowMeta().size();

	  String dish = getInputRowMeta().getString(r, 0);
	  String guestCount = getInputRowMeta().getString(r, 1);
	  outputRowData[outputIndex++] = decider.decide(dish, guestCount);


	  putRow(data.outputRowMeta, outputRowData);

	  return true;
	}
