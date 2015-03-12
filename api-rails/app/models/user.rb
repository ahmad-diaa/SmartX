class User < ActiveRecord::Base
	has_secure_password    
	validates :name,  presence: true, length: { maximum: 25 }, uniqueness: { case_sensitive: false }
	validates :password,  presence: true, length: { minimum: 6}
	def User.digest(string)
    cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST :
                                                  BCrypt::Engine.cost
    BCrypt::Password.create(string, cost: cost)
  end

end
