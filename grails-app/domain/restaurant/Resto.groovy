package restaurant

class Resto {
    Long id
    String restoName
    String restoLocation


    static hasMany = [plats: Plat]
    static constraints = {
        restoName(nullable: false, blank: false)
    }
    static mapping = {
        table: 'restos'
        id column: 'id', generator: 'native', params: [sequence: 'resto_seq']
    }
}
