package battle;

import java.util.ArrayList;

class ListOfCharacters{
    private ArrayList<Character> characterList;
    private ListOfMoves startMoves;

    ListOfCharacters() {
        startMoves = new ListOfMoves();
        characterList = new ArrayList<Character>();
        //Movesets for playable characters
        String[] fengMoveNames = {"Steal Code", "Throw Chair", "Stack Overflow", "Snort Candy"};
        String[] joyceMoveNames = {"Cram", "Throw Basketball", "Quick Maths", "Call Uber"};
        String[] sihanMoveNames = {"Argue", "Cram", "Cry", "Ghost"};
        String[] yashMoveNames = {"Quadratic Formula", "Chemistry Pun", "Dodge Homework", "TLAP"};
        String[] mishaMoveNames = {"Stack Overflow", "Argue", "Cry", "Write Garbage Code"};
        String[] johannMoveNames = {"Spam Calculator", "Quick Maths", "Work Out", "Not Study"};
        String[] angelaMoveNames = {"Dissect Frog", "Drink Bubble Tea", "Splash Acid", "Chemistry Pun"};
        Move[] fengMoves = returnMoveArray(fengMoveNames);
        Move[] joyceMoves = returnMoveArray(joyceMoveNames);
        Move[] sihanMoves = returnMoveArray(sihanMoveNames);
        Move[] yashMoves = returnMoveArray(yashMoveNames);
        Move[] mishaMoves = returnMoveArray(mishaMoveNames);
        Move[] johannMoves = returnMoveArray(johannMoveNames);
        Move[] angelaMoves = returnMoveArray(angelaMoveNames);

        //Movesets for nonplayable characters
        String[] mrChoiMoveNames = {"Trigonometry Test", "Olympiads Question", "Mark Test", "Quadratic Formula"};
        String[] mrShimMoveNames = {"Trigonometry Test", "Practice Set", "Life Lesson", "Meter Stick"};
        String[] mrTimmermanMoveNames = {"Divisibility Test", "Quadratic Formula", "Complete the Square", "Math Angels"};
        Move[] mrChoiMoves = returnMoveArray(mrChoiMoveNames);
        Move[] mrShimMoves = returnMoveArray(mrShimMoveNames);
        Move[] mrTimmermanMoves = returnMoveArray(mrTimmermanMoveNames);

        //List of playable characters
        characterList.add(new PlayableCharacter(50, 60, 20, 50, 60, "Technology", "Feng", "[description]", fengMoves, "Clown", null, null, null, null, null));
        characterList.add(new PlayableCharacter(25, 25, 70, 20, 60, "Math", "Joyce", "[description]", joyceMoves, "Persistent", null, null, null, null, null));
        characterList.add(new PlayableCharacter(20, 20, 65, 60, 35, "English", "Sihan", "[description]", sihanMoves, "Distressed", null, null, null, null, null));
        characterList.add(new PlayableCharacter(30, 40, 30, 70, 30, "Science", "Yash", "[description]", yashMoves, "Relieve", null, null, null, null, null));
        characterList.add(new PlayableCharacter(80, 60, 80, 10, 70, "Math", "Johann", "[description]", johannMoves, "Extreme Luck", null, null, null, null, null));
        characterList.add(new PlayableCharacter(30, 40, 50, 40, 40, "Technology", "Misha", "[description]", mishaMoves, "Avoidant", null, null, null, null, null));
        characterList.add(new PlayableCharacter(35, 40, 65, 75, 35, "Science", "Angela", "[description]", angelaMoves, "Protective", null, null, null, null, null));
        //List of nonplayable characters
        characterList.add(new NonPlayableCharacter(60, 30, 110, 70, 230, "Math", "Mr. Choi", "perabler", mrChoiMoves, 100, "Speed Boost", null));
        characterList.add(new NonPlayableCharacter(300, 50, 100, 30, 20, "Math", "Mr. Shim", "Let homework be your guide", mrShimMoves, 100, "Demoralize", null));
        characterList.add(new NonPlayableCharacter(200, 40, 100, 60, 100, "Math", "Mr. Timmerman", "Focus isn't just the name of a car.", mrTimmermanMoves, 100, "Unaware", null));
    }

    private Move[] returnMoveArray(String[] moveNames) {
        Move[] moveArray = new Move[4];
        for (int i=0; i<4; i++) {
            moveArray[i] = startMoves.retrieveMove(moveNames[i]);
        }
        return moveArray;
    }

    public Character returnCharacter(String name) {
        for(int i=0; i<characterList.size(); i++) {
            if (name.equals(characterList.get(i).getName())) {
                return characterList.get(i);
            }
        }
        return null;
    }
}