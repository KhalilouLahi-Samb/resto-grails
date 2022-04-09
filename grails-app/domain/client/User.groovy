package client

import restaurant.Plat

class User {
    Long id
    String firstName
    String lastName
    String address

    static hasMany = [usersPlats: Plat]

    static constraints = {
        firstName nullable: false
        lastName nullable: true
    }

    static mapping = {
        table: 'users'
        id column: 'id', generator: 'native', params: [sequence: 'user_seq']
    }
}
