package com.TournamentTracker.util;

public interface ExceptionMessages {
    //USER
    String USER_NOT_FOUND_BY_ID = "Użytkownik z id %s nie został znaleziony.";
    String USER_ALREADY_EXISTS = "Użytkownik z nazwą %s już istnieje.";
    String USERNAME_OR_PASSWORD_INCORRECT = "Nazwa użytkownika lub hasło jest niepoprawne.";
    String USER_NOT_AUTHENTICATED = "Użytkownik nie jest zautoryzowany.";
    String USER_NOT_FOUND_BY_USERNAME = "Użytkownik z nazwą %s nie został znaleziony.";
    String OLD_PASSWORD_DOES_NOT_MATCH = "Stare hasło nie jest poprawne.";
    String NEW_PASSWORD_SHOULD_BE_DIFFERENT = "Nowe hasło powinno być inne niż stare.";
    String USERNAME_SHOULD_BE_EMAIL = "Nazwa użytkownika powinna być emailem.";

    //TOURNAMENT
    String TOURNAMENT_NOT_FOUND = "Turniej z id %s nie został znaleziony.";
    String NOT_AUTHORIZED_TOURNAMENT = "Nie masz uprawnień do modyfikacji tego turnieju.";
    String ALREADY_OWNER_OF_TOURNAMENT = "Jesteś już właścicielem turnieju.";

    //TEAM
    String TEAM_NOT_FOUND = "Drużyna z id %s nie została znaleziona.";
    String NOT_OWNER_OF_TEAM = "Nie jesteś założycielem żadnej drużyny.";
    String OWNER_OR_MEMBER_OF_TEAM = "Jesteś już założycielem lub członkiem drużyny.";
    String NOT_AUTHORIZED_TEAM = "Nie masz uprawnień do modyfikacji tej drużyny.";

    //GAME
    String GAME_NOT_FOUND = "Mecz z id %s nie został znaleziony.";
    String NOT_AUTHORIZED_GAME_TOURNAMENT = "Nie masz uprawnień do modyfikacji meczu w tym turnieju.";

    //SPORT
    String SPORT_NOT_FOUND = "Sport z id %s nie został znaleziony.";

    //RULE
    String RULE_NOT_FOUND = "Zasady z id %s nie zostały znalezione.";


}
