package opennlp.maxent;

import java.io.IOException;

import opennlp.model.FileEventStream;
import opennlp.model.OnePassRealValueDataIndexer;
import opennlp.model.RealValueFileEventStream;

import junit.framework.TestCase;

public class RealValueModelTest extends TestCase {

  public void testRealValuedWeightsVsRepeatWeighting() throws IOException {
    RealValueFileEventStream rvfes1 = new RealValueFileEventStream("src/test/resources/data/opennlp/maxent/real-valued-weights-training-data.txt");
    GISModel realModel = GIS.trainModel(100,new OnePassRealValueDataIndexer(rvfes1,1));

    FileEventStream rvfes2 = new FileEventStream("src/test/resources/data/opennlp/maxent/repeat-weighting-training-data.txt");
    GISModel repeatModel = GIS.trainModel(100,new OnePassRealValueDataIndexer(rvfes2,1));

    String[] features2Classify = new String[] {"feature2","feature5"};
    double[] realResults = realModel.eval(features2Classify);
    double[] repeatResults = repeatModel.eval(features2Classify);

    assertEquals(realResults.length, repeatResults.length);
    for(int i=0; i<realResults.length; i++) {
      System.out.println(String.format("classifiy with realModel: %1$s = %2$f", realModel.getOutcome(i), realResults[i]));
      System.out.println(String.format("classifiy with repeatModel: %1$s = %2$f", repeatModel.getOutcome(i), repeatResults[i]));
      assertEquals(realResults[i], repeatResults[i], 0.01f);
    }

    features2Classify = new String[] {"feature1","feature2","feature3","feature4","feature5"};
    realResults = realModel.eval(features2Classify, new float[] {5.5f, 6.1f, 9.1f, 4.0f, 1.8f});
    repeatResults = repeatModel.eval(features2Classify, new float[] {5.5f, 6.1f, 9.1f, 4.0f, 1.8f});

    System.out.println();
    assertEquals(realResults.length, repeatResults.length);
    for(int i=0; i<realResults.length; i++) {
      System.out.println(String.format("classifiy with realModel: %1$s = %2$f", realModel.getOutcome(i), realResults[i]));
      System.out.println(String.format("classifiy with repeatModel: %1$s = %2$f", repeatModel.getOutcome(i), repeatResults[i]));
      assertEquals(realResults[i], repeatResults[i], 0.01f);      
    }

  }
}
