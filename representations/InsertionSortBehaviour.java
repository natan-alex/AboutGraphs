package representations;

public class InsertionSortBehaviour implements SortBehaviour {
    public static final SortBehaviour singletonInstance = new InsertionSortBehaviour();

    @Override
    public void sortArraysTogether(int[] arrayToBeSorted, int[] otherArray) {
        int currentItemOfWhichToSortArray, currentItemOfOtherArray, auxiliarVariable;

        for (int minimumItemIndex = 1; minimumItemIndex < arrayToBeSorted.length; minimumItemIndex++) {
            currentItemOfWhichToSortArray = arrayToBeSorted[minimumItemIndex];
            currentItemOfOtherArray = otherArray[minimumItemIndex];
            auxiliarVariable = minimumItemIndex - 1;

            while (auxiliarVariable >= 0 && arrayToBeSorted[auxiliarVariable] > currentItemOfWhichToSortArray) {
                arrayToBeSorted[auxiliarVariable + 1] = arrayToBeSorted[auxiliarVariable];
                otherArray[auxiliarVariable + 1] = otherArray[auxiliarVariable];
                auxiliarVariable--;
            }

            arrayToBeSorted[auxiliarVariable + 1] = currentItemOfWhichToSortArray;
            otherArray[auxiliarVariable + 1] = currentItemOfOtherArray;
        }
    }
}
