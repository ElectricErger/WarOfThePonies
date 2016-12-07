package characters;

public class Party {
	static private Party _instance;
	static private final int PARTY_MAX_SIZE = 6;
	static private int party_size = 1;
	static private Character[] members = new Character[PARTY_MAX_SIZE];
	
	static public Party getParty(){
		if(_instance == null)
			_instance = new Party();
		return _instance;
	}
	
	private Party(){
		
	}
	
	private void verifyPartySize(){
		if(party_size >= PARTY_MAX_SIZE || members[party_size+1]==null)
			return;
		else{ //It's not accurate
			party_size = 0;
			for(Character c : members){
				if(c != null)
					party_size++;
			}
		}
	}
	
	
	public int getPartySize(){
		verifyPartySize();
		return party_size;
	}
	public void addMember(Character member) throws memberSizeError{
		verifyPartySize();		
		if(party_size >= PARTY_MAX_SIZE)
			throw new memberSizeError("Your party is full");
		else{
			members[party_size] = member;
			party_size++;
		}
	}
	public void removeMember(Character member) throws memberMissing{
		
	}
}


class memberSizeError extends Exception{
	public memberSizeError(String msg){
		super(msg);
	}
}
class memberMissing extends Exception{
	public memberMissing (String msg){
		super(msg);
	}
}