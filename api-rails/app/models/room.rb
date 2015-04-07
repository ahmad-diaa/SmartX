class Room < ActiveRecord::Base
	validates :name, presence: true
	belongs_to :user
	has_many :devices,dependent: :destroy
end
