class Device < ActiveRecord::Base
	self.primary_key = 'device_id'
	belongs_to :rooms
	has_one :clicker
	has_many :notes,dependent: :destroy
	validates :name, :uniqueness => {:scope => [:user_id, :room_id]}
	def to_param
    	device_id.parameterize
  	end
end
