package indicators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParabolicSARTest {
    /**
     * ParabolicSAR is a class for calculating Parabolic Stop and Reverse (SAR) over a time series data.
     * This class primarily provides methods to get the Parabolic SAR value and to draw them on a graph.
     * <p>
     * The `save` method is responsible for setting the parameters required for the computation,
     * including start, increment, and max values. These parameters are loaded from text fields by the `save` method.
     */

    @Test
    public void testSave() {
        // Given a ParabolicSAR instance
        ParabolicSAR parabolicSAR = new ParabolicSAR();

        // When updating the start, increment, and max values via the text fields
        parabolicSAR.getStartField().setText("0.03");
        parabolicSAR.getIncrementField().setText("0.03");
        parabolicSAR.getMaxValueField().setText("0.3");

        // And then calling the save method to apply the changes
        parabolicSAR.save();

        // Then we expect the parameters to have been updated correctly
        assertEquals(0.03, parabolicSAR.getStart());
        assertEquals(0.03, parabolicSAR.getIncrement());
        assertEquals(0.3, parabolicSAR.getMaxValue());
    }
}